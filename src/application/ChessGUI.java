package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ChessGUI extends Application {

    private static final int BOARD_SIZE = 8;
    private static final int SQUARE_SIZE = 60;

    private ChessMatch chessMatch;
    private GridPane boardGrid;
    private Map<String, Image> pieceImages;

    @Override
    public void start(Stage primaryStage) {
        chessMatch = new ChessMatch();
        pieceImages = loadPieceImages();

        boardGrid = createBoard();
        updateBoard();

        Scene scene = new Scene(boardGrid, BOARD_SIZE * SQUARE_SIZE, BOARD_SIZE * SQUARE_SIZE);

        primaryStage.setTitle("Jogo de Xadrez");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Map<String, Image> loadPieceImages() {
        Map<String, Image> images = new HashMap<>();
        String[] possibleBasePaths = {"/images/", "/resources/images/"};

        String[] pieceTypes = {"rook", "knight", "bishop", "queen", "king", "pawn"};
        String[] fileColors = {"white", "black"};

        for (String fileColor : fileColors) {
            for (String type : pieceTypes) {
                String fileName = fileColor + "_" + type + ".png";
                Image img = null;
                String loadedPath = "Nenhum";

                for (String basePath : possibleBasePaths) {
                    String resourcePath = basePath + fileName;
                    try (InputStream imageStream = getClass().getResourceAsStream(resourcePath)) {
                        if (imageStream != null) {
                            img = new Image(imageStream);
                            if (!img.isError()) {
                                loadedPath = resourcePath;
                                break;
                            } else {
                                System.err.println("Erro ao decodificar imagem: " + resourcePath);
                                img = null;
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Exceção ao tentar carregar: " + resourcePath + " - " + e.getMessage());
                    }
                }

                if (img != null && !img.isError()) {
                    String key = (fileColor.equals("white") ? Color.PURPLE : Color.GREEN) + "_" + type.toUpperCase();
                    images.put(key, img);
                    System.out.println("Carregada: " + loadedPath);
                } else {
                    System.err.println("FALHA AO CARREGAR: " + fileName + " (Verifique 'Resources Root' e Rebuild)");
                }
            }
        }

        if (images.isEmpty()) {
            System.err.println("ALERTA: Nenhuma imagem de peça foi carregada.");
        } else {
            System.out.println(images.size() + " imagens de peças carregadas no mapa.");
        }
        return images;
    }


    private GridPane createBoard() {
        GridPane gridPane = new GridPane();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Rectangle square = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);

                if ((row + col) % 2 == 0) {
                    square.setFill(javafx.scene.paint.Color.web("#f0d9b5"));
                } else {
                    square.setFill(javafx.scene.paint.Color.web("#b58863"));
                }

                StackPane squarePane = new StackPane(square);
                gridPane.add(squarePane, col, row);
            }
        }
        return gridPane;
    }

    private void updateBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                StackPane squarePane = getSquarePane(row, col);
                if (squarePane != null && squarePane.getChildren().size() > 1) {
                    squarePane.getChildren().remove(1);
                }
            }
        }

        ChessPiece[][] pieces = chessMatch.getPieces();
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                ChessPiece piece = pieces[i][j];
                if (piece != null) {
                    ImageView pieceView = createPieceImageView(piece);
                    StackPane targetPane = getSquarePane(i, j);
                    if (targetPane != null && pieceView != null) {
                        targetPane.getChildren().add(pieceView);
                    }
                }
            }
        }
    }

    private ImageView createPieceImageView(ChessPiece piece) {
        String pieceTypeSimple = piece.getClass().getSimpleName();
        String pieceType;

        switch (pieceTypeSimple) {
            case "Rei": pieceType = "KING"; break;
            case "Rainha": pieceType = "QUEEN"; break;
            case "Torre": pieceType = "ROOK"; break;
            case "Bispo": pieceType = "BISHOP"; break;
            case "Cavalo": pieceType = "KNIGHT"; break;
            case "Peão": pieceType = "PAWN"; break;
            default:
                System.err.println("Tipo de peça desconhecido no Switch: " + pieceTypeSimple);
                pieceType = "UNKNOWN";
                break;
        }

        String key = piece.getColor() + "_" + pieceType;

        Image img = pieceImages.get(key);
        if (img != null) {
            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(SQUARE_SIZE * 0.8);
            imageView.setFitHeight(SQUARE_SIZE * 0.8);
            imageView.setPreserveRatio(true);
            return imageView;
        } else {
            if (!pieceType.equals("UNKNOWN")) {
                System.err.println("Imagem não encontrada no mapa para a chave: " + key);
            }
            return null;
        }
    }

    private StackPane getSquarePane(int row, int col) {
        int index = col + row * BOARD_SIZE;
        if (index >= 0 && index < boardGrid.getChildren().size()) {
            if (boardGrid.getChildren().get(index) instanceof StackPane) {
                return (StackPane) boardGrid.getChildren().get(index);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
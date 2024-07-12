package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";

    public static void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        }
        catch (RuntimeException e) {
            throw new InputMismatchException("Erro!! Os as casas válidas são de a1 a h8.");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turno : " + chessMatch.getTurn());
        if (!chessMatch.getCheckMate()) {
            System.out.println("Esperando o jogador: " + chessMatch.getCurrentPlayer());
            if (chessMatch.getCheck()) {
                System.out.println("CHECK!");
            }
        }
        else {
            System.out.println("CHECKMATE!");
            System.out.println("Vencedor: " + chessMatch.getCurrentPlayer());
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_BLACK_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.GREEN) {
                System.out.print(ANSI_GREEN + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_PURPLE + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> green = captured.stream().filter(x -> x.getColor() == Color.GREEN).collect(Collectors.toList());
        List<ChessPiece> purple = captured.stream().filter(x -> x.getColor() == Color.PURPLE).collect(Collectors.toList());
        System.out.println("Peças comidas:");
        System.out.print("Jogador verde: ");
        System.out.print(ANSI_GREEN);
        System.out.println(Arrays.toString(green.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Jogador roxo: ");
        System.out.print(ANSI_PURPLE);
        System.out.println(Arrays.toString(purple.toArray()));
        System.out.print(ANSI_RESET);
    }
}
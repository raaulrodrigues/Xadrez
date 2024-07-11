package application;

import boardgame.Position;
import boardgame.Board;
import chess.ChessMatch;
import application.UI;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.Scanner;

public class Program{

    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

         while(true){
             UI.printBoard(chessMatch.getPieces());
             System.out.println();
             System.out.print("Posição atual: ");
             ChessPosition source = UI.readChessPosition(sc);
             System.out.println();
             System.out.print("Posição de destino: ");
             ChessPosition target = UI.readChessPosition(sc);

             ChessPiece capturedPiece = chessMatch.performChessMove(source,target);
        }
    }
}
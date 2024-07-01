package application;

import boardgame.Position;
import boardgame.Board;
import chess.ChessMatch;
import application.UI;
public class Program{
    public static void main (String[] args){
        ChessMatch chessMatch = new ChessMatch();
         UI.printBoard(chessMatch.getPieces());
    }
}
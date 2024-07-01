package boardgame;

public class Piece {
    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    //apenas get para ngm poder editar o tabuleiro
    //só pode ser acessado dentro do pacote tabuleiro e a subclasse da peça (Piece)
    protected Board getBoard() {
        return board;
    }
}

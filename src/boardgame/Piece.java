package boardgame;

public class Piece {
    protected Posicao posicao;
    private Tabuleiro tabuleiro;

    public Piece(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
        posicao = null;
    }

    //apenas get para ngm poder editar o tabuleiro
    //só pode ser acessado dentro do pacote tabuleiro e a subclasse da peças
    protected Board getTabuleiro() {
        return tabuleiro;
    }
}

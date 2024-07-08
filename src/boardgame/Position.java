package boardgame;

public class Position {
    private int row;
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int linha) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int coluna) {
        this.column = coluna;
    }

    @Override
    public String toString(){
        return "Posição (" + row + "," + column + ") ";
    }
}

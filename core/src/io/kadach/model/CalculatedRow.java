package io.kadach.model;


public class CalculatedRow {

    private GameBox[] row;
    private int score;


    public GameBox[] getRow() {
        return row;
    }

    public int getScore() {
        return score;
    }

    public CalculatedRow(GameBox[] row, int score) {
        this.row = row;
        this.score = score;
    }

}

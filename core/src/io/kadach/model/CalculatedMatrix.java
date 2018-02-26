package io.kadach.model;


public class CalculatedMatrix {

    private GameBox[][] matrix;
    private int score;


    public GameBox[][] getMatrix() {
        return matrix;
    }

    public int getScore() {
        return score;
    }

    public CalculatedMatrix(GameBox[][] matrix, int score) {
        this.matrix = matrix;
        this.score = score;
    }
}

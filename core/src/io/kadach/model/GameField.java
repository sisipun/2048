package io.kadach.model;


public class GameField {

    private GameBox[][] fieldMatrix;

    public GameField() {
        fieldMatrix = new GameBox[4][4];
    }

    public GameBox[][] getFieldMatrix() {
        return fieldMatrix;
    }

}

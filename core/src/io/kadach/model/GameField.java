package io.kadach.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

import static io.kadach.util.Constants.GAME_FIELD_SIZE;

public class GameField {

    private GameBox[][] fieldMatrix;
    private boolean gameOver;

    public GameField() {
        this.fieldMatrix = new GameBox[GAME_FIELD_SIZE][GAME_FIELD_SIZE];
        this.gameOver = false;
        generateBox(4);
        printMatrix();
    }

    public GameBox[][] getFieldMatrix() {
        return fieldMatrix;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            upPressed();
            generateBox(1);
            checkGameOver();
            printMatrix();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            leftPressed();
            generateBox(1);
            checkGameOver();
            printMatrix();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            rightPressed();
            generateBox(1);
            checkGameOver();
            printMatrix();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            downPressed();
            generateBox(1);
            checkGameOver();
            printMatrix();
        }
    }

    private void leftPressed() {
        for (int i = 0; i < fieldMatrix.length; i++) {
            GameBox[] newRow = calculateNewRow(fieldMatrix[i]);
            for (int j = 0; j < fieldMatrix[i].length; j++) {
                fieldMatrix[i][j] = newRow[j];
            }
        }
    }

    private void rightPressed() {
        for (int i = 0; i < fieldMatrix.length; i++) {
            GameBox[] oldRow = ArrayUtils.clone(fieldMatrix[i]);
            ArrayUtils.reverse(oldRow);
            GameBox[] newRow = calculateNewRow(oldRow);
            ArrayUtils.reverse(newRow);
            for (int j = 0; j < fieldMatrix[i].length; j++) {
                fieldMatrix[i][j] = newRow[j];
            }
        }
    }

    private void upPressed() {
        for (int j = 0; j < fieldMatrix[0].length; j++) {
            GameBox[] oldRow = getColumn(fieldMatrix, j);
            GameBox[] newRow = calculateNewRow(oldRow);
            for (int i = 0; i < fieldMatrix.length; i++) {
                fieldMatrix[i][j] = newRow[i];
            }
        }
    }

    private void downPressed() {
        for (int j = 0; j < fieldMatrix[0].length; j++) {
            GameBox[] oldRow = getColumn(fieldMatrix, j);
            ArrayUtils.reverse(oldRow);
            GameBox[] newRow = calculateNewRow(oldRow);
            ArrayUtils.reverse(newRow);
            for (int i = 0; i < fieldMatrix.length; i++) {
                fieldMatrix[i][j] = newRow[i];
            }
        }
    }

    private GameBox[] calculateNewRow(GameBox[] oldRow) {
        GameBox[] newRow = new GameBox[oldRow.length];
        GameBox lastRowBox = null;
        int indexOfLast = -1;
        for (GameBox box : oldRow) {
            if (box == null) {
                continue;
            }
            if (lastRowBox != null && box.getType().equals(lastRowBox.getType())) {
                lastRowBox = new GameBox(box.getType().getNext());
                newRow[indexOfLast] = lastRowBox;
                continue;
            }
            indexOfLast++;
            lastRowBox = box;
            newRow[indexOfLast] = lastRowBox;
        }

        return newRow;
    }

    private GameBox[] getColumn(GameBox[][] matrix, int columnNumber) {
        GameBox[] column = new GameBox[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][columnNumber];
        }
        return column;
    }

    private void printMatrix() {
        Gdx.app.log("GO:", String.valueOf(gameOver));
        for (GameBox[] row : fieldMatrix) {
            String listString = "|";
            for (GameBox s : row) {
                if (s == null) {
                    listString += "\t|";
                    continue;
                }
                listString += s.getType().name() + "\t|";
            }
            Gdx.app.log("", listString);
        }
        Gdx.app.log("", "----------------------------------");
    }

    private void generateBox(int count) {
        Random random = new Random();
        while (count > 0) {
            int i = random.nextInt(fieldMatrix.length);
            int j = random.nextInt(fieldMatrix[0].length);
            if (fieldMatrix[i][j] == null) {
                fieldMatrix[i][j] = new GameBox();
                count--;
            }
        }
    }

    private void checkGameOver() {
        boolean placeLeft = false;
        for (GameBox[] row : fieldMatrix) {
            for (GameBox box : row) {
                if (box == null) {
                    placeLeft = true;
                }
            }
        }
        this.gameOver = !placeLeft;
    }

}

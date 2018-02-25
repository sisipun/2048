package io.kadach.model;


import com.badlogic.gdx.Gdx;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.R;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;
import static io.kadach.util.Constants.GAME_FIELD_SIZE;
import static java.lang.System.arraycopy;
import static java.util.Arrays.deepEquals;
import static org.apache.commons.lang3.ArrayUtils.reverse;

public class GameField {

    private GameBox[][] fieldMatrix;
    private GameBox[][] previousTurnFieldMatrix;
    private boolean gameOver;
    private int score;
    private int resetAttemptCount;

    public GameField() {
        this.fieldMatrix = new GameBox[GAME_FIELD_SIZE][GAME_FIELD_SIZE];
        generateBox(4);
        this.previousTurnFieldMatrix = copy(fieldMatrix);
        this.gameOver = false;
        this.score = 0;
        this.resetAttemptCount = 3;
    }

    public GameBox[][] getFieldMatrix() {
        return fieldMatrix;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public int getResetAttemptCount() {
        return resetAttemptCount;
    }

    public void handleInput() {
        GameBox[][] newMatrix = getNewMatrix();
        if (null != newMatrix && !deepEquals(newMatrix, fieldMatrix)) {
            previousTurnFieldMatrix = copy(fieldMatrix);
            fieldMatrix = newMatrix;
            score = calculateScore(fieldMatrix);
            generateBox(1);
            checkGameOver();
        }
        if (Gdx.input.isKeyPressed(R)) {
            resetLastAction();
        }
    }

    private GameBox[][] getNewMatrix() {
        if (Gdx.input.isKeyJustPressed(UP)) return onUpPressed();
        if (Gdx.input.isKeyJustPressed(LEFT)) return onLeftPressed();
        if (Gdx.input.isKeyJustPressed(RIGHT)) return onRightPressed();
        if (Gdx.input.isKeyJustPressed(DOWN)) return onDownPressed();
        return null;
    }

    private void resetLastAction() {
        if (resetAttemptCount > 0 && !deepEquals(previousTurnFieldMatrix, fieldMatrix)) {
            resetAttemptCount--;
            fieldMatrix = copy(previousTurnFieldMatrix);
        }
    }

    private GameBox[][] onLeftPressed() {
        GameBox[][] newMatrix = copy(fieldMatrix);
        for (GameBox[] row : newMatrix) {
            GameBox[] newRow = calculateNewRow(row);
            arraycopy(newRow, 0, row, 0, row.length);
        }
        return newMatrix;
    }

    private GameBox[][] onRightPressed() {
        GameBox[][] newMatrix = copy(fieldMatrix);
        for (GameBox[] row : newMatrix) {
            GameBox[] oldRow = ArrayUtils.clone(row);
            reverse(oldRow);
            GameBox[] newRow = calculateNewRow(oldRow);
            reverse(newRow);
            arraycopy(newRow, 0, row, 0, row.length);
        }
        return newMatrix;
    }

    private GameBox[][] onUpPressed() {
        GameBox[][] newMatrix = copy(fieldMatrix);
        for (int j = 0; j < newMatrix[0].length; j++) {
            GameBox[] oldRow = getColumn(newMatrix, j);
            GameBox[] newRow = calculateNewRow(oldRow);
            for (int i = 0; i < newMatrix.length; i++) {
                newMatrix[i][j] = newRow[i];
            }
        }
        return newMatrix;
    }

    private GameBox[][] onDownPressed() {
        GameBox[][] newMatrix = copy(fieldMatrix);
        for (int j = 0; j < newMatrix[0].length; j++) {
            GameBox[] oldRow = getColumn(newMatrix, j);
            reverse(oldRow);
            GameBox[] newRow = calculateNewRow(oldRow);
            reverse(newRow);
            for (int i = 0; i < newMatrix.length; i++) {
                newMatrix[i][j] = newRow[i];
            }
        }
        return newMatrix;
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

    private int calculateScore(GameBox[][] matrix) {
        int score = 0;
        for (GameBox[] row : matrix) {
            for (GameBox box : row) {
                if (null != box) {
                    score += box.getType().getPoints();
                }
            }
        }
        return score;
    }

    private GameBox[] getColumn(GameBox[][] matrix, int columnNumber) {
        GameBox[] column = new GameBox[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][columnNumber];
        }
        return column;
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
        GameBox[][] onUpPressed = onUpPressed();
        GameBox[][] onLeftPressed = onLeftPressed();
        this.gameOver = !placeLeft && deepEquals(onUpPressed, onLeftPressed);
    }

    private GameBox[][] copy(GameBox[][] fieldMatrix) {
        GameBox[][] copyMatrix = new GameBox[fieldMatrix.length][fieldMatrix[0].length];
        for (int i = 0; i < fieldMatrix.length; i++) {
            arraycopy(fieldMatrix[i], 0, copyMatrix[i], 0, fieldMatrix[i].length);
        }
        return copyMatrix;
    }

}

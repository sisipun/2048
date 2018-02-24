package io.kadach.model;


import com.badlogic.gdx.Gdx;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;
import static io.kadach.util.Constants.GAME_FIELD_SIZE;
import static java.lang.System.arraycopy;
import static java.util.Arrays.deepEquals;
import static org.apache.commons.lang3.ArrayUtils.*;

public class GameField {

    private GameBox[][] fieldMatrix;
    private GameBox[][] previousTurnFieldMatrix;
    private boolean gameOver;
    private int score;
    private final List<GameBox> changedBoxes;

    public GameField() {
        this.fieldMatrix = new GameBox[GAME_FIELD_SIZE][GAME_FIELD_SIZE];
        this.previousTurnFieldMatrix = new GameBox[GAME_FIELD_SIZE][GAME_FIELD_SIZE];
        this.gameOver = false;
        this.score = 0;
        this.changedBoxes = new ArrayList<GameBox>();
        generateBox(4);
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

    public void setScore(int score) {
        this.score = score;
    }

    public void handleInput() {
        if (isFieldChanged()) {
            calculateScore();
            generateBox(1);
            checkGameOver();
            changedBoxes.clear();
        }
    }

    private void calculateScore() {
        for (GameBox box : changedBoxes) {
            score += box.getType().getPoints();
        }
    }

    private boolean isFieldChanged() {
        if (Gdx.input.isKeyJustPressed(UP)) {
            GameBox[][] newMatrix = onUpPressed();
            previousTurnFieldMatrix = copy(fieldMatrix);
            fieldMatrix = newMatrix;
            return !deepEquals(previousTurnFieldMatrix, fieldMatrix);
        }
        if (Gdx.input.isKeyJustPressed(LEFT)) {
            GameBox[][] newMatrix = onLeftPressed();
            previousTurnFieldMatrix = copy(fieldMatrix);
            fieldMatrix = newMatrix;
            return !deepEquals(previousTurnFieldMatrix, fieldMatrix);
        }
        if (Gdx.input.isKeyJustPressed(RIGHT)) {
            GameBox[][] newMatrix = onRightPressed();
            previousTurnFieldMatrix = copy(fieldMatrix);
            fieldMatrix = newMatrix;
            return !deepEquals(previousTurnFieldMatrix, fieldMatrix);
        }
        if (Gdx.input.isKeyJustPressed(DOWN)) {
            GameBox[][] newMatrix = onDownPressed();
            previousTurnFieldMatrix = copy(fieldMatrix);
            fieldMatrix = newMatrix;
            return !deepEquals(previousTurnFieldMatrix, fieldMatrix);
        }
        return false;
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
                changedBoxes.add(lastRowBox);
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

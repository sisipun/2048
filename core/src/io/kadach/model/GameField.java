package io.kadach.model;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

import static io.kadach.model.ChangeFieldDestination.DOWN;
import static io.kadach.model.ChangeFieldDestination.LEFT;
import static io.kadach.model.ChangeFieldDestination.RIGHT;
import static io.kadach.model.ChangeFieldDestination.UP;
import static io.kadach.util.Constants.BOX_SIZE;
import static java.lang.System.arraycopy;
import static java.util.Arrays.deepEquals;
import static org.apache.commons.lang3.ArrayUtils.reverse;

public class GameField extends Actor {

    private GameBox[][] fieldMatrix;
    private GameBox[][] previousTurnFieldMatrix;
    private boolean gameOver;
    private int score;
    private int resetAttemptCount;
    private Texture texture;
    private GameFieldSize size;


    public int getScore() {
        return score;
    }

    public GameField(GameFieldSize size) {
        this.size = size;
        reset();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float width = getStage().getViewport().getWorldWidth();
        float height = getStage().getViewport().getWorldHeight();

        batch.draw(
                texture,
                0,
                0,
                width,
                height
        );
        for (int i = 0; i < fieldMatrix.length; i++) {
            for (int j = 0; j < fieldMatrix[i].length; j++) {
                if (null == fieldMatrix[i][j]) {
                    continue;
                }
                batch.draw(
                        fieldMatrix[i][j].getType().getTexture(),
                        ((width / 4) * (j + 1)) - (width / 8) - (BOX_SIZE / 2),
                        ((height / 4) * (4 - i)) - (height / 8) - (BOX_SIZE / 2),
                        BOX_SIZE,
                        BOX_SIZE
                );
            }
        }
    }

    public void reset() {
        this.fieldMatrix = new GameBox[size.getValue()][size.getValue()];
        generateBox(4);
        this.previousTurnFieldMatrix = copy(fieldMatrix);
        this.gameOver = false;
        this.score = 0;
        this.resetAttemptCount = 3;
        this.texture = new Texture("background.png");
    }

    public void resetLastAction() {
        if (resetAttemptCount > 0 && !deepEquals(previousTurnFieldMatrix, fieldMatrix)) {
            resetAttemptCount--;
            fieldMatrix = copy(previousTurnFieldMatrix);
        }
    }

    public void changeField(ChangeFieldDestination destination) {
        CalculatedMatrix calculatedMatrix = calculateNewMatrix(destination);
        if (!deepEquals(calculatedMatrix.getMatrix(), fieldMatrix)) {
            previousTurnFieldMatrix = copy(fieldMatrix);
            fieldMatrix = calculatedMatrix.getMatrix();
            score += calculatedMatrix.getScore();
            generateBox(1);
            checkGameOver();
        }
    }

    private CalculatedMatrix calculateNewMatrix(ChangeFieldDestination destination) {
        if (destination.equals(UP)) return onUpPressed();
        if (destination.equals(LEFT)) return onLeftPressed();
        if (destination.equals(RIGHT)) return onRightPressed();
        if (destination.equals(DOWN)) return onDownPressed();
        throw new IllegalArgumentException("Illegal destination");
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
        GameBox[][] onUpPressed = onUpPressed().getMatrix();
        GameBox[][] onLeftPressed = onLeftPressed().getMatrix();
        this.gameOver = !placeLeft && deepEquals(onUpPressed, onLeftPressed);
    }

    private CalculatedMatrix onLeftPressed() {
        GameBox[][] newMatrix = copy(fieldMatrix);
        int score = 0;
        for (GameBox[] row : newMatrix) {
            CalculatedRow calculatedRow = calculateNewRow(row);
            score += calculatedRow.getScore();
            GameBox[] newRow = calculatedRow.getRow();
            arraycopy(newRow, 0, row, 0, row.length);
        }
        return new CalculatedMatrix(newMatrix, score);
    }

    private CalculatedMatrix onRightPressed() {
        GameBox[][] newMatrix = copy(fieldMatrix);
        int score = 0;
        for (GameBox[] row : newMatrix) {
            GameBox[] oldRow = ArrayUtils.clone(row);
            reverse(oldRow);
            CalculatedRow calculatedRow = calculateNewRow(oldRow);
            score += calculatedRow.getScore();
            GameBox[] newRow = calculatedRow.getRow();
            reverse(newRow);
            arraycopy(newRow, 0, row, 0, row.length);
        }
        return new CalculatedMatrix(newMatrix, score);
    }

    private CalculatedMatrix onUpPressed() {
        GameBox[][] newMatrix = copy(fieldMatrix);
        int score = 0;
        for (int j = 0; j < newMatrix[0].length; j++) {
            GameBox[] oldRow = getColumn(newMatrix, j);
            CalculatedRow calculatedRow = calculateNewRow(oldRow);
            score += calculatedRow.getScore();
            GameBox[] newRow = calculatedRow.getRow();
            for (int i = 0; i < newMatrix.length; i++) {
                newMatrix[i][j] = newRow[i];
            }
        }
        return new CalculatedMatrix(newMatrix, score);
    }

    private CalculatedMatrix onDownPressed() {
        GameBox[][] newMatrix = copy(fieldMatrix);
        int score = 0;
        for (int j = 0; j < newMatrix[0].length; j++) {
            GameBox[] oldRow = getColumn(newMatrix, j);
            reverse(oldRow);
            CalculatedRow calculatedRow = calculateNewRow(oldRow);
            score += calculatedRow.getScore();
            GameBox[] newRow = calculatedRow.getRow();
            reverse(newRow);
            for (int i = 0; i < newMatrix.length; i++) {
                newMatrix[i][j] = newRow[i];
            }
        }
        return new CalculatedMatrix(newMatrix, score);
    }

    private CalculatedRow calculateNewRow(GameBox[] oldRow) {
        GameBox[] newRow = new GameBox[oldRow.length];
        int score = 0;
        GameBox lastRowBox = null;
        int indexOfLast = -1;
        for (GameBox box : oldRow) {
            if (box == null) {
                continue;
            }
            if (lastRowBox != null && box.getType().equals(lastRowBox.getType())) {
                lastRowBox = new GameBox(box.getType().getNext());
                score += lastRowBox.getType().getPoints();
                newRow[indexOfLast] = lastRowBox;
                continue;
            }
            indexOfLast++;
            lastRowBox = box;
            newRow[indexOfLast] = lastRowBox;
        }

        return new CalculatedRow(newRow, score);
    }

    private GameBox[] getColumn(GameBox[][] matrix, int columnNumber) {
        GameBox[] column = new GameBox[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][columnNumber];
        }
        return column;
    }

    private GameBox[][] copy(GameBox[][] fieldMatrix) {
        GameBox[][] copyMatrix = new GameBox[fieldMatrix.length][fieldMatrix[0].length];
        for (int i = 0; i < fieldMatrix.length; i++) {
            arraycopy(fieldMatrix[i], 0, copyMatrix[i], 0, fieldMatrix[i].length);
        }
        return copyMatrix;
    }

}

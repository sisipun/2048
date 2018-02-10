package io.kadach.model;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Random;

public class GameField {

    private GameBox[][] fieldMatrix;

    public GameField() {
        fieldMatrix = new GameBox[4][4];
        gennerateBoxes();
        printMatrix();
    }

    public GameBox[][] getFieldMatrix() {
        return fieldMatrix;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            upPressed();
            gennerateBoxes();
            printMatrix();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            leftPressed();
            gennerateBoxes();
            printMatrix();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            rightPressed();
            gennerateBoxes();
            printMatrix();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            downPressed();
            gennerateBoxes();
            printMatrix();
        }
    }

    private void leftPressed() {
        for (int i = 0; i < fieldMatrix.length; i++) {
            for (int j = 1; j < fieldMatrix[i].length; j++) {
                if (fieldMatrix[i][j - 1] == null) {
                    for (int k = j; k < fieldMatrix[i].length; k++) {
                        fieldMatrix[i][k - 1] = fieldMatrix[i][k];
                        fieldMatrix[i][k] = null;
                    }
                    continue;
                }
                if (fieldMatrix[i][j] == null) {
                    continue;
                }
                if (fieldMatrix[i][j].getType() == fieldMatrix[i][j - 1].getType()) {
                    fieldMatrix[i][j - 1] = new GameBox(fieldMatrix[i][j].getType().getNextType());
                    fieldMatrix[i][j] = null;
                }
            }
        }
    }

    private void upPressed() {
        for (int j = 0; j < fieldMatrix[0].length; j++) {
            for (int i = 1; i < fieldMatrix.length; i++) {
                if (fieldMatrix[i - 1][j] == null) {
                    for (int k = i; k < fieldMatrix.length; k++) {
                        fieldMatrix[k - 1][j] = fieldMatrix[k][j];
                        fieldMatrix[k][j] = null;
                    }
                    continue;
                }
                if (fieldMatrix[i][j] == null) {
                    continue;
                }
                if (fieldMatrix[i][j].getType() == fieldMatrix[i - 1][j].getType()) {
                    fieldMatrix[i - 1][j] = new GameBox(fieldMatrix[i][j].getType().getNextType());
                    fieldMatrix[i][j] = null;
                }
            }
        }
    }

    private void downPressed() {
        for (int j = 0; j < fieldMatrix[0].length; j++) {
            for (int i = fieldMatrix.length - 2; i >= 0; i--) {
                if (fieldMatrix[i + 1][j] == null) {
                    for (int k = i; k >= 0; k--) {
                        fieldMatrix[k + 1][j] = fieldMatrix[k][j];
                        fieldMatrix[k][j] = null;
                    }
                    continue;
                }
                if (fieldMatrix[i][j] == null) {
                    continue;
                }
                if (fieldMatrix[i][j].getType() == fieldMatrix[i + 1][j].getType()) {
                    fieldMatrix[i + 1][j] = new GameBox(fieldMatrix[i][j].getType().getNextType());
                    fieldMatrix[i][j] = null;
                }
            }
        }
    }

    private void rightPressed() {
        for (int i = 0; i < fieldMatrix.length; i++) {
            for (int j = fieldMatrix[i].length - 2; j >= 0; j--) {
                if (fieldMatrix[i][j + 1] == null) {
                    for (int k = j; k >= 0; k--) {
                        fieldMatrix[i][k + 1] = fieldMatrix[i][k];
                        fieldMatrix[i][k] = null;
                    }
                    continue;
                }
                if (fieldMatrix[i][j] == null) {
                    continue;
                }
                if (fieldMatrix[i][j].getType() == fieldMatrix[i][j + 1].getType()) {
                    fieldMatrix[i][j + 1] = new GameBox(fieldMatrix[i][j].getType().getNextType());
                    fieldMatrix[i][j] = null;
                }
            }
        }
    }

    private void printMatrix() {
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

    private void gennerateBoxes() {
        int count = 2;
        Random random = new Random();
        while (count != 0) {
            int i = random.nextInt(fieldMatrix.length - 1);
            int j = random.nextInt(fieldMatrix[0].length - 1);
            if (fieldMatrix[i][j] == null) {
                fieldMatrix[i][j] = new GameBox(GameBoxType._2);
                count --;
            }
        }
    }

}

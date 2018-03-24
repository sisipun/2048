package io.kadach.listener;


import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import io.kadach.model.ChangeFieldDestination;
import io.kadach.model.GameField;

public class GameScreenGestureListener implements GestureDetector.GestureListener {

    private final GameField gameField;


    public GameScreenGestureListener(GameField gameField) {
        this.gameField = gameField;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                gameField.changeField(ChangeFieldDestination.RIGHT);
            } else  {
                gameField.changeField(ChangeFieldDestination.LEFT);
            }
        } else {
            if (velocityY > 0) {
                gameField.changeField(ChangeFieldDestination.DOWN);
            } else {
                gameField.changeField(ChangeFieldDestination.UP);
            }
        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}

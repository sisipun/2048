package io.kadach.controll;


import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import io.kadach.model.GameField;

import static io.kadach.model.ChangeFieldDestination.DOWN;
import static io.kadach.model.ChangeFieldDestination.LEFT;
import static io.kadach.model.ChangeFieldDestination.RIGHT;
import static io.kadach.model.ChangeFieldDestination.UP;

public class GameFieldListener extends ActorGestureListener {

    private final GameField gameField;


    public GameFieldListener(GameField gameField) {
        this.gameField = gameField;
    }

    @Override
    public void fling(InputEvent event, float velocityX, float velocityY, int button) {
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                gameField.changeField(RIGHT);
            } else  {
                gameField.changeField(LEFT);
            }
        } else {
            if (velocityY > 0) {
                gameField.changeField(UP);
            } else {
                gameField.changeField(DOWN);
            }
        }
    }

}

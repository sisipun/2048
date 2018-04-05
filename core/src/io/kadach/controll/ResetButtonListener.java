package io.kadach.controll;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import io.kadach.model.GameField;

public class ResetButtonListener extends ClickListener {

    private final GameField gameField;


    public ResetButtonListener(GameField gameField) {
        this.gameField = gameField;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        gameField.reset();
    }

}
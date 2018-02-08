package io.kadach.screen;


import com.badlogic.gdx.ScreenAdapter;

import io.kadach.model.GameField;

public class GameScreen extends ScreenAdapter {

    private GameField gameField;

    public GameScreen() {
        this.gameField = new GameField();
    }

    @Override
    public void render(float delta) {
    }

}

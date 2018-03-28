package io.kadach.view;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import static io.kadach.util.Constants.GAME_FIELD_SIZE;

public class ChooseSizeScreen extends BaseScreen {

    public ChooseSizeScreen(Game game) {
        super(game);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game, GAME_FIELD_SIZE));
        }
    }

}

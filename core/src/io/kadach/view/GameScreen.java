package io.kadach.view;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import io.kadach.model.ChangeFieldDestination;
import io.kadach.model.GameFieldSize;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;
import static io.kadach.util.Constants.PREFERENCES_HIGH_SCORE_KEY;
import static io.kadach.util.Constants.PREFERENCES_KEY;

public class GameScreen extends BaseScreen {

    private int highScore;
    private final Preferences preferences;
    private final GameStage stage;

    public GameScreen(Game game, GameFieldSize size) {
        super(game);
        this.stage = new GameStage(size);
        this.preferences = Gdx.app.getPreferences(PREFERENCES_KEY);
        this.highScore = preferences.getInteger(PREFERENCES_HIGH_SCORE_KEY);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    protected void update(Float delta) {
        if (stage.getGameField().getScore() > highScore) {
            highScore = stage.getGameField().getScore();
            preferences.putInteger(PREFERENCES_HIGH_SCORE_KEY, highScore);
            preferences.flush();
        }
    }

    @Override
    protected void render() {
        stage.draw();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(UP)) stage.getGameField().changeField(ChangeFieldDestination.UP);
        if (Gdx.input.isKeyJustPressed(LEFT)) stage.getGameField().changeField(ChangeFieldDestination.LEFT);
        if (Gdx.input.isKeyJustPressed(RIGHT)) stage.getGameField().changeField(ChangeFieldDestination.RIGHT);
        if (Gdx.input.isKeyJustPressed(DOWN)) stage.getGameField().changeField(ChangeFieldDestination.DOWN);
    }

    @Override
    protected void screenDispose() {
        stage.dispose();
    }

}

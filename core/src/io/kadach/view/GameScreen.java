package io.kadach.view;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.kadach.controll.GameFieldListener;
import io.kadach.controll.ResetButtonListener;
import io.kadach.controll.ResetLastActionButtonListener;
import io.kadach.model.GameField;
import io.kadach.model.GameFieldSize;

import static io.kadach.util.Constants.GAME_SIZE;
import static io.kadach.util.Constants.PREFERENCES_HIGH_SCORE_KEY;
import static io.kadach.util.Constants.PREFERENCES_KEY;

public class GameScreen extends BaseScreen {

    private int highScore;
    private final Preferences preferences;
    private final Stage stage;

    public GameScreen(Game game, GameFieldSize size) {
        super(game);
        this.stage = new Stage(size);
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
    protected void screenDispose() {
        stage.dispose();
    }

    public class Stage extends com.badlogic.gdx.scenes.scene2d.Stage {

        private GameField gameField;


        public GameField getGameField() {
            return gameField;
        }

        public Stage(GameFieldSize size) {
            super(new FitViewport(GAME_SIZE, GAME_SIZE));

            this.gameField = new GameField(size);
            gameField.setPosition(0, 0);
            gameField.setSize(getViewport().getWorldWidth(), getViewport().getWorldHeight());

            ImageButton resetLastActionButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("reset-last.png"))));
            resetLastActionButton.setPosition(getViewport().getWorldWidth() * 0.9f, getViewport().getWorldHeight() * 0.9f);
            resetLastActionButton.setSize(getViewport().getWorldWidth() / 10, getViewport().getWorldHeight() / 10);

            ImageButton resetButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("reset.png"))));
            resetButton.setPosition(getViewport().getWorldWidth() * 0.8f, getViewport().getWorldHeight() * 0.9f);
            resetButton.setSize(getViewport().getWorldWidth() / 10, getViewport().getWorldHeight() / 10);

            gameField.addListener(new GameFieldListener(gameField));
            resetLastActionButton.addListener(new ResetLastActionButtonListener(gameField));
            resetButton.addListener(new ResetButtonListener(gameField));

            this.addActor(gameField);
            this.addActor(resetLastActionButton);
            this.addActor(resetButton);
        }

    }

}

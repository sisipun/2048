package io.kadach.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import io.kadach.model.GameBox;
import io.kadach.model.GameField;
import io.kadach.util.Constants;

import static io.kadach.util.Constants.GAME_HEIGHT;
import static io.kadach.util.Constants.GAME_WIDTH;
import static io.kadach.util.Constants.PREFERENCES_HIGH_SCORE_KEY;
import static io.kadach.util.Constants.PREFERENCES_KEY;

public class GameScreen extends ScreenAdapter {

    private final GameField gameField;
    private final BitmapFont font;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private int highScore;
    private final Preferences preferences;

    public GameScreen() {
        this.gameField = new GameField();
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.preferences = Gdx.app.getPreferences(PREFERENCES_KEY);
        this.highScore = preferences.getInteger(PREFERENCES_HIGH_SCORE_KEY);
        camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        gameField.handleInput();

        if (gameField.getScore() > highScore) {
            highScore = gameField.getScore();
            preferences.putInteger(PREFERENCES_HIGH_SCORE_KEY, highScore);
            preferences.flush();
        }

        batch.begin();
        font.draw(
                batch,
                String.format(Constants.SCORE_MESSAGE_TEMPLATE, gameField.getScore(), highScore),
                camera.viewportWidth / 2,
                camera.viewportHeight - (GAME_HEIGHT / 16),
                0,
                Align.center,
                false
        );
        GameBox[][] fieldMatrix = gameField.getFieldMatrix();
        for (int i = 0; i < fieldMatrix.length; i++) {
            for (int j = 0; j < fieldMatrix[i].length; j++) {
                if (null == fieldMatrix[i][j]) {
                    continue;
                }
                font.draw(
                        batch,
                        fieldMatrix[i][j].getType().name(),
                        ((camera.viewportWidth / 4) * (j + 1)) - (GAME_WIDTH / 8),
                        ((camera.viewportHeight / 4) * (4 - i)) - (GAME_HEIGHT / 8),
                        0,
                        Align.center,
                        false
                );
            }
        }
        if (gameField.isGameOver()) {
            font.draw(
                    batch,
                    String.format(Constants.GAME_OVER_MESSAGE_TEMPLATE, gameField.getScore()),
                    camera.viewportWidth / 2,
                    camera.viewportHeight / 2,
                    0,
                    Align.center,
                    false
            );
        }
        batch.end();
    }

}

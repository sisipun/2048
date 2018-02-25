package io.kadach.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

import io.kadach.model.GameBox;
import io.kadach.model.GameField;
import io.kadach.util.Constants;

import static io.kadach.util.Constants.GAME_HEIGHT;
import static io.kadach.util.Constants.GAME_WIDTH;
import static io.kadach.util.Constants.PREFERENCES_HIGH_SCORE_KEY;
import static io.kadach.util.Constants.PREFERENCES_KEY;

public class GameScreen extends BaseScreen {

    private final GameField gameField;
    private final BitmapFont font;
    private int highScore;
    private final Preferences preferences;

    public GameScreen() {
        this.gameField = new GameField();
        this.font = new BitmapFont();
        this.preferences = Gdx.app.getPreferences(PREFERENCES_KEY);
        this.highScore = preferences.getInteger(PREFERENCES_HIGH_SCORE_KEY);
    }

    @Override
    protected void update(Float delta) {
        if (gameField.getScore() > highScore) {
            highScore = gameField.getScore();
            preferences.putInteger(PREFERENCES_HIGH_SCORE_KEY, highScore);
            preferences.flush();
        }
    }

    @Override
    protected void render() {
        font.draw(
                batch,
                String.format(Constants.SCORE_MESSAGE_TEMPLATE, gameField.getScore(), highScore),
                camera.viewportWidth / 2 - camera.viewportWidth / 4,
                camera.viewportHeight - (GAME_HEIGHT / 16),
                0,
                Align.center,
                false
        );
        font.draw(
                batch,
                String.format(Constants.RESET_ATTEMPT_COUNT_MESSAGE_TEMPLATE, gameField.getResetAttemptCount()),
                camera.viewportWidth / 2 + camera.viewportWidth / 4,
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
    }

    @Override
    protected void handleInput() {
        gameField.handleInput();
    }

    @Override
    protected void screenDispose() {
        font.dispose();
    }

}

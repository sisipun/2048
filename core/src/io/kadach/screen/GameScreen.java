package io.kadach.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

import io.kadach.model.ChangeFieldDestination;
import io.kadach.model.GameBox;
import io.kadach.model.GameField;
import io.kadach.util.Constants;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.Q;
import static com.badlogic.gdx.Input.Keys.R;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;
import static io.kadach.util.Constants.BOX_SIZE;
import static io.kadach.util.Constants.GAME_FIELD_SIZE;
import static io.kadach.util.Constants.GAME_SIZE;
import static io.kadach.util.Constants.PREFERENCES_HIGH_SCORE_KEY;
import static io.kadach.util.Constants.PREFERENCES_KEY;

public class GameScreen extends BaseScreen {

    private final BitmapFont font;
    private GameField gameField;
    private int highScore;
    private final Preferences preferences;

    public GameScreen() {
        this.font = new BitmapFont();
        this.gameField = new GameField(GAME_FIELD_SIZE);
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
        batch.draw(
                gameField.getTexture(),
                0,
                0,
                GAME_SIZE,
                GAME_SIZE
        );
        /*font.draw(
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
        );*/
        GameBox[][] fieldMatrix = gameField.getFieldMatrix();
        for (int i = 0; i < fieldMatrix.length; i++) {
            for (int j = 0; j < fieldMatrix[i].length; j++) {
                if (null == fieldMatrix[i][j]) {
                    continue;
                }
                batch.draw(
                        fieldMatrix[i][j].getType().getTexture(),
                        ((GAME_SIZE / 4) * (j + 1)) - (GAME_SIZE / 8) - (BOX_SIZE / 2),
                        ((GAME_SIZE / 4) * (4 - i)) - (GAME_SIZE / 8) - (BOX_SIZE / 2),
                        BOX_SIZE,
                        BOX_SIZE
                );
                /*font.draw(
                        batch,
                        fieldMatrix[i][j].getType().name(),
                        ((camera.viewportWidth / 4) * (j + 1)) - (GAME_WIDTH / 8),
                        ((camera.viewportHeight / 4) * (4 - i)) - (GAME_HEIGHT / 8),
                        0,
                        Align.center,
                        false
                );*/
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
        if (Gdx.input.isKeyJustPressed(R)) gameField.resetLastAction();
        if (Gdx.input.isKeyJustPressed(Q)) gameField = new GameField(GAME_FIELD_SIZE);
        if (Gdx.input.isKeyJustPressed(UP)) gameField.changeField(ChangeFieldDestination.UP);
        if (Gdx.input.isKeyJustPressed(LEFT)) gameField.changeField(ChangeFieldDestination.LEFT);
        if (Gdx.input.isKeyJustPressed(RIGHT)) gameField.changeField(ChangeFieldDestination.RIGHT);
        if (Gdx.input.isKeyJustPressed(DOWN)) gameField.changeField(ChangeFieldDestination.DOWN);
    }

    @Override
    protected void screenDispose() {
        font.dispose();
    }

}

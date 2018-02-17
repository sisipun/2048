package io.kadach.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

import io.kadach.model.GameBox;
import io.kadach.model.GameField;

import static io.kadach.util.Constants.GAME_HEIGHT;
import static io.kadach.util.Constants.GAME_WIDTH;

public class GameScreen extends ScreenAdapter {

    private final GameField gameField;
    private final BitmapFont font;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;

    public GameScreen() {
        this.gameField = new GameField();
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME_WIDTH, GAME_HEIGHT);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
//        Gdx.app.log("", "x: " + camera.position.x + " y: " + camera.position.y + " vW: " + camera.viewportWidth + " vH: " + camera.viewportHeight);

        gameField.handleInput();

        batch.begin();
        GameBox[][] fieldMatrix = gameField.getFieldMatrix();
        for (int i = 0; i < fieldMatrix.length; i++) {
            for (int j = 0; j < fieldMatrix[i].length; j++) {
                if (null == fieldMatrix[i][j]) {
                    continue;
                }
                font.draw(
                        batch,
                        fieldMatrix[i][j].getType().name(),
                        ((camera.viewportWidth / 4)  * (j + 1)) - 75,
                        ((camera.viewportHeight / 4) * (4 - i)) - 100,
                        0,
                        Align.center,
                        false
                );
            }
        }
        batch.end();
    }

}

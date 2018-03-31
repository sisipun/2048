package io.kadach.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static io.kadach.util.Constants.GAME_SIZE;


public class BaseScreen extends ScreenAdapter {

    protected final Game game;
    protected final SpriteBatch batch;
    protected final BitmapFont font;
    protected final OrthographicCamera camera;

    public BaseScreen(Game game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME_SIZE, GAME_SIZE);
    }

    protected void update(Float delta) {}

    protected void render() {}

    protected void handleInput() {}

    protected void screenDispose() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        render();
        handleInput();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        screenDispose();
    }

}

package io.kadach.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;


public class BaseScreen extends ScreenAdapter {

    protected final Game game;

    public BaseScreen(Game game) {
        this.game = game;
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
        render();
        handleInput();
    }

    @Override
    public void dispose() {
        screenDispose();
    }

}

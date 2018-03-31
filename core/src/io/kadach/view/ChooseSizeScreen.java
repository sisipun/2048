package io.kadach.view;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import io.kadach.model.GameFieldSize;

import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.SPACE;

public class ChooseSizeScreen extends BaseScreen {

    private Array<GameFieldSize> sizes;
    private int sizeIndex;

    public ChooseSizeScreen(Game game) {
        super(game);
        sizes = Array.with(GameFieldSize.values());
        sizeIndex = 1;
    }

    @Override
    protected void render() {
        font.draw(
                batch,
                sizes.get(sizeIndex).name(),
                camera.viewportWidth / 2 ,
                camera.viewportHeight / 2,
                0,
                Align.center,
                false
        );
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(SPACE)) {
            game.setScreen(new GameScreen(game, sizes.get(sizeIndex)));
        }
        if (Gdx.input.isKeyJustPressed(LEFT)) {
            sizeIndex--;
            if (sizeIndex < 0) {
                sizeIndex = sizes.size - 1;
            }
        }
        if (Gdx.input.isKeyJustPressed(RIGHT)) {
            sizeIndex++;
            if (sizeIndex > sizes.size - 1) {
                sizeIndex = 0;
            }
        }
    }

}

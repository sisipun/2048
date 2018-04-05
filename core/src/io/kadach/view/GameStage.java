package io.kadach.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.kadach.controll.GameFieldListener;
import io.kadach.controll.ResetButtonListener;
import io.kadach.controll.ResetLastActionButtonListener;
import io.kadach.model.GameField;
import io.kadach.model.GameFieldSize;

import static io.kadach.util.Constants.GAME_SIZE;

public class GameStage extends Stage {

    private GameField gameField;

    public GameField getGameField() {
        return gameField;
    }

    public GameStage(GameFieldSize size) {
        super(new FitViewport(GAME_SIZE, GAME_SIZE));

        this.gameField = new GameField(size);

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

package io.kadach;

import com.badlogic.gdx.Game;

import io.kadach.model.GameFieldSize;
import io.kadach.view.GameScreen;

public class Color2048 extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen(this, GameFieldSize._4x4));
	}

}

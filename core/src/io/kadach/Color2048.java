package io.kadach;

import com.badlogic.gdx.Game;

import io.kadach.view.ChooseSizeScreen;

public class Color2048 extends Game {

	@Override
	public void create () {
		setScreen(new ChooseSizeScreen(this));
	}

}

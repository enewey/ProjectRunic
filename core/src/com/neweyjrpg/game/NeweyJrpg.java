package com.neweyjrpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.neweyjrpg.controller.InputController;
import com.neweyjrpg.graphic.TileGraphic;

public class NeweyJrpg extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Sprite chara;
	InputController input;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("dungeon.png");
		chara = new Sprite(new Texture("charas.png"), 0, 52, 16, 16);
		chara.setPosition(20f, 20f);
		input = new InputController();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.input.setInputProcessor(input);
		batch.begin();
		for (int x=0; x<60; x++)
			for (int y=0; y<30; y++)
				batch.draw(new TileGraphic(img, 20, 10), x*16, y*16);
		
		boolean[] dirs = input.getInputs();
		if (dirs[0] && !dirs[2])
			chara.translateY(1f);
		else if (!dirs[0] && dirs[2])
			chara.translateY(-1f);
		
		if (dirs[1] && !dirs[3])
			chara.translateX(1f);
		else if (!dirs[1] && dirs[3])
			chara.translateX(-1f);
		
		chara.draw(batch);
		batch.end();
	}
}

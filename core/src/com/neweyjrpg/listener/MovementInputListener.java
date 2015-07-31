package com.neweyjrpg.listener;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.neweyjrpg.actor.GameActor;

public class MovementInputListener extends InputListener {
		
	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		GameActor actor = (GameActor)event.getListenerActor();
		if (actor.dirs == null)
			return false;
		
		switch (keycode){
			case Keys.UP:
				actor.dirs[0] = true;
				return true;
			case Keys.RIGHT:
				actor.dirs[1] = true;
				return true;
			case Keys.DOWN:
				actor.dirs[2] = true;
				return true;
			case Keys.LEFT:
				actor.dirs[3] = true;
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean keyUp(InputEvent event, int keycode) {
		GameActor actor = (GameActor)event.getListenerActor();
		if (actor.dirs == null)
			return false;
		
		switch (keycode){
			case Keys.UP:
				actor.dirs[0] = false;
				return true;
			case Keys.RIGHT:
				actor.dirs[1] = false;
				return true;
			case Keys.DOWN:
				actor.dirs[2] = false;
				return true;
			case Keys.LEFT:
				actor.dirs[3] = false;
				return true;
		}
	
		return false;
	}

}

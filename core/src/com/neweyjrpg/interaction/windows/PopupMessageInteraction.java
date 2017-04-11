package com.neweyjrpg.interaction.windows;

import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.game.GameScene;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interfaces.IHandlesInteraction;
import com.neweyjrpg.manager.Manager;
import com.neweyjrpg.manager.WindowManager;
import com.neweyjrpg.window.PopupWindow;

public class PopupMessageInteraction extends WindowInteraction {

	private GameActor source;	
	public PopupMessageInteraction(IHandlesInteraction scene, String str, GameActor source) {
		super(scene);
		this.source = source;
	}
	
	@Override
	public Interaction process(Manager m) {
		super.process(m);
		float x = Math.min(Math.max(source.getX() + GameScene.getScroll().x, 1), Constants.GAME_WIDTH-1);
		float y = Math.min(Math.max(source.getY() + GameScene.getScroll().y, 1), Constants.GAME_HEIGHT-1);
		Vector2 v = new Vector2(x,y);
		
		((WindowManager)m).addWindow(
				new PopupWindow(this, 0, 0, Constants.GAME_WIDTH, 80, v, Constants.POPUP_DURATION));
		
		return this;
	}

	@Override
	public Object getData() {
		return null;
	}

}

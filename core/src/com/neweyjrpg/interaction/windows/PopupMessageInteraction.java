package com.neweyjrpg.interaction.windows;

import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.actor.GameActor;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.game.GameScene;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.manager.Manager;
import com.neweyjrpg.manager.WindowManager;
import com.neweyjrpg.window.PopupWindow;

public class PopupMessageInteraction extends WindowInteraction {

	private GameActor source;
	private Vector2 scroll;
	
	public PopupMessageInteraction(GameScene scene, String str, GameActor source) {
		super(scene);
		this.source = source;
		this.scroll = scene.getScroll();
	}
	
	@Override
	public Interaction process(Manager m) {
		super.process(m);
		((WindowManager)m).addWindow(
				new PopupWindow(this, 0, 0, Constants.GAME_WIDTH, 80, 
						new Vector2(source.getX(), source.getY()).add(scroll), 
						Constants.POPUP_DURATION));
		
		return this;
	}

	@Override
	public Object getData() {
		return null;
	}

}

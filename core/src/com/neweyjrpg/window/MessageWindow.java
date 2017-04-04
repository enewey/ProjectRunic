package com.neweyjrpg.window;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums;
import com.neweyjrpg.game.Assets;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.sequences.MessageSequence;

public class MessageWindow extends GameWindow {

	private int letterDelay = Constants.LETTER_DELAY;
	private int idleTime = Constants.IDLE_TIME; //TODO: make these constants
	private int padding = Constants.WINDOW_PADDING;

	private BitmapFont font;
	private MessageSequence message;
	public MessageSequence getSequence() { return this.message; }
	
	public MessageWindow(Interaction i, int x, int y, int width, int height, String font, String str) {
		super(i, x, y, width, height, Enums.Priority.Above);
		this.message = new MessageSequence(str, letterDelay, idleTime);
		this.font = Assets.loadFont(font);
	}
	
	@Override
	public void draw(Batch batch, float deltaTime) {		
		super.draw(batch, deltaTime);
		String m = message.stepMessage();
		font.draw(batch, m, x + padding, y+height-padding);
	}
	
	public boolean isDone() {
		return message.isDone();
	}
	
	@Override
	public void dispose() {
		//this.font.dispose();
		super.dispose();
	}

	@Override
	public void interact() {
		if (this.isDone())
			this.dispose();
		else {
			this.getSequence().skip();
		}
	}

}

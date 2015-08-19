package com.neweyjrpg.window;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.neweyjrpg.models.MessageSequence;

public class MessageWindow extends GameWindow {

	private static int repeats = 3;
	private static int idleTime = 60;
	private static int padding = 5;

	private BitmapFont font;
	private MessageSequence message;
	
	public MessageWindow(int x, int y, int width, int height, String str) {
		super(x, y, width, height);
		this.message = new MessageSequence(str, repeats, idleTime);
		this.font = new BitmapFont();
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

}

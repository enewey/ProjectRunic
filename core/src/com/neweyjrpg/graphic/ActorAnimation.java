package com.neweyjrpg.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neweyjrpg.constants.Constants;
import com.neweyjrpg.enums.Enums.Direction;

public class ActorAnimation {
	
	private Animation upAnim;
	private Animation rightAnim;
	private Animation downAnim;
	private Animation leftAnim;
	
	private float xPos;
	private float yPos;
	
	/**
	 * 
	 * @param charaSet
	 * @param pos - currently laid out as: 0 1 2 3 /n 4 5 6 7
	 */
	public ActorAnimation(Texture charaSet, int pos) {
		int x = (pos % 4) * Constants.CHARA_WIDTH;
		int y = ((int)(pos / 4)) * Constants.CHARA_HEIGHT;
		buildFrames(charaSet, x, y);
	}

	private void buildFrames(Texture charaSet, int x, int y){
		for (int i = 0; i < 4; i++) {
			ActorGraphic[] tr = new ActorGraphic[Constants.FRAME_COUNT];
			for (int f=0; f < Constants.FRAME_COUNT-1; f++)
			{
				if (f==1) tr[Constants.FRAME_COUNT-1] = new ActorGraphic(charaSet, x+f, y+i); //TODO: change this
				tr[f] = new ActorGraphic(charaSet, x+f, y+i);
			}
			switch (i) {
				case 0: //Up
					upAnim = new Animation(Constants.FRAME_DURATION, tr);
					break;
				case 1:	//Right
					rightAnim = new Animation(Constants.FRAME_DURATION, tr);
					break;
				case 2: //Down
					downAnim = new Animation(Constants.FRAME_DURATION, tr);
					break;
				case 3: //Left
					leftAnim = new Animation(Constants.FRAME_DURATION, tr);
					break;
				default:
					break;
			}
		}
	}
	
	public Animation getAnim(Direction dir) {
		switch(dir){
			case UP:
				return upAnim;
			case RIGHT: 
				return rightAnim;
			case DOWN:
				return downAnim;
			default:
				return leftAnim;
		}
	}
	
	public void setPosition(float x, float y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public void translate(float x, float y) {
		this.xPos += x;
		this.yPos += y;
		
	}
	
	public float[] getPosition() {
		return new float[] { xPos, yPos };
	}
	
	public void draw(Batch batch, float deltaTime, Direction dir, boolean loop) {
		switch(dir){
			case UP:
				batch.draw(upAnim.getKeyFrame(deltaTime, loop), this.xPos, this.yPos);
				break;
			case RIGHT: 
				batch.draw(rightAnim.getKeyFrame(deltaTime, loop), this.xPos, this.yPos);
				break;
			case DOWN:
				batch.draw(downAnim.getKeyFrame(deltaTime, loop), this.xPos, this.yPos);
				break;
			default:
				batch.draw(leftAnim.getKeyFrame(deltaTime, loop), this.xPos, this.yPos);
				break;
		}
	}
	
	
}

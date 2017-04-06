package com.neweyjrpg.graphic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neweyjrpg.enums.Enums.Dir;

public class EffectAnimation {
	
	protected Animation upAnim;
	protected Animation rightAnim;
	protected Animation downAnim;
	protected Animation leftAnim;
	
	public Animation getAnim(Dir dir) {
		switch(dir){
			case UP:
				return upAnim;
			case RIGHT: 
				return rightAnim;
			case DOWN:
				return downAnim;
			case LEFT:
			default:
				return leftAnim;
		}
	}
		
	public TextureRegion getFrame(float stateTime, Dir dir, boolean loop) {
		switch(dir){
		case UP:
			return upAnim.getKeyFrame(stateTime, loop);
		case RIGHT: 
			return rightAnim.getKeyFrame(stateTime, loop);
		case DOWN:
			return downAnim.getKeyFrame(stateTime, loop);
		case LEFT:
		default:
			return leftAnim.getKeyFrame(stateTime, loop);
		}
	}
	
	public void dispose() {
		for (TextureRegion t : this.downAnim.getKeyFrames()) {
			t.getTexture().dispose();
		}
		for (TextureRegion t : this.upAnim.getKeyFrames()) {
			t.getTexture().dispose();
		}
		for (TextureRegion t : this.rightAnim.getKeyFrames()) {
			t.getTexture().dispose();
		}
		for (TextureRegion t : this.leftAnim.getKeyFrames()) {
			t.getTexture().dispose();
		}
		
	}
}

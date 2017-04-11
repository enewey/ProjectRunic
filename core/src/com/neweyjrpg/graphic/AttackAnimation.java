package com.neweyjrpg.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.neweyjrpg.constants.Constants;

public class AttackAnimation extends EffectAnimation {

	private Texture graphic;
	private float speed;
	
	public AttackAnimation(float speed) {
		this.speed = speed;
		this.graphic = new Texture("arrow-swing.png");
		this.buildSwordSwing();
	}
		
	protected void buildSwordSwing() {
		for (int i = 0; i < 4; i++) {
			Sprite[] tr = new Sprite[Constants.EFFECT_FRAMES];
			for (int f=0; f < Constants.EFFECT_FRAMES; f++)
			{
				int x = (f > 3 ? 3 : f);
				tr[f] = new Sprite(graphic, x*32, i*32, 32, 32); //TODO: make effect graphics more frames frames
			}
			switch (i) {
				case 0: //Up
					this.upAnim = new Animation(Constants.FRAME_DURATION / speed, tr);
					break;
				case 1:	//Right
					this.rightAnim = new Animation(Constants.FRAME_DURATION / speed, tr);
					break;
				case 2: //Down
					this.downAnim = new Animation(Constants.FRAME_DURATION / speed, tr);
					break;
				case 3: //Left
					this.leftAnim = new Animation(Constants.FRAME_DURATION / speed, tr);
					break;
				default:
					break;
			}
		}
	}
}

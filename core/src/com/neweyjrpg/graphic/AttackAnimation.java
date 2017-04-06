package com.neweyjrpg.graphic;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neweyjrpg.constants.Constants;

public class AttackAnimation extends EffectAnimation {

	private Texture graphic;
	
	public AttackAnimation() {
//		Pixmap pxm = new Pixmap(128,128,Format.RGBA8888);
//		pxm.setColor(Color.GREEN);
//		pxm.fillRectangle(0,0,128,128);
//		this.testGraphic = new TextureRegion(new Texture(pxm)); //TODO: TEXUTRES NOT SHOWING UP>???
//		pxm.dispose();
		
		this.graphic = new Texture("arrow-swing.png");
		this.buildSwordSwing();
	}
		
	protected void buildSwordSwing() {
		for (int i = 0; i < 4; i++) {
			Sprite[] tr = new Sprite[4];
			//TextureRegion[] tr = new TextureRegion[4];
			for (int f=0; f < 4; f++)
			{
				tr[f] = new Sprite(graphic, f*32, i*32, 32, 32);
			}
			switch (i) {
				case 0: //Up
					this.upAnim = new Animation(Constants.FRAME_DURATION, tr);
					break;
				case 1:	//Right
					this.rightAnim = new Animation(Constants.FRAME_DURATION, tr);
					break;
				case 2: //Down
					this.downAnim = new Animation(Constants.FRAME_DURATION, tr);
					break;
				case 3: //Left
					this.leftAnim = new Animation(Constants.FRAME_DURATION, tr);
					break;
				default:
					break;
			}
		}
	}
}

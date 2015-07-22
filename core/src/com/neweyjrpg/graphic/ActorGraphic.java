package com.neweyjrpg.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.neweyjrpg.constants.Constants;

public class ActorGraphic extends TextureRegion {
	
	public ActorGraphic(Texture charaSet, int x, int y)
	{
		super(charaSet, 
				x*(Constants.CHARA_WIDTH + Constants.CHARA_MARGIN),
				y*(Constants.CHARA_HEIGHT + Constants.CHARA_MARGIN),
				Constants.CHARA_WIDTH,
				Constants.CHARA_HEIGHT);
		System.out.println("x/y:"+x*(Constants.CHARA_WIDTH + Constants.CHARA_MARGIN)+"/"+
				y*(Constants.CHARA_HEIGHT + Constants.CHARA_MARGIN));
	}

}

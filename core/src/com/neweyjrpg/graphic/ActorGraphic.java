package com.neweyjrpg.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.neweyjrpg.constants.Constants;

public class ActorGraphic extends Sprite {
	
	public ActorGraphic(Texture charaSet, int x, int y)
	{
		super(charaSet, 
				x*(Constants.CHARA_WIDTH + Constants.CHARA_MARGIN),
				y*(Constants.CHARA_HEIGHT + Constants.CHARA_MARGIN),
				Constants.CHARA_WIDTH,
				Constants.CHARA_HEIGHT);
	}

}

package com.neweyjrpg.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.neweyjrpg.enums.Enums.PhysicalState;

public class BlockBody {

	private PhysicalState type;
	public PhysicalState getType() {
		return type;
	}

	private Rectangle bounds;
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Vector2 getCenter() {
		Vector2 ret = new Vector2();
		this.bounds.getCenter(ret);
		return ret;
	}

	public BlockBody(PhysicalState type, Rectangle bounds) {
		this.type = type;
		this.bounds = bounds;
	}
	
	public Vector2 moveOff(BlockBody other) {
		if (this.getBounds().overlaps(other.getBounds())) {
			
			float xdiff = this.getBounds().x - other.getBounds().x;
			float ydiff = this.getBounds().y - other.getBounds().y;
			
			if (Math.abs(xdiff) < Math.abs(ydiff)) {
				float twidth  = this.getBounds().width;
				float owidth  = other.getBounds().width;
				if (xdiff < other.getBounds().width / 2.0) {
					//eject left
					return new Vector2(-(twidth + xdiff), 0);
				} else {
					//eject right
					return new Vector2((owidth - xdiff), 0);
				}
				
			} else {
				float theight = this.getBounds().height;
				float oheight = other.getBounds().height;
				if (ydiff < other.getBounds().height / 2.0) {
					//eject down
					return new Vector2(-(theight + ydiff), 0);
				} else {
					//eject up
					return new Vector2((oheight - ydiff), 0);
				}
			}
		}
		
		return null;
	}
}

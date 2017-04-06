package com.neweyjrpg.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Overriding the FitViewport in order to have more control over the update method.
 * This is so the Viewport stays centered in the window and maintains a consistent dpi.
 * This game is very pixel precise, so it's important that individual pixels don't get
 * distorted by stretching, etc!
 * @author erichnewey
 *
 */
public class NeweyViewport extends FitViewport {

	public NeweyViewport(float worldWidth, float worldHeight) {
		super(worldWidth, worldHeight);
	}
	
	/**
	 * TAKE BACK CONTROL OF THE VIEWPORT, MY SON
	 * @param gameWidth - Width of the viewport
	 * @param gameHeight - Height of the viewport
	 * @param screenWidth - Width of the window
	 * @param screenHeight - Height of the window
	 */
	public void update (int gameWidth, int gameHeight, int screenWidth, int screenHeight) {
		Vector2 scaled = this.getScaling().apply(getWorldWidth(), getWorldHeight(), gameWidth, gameHeight);
		int viewportWidth = Math.round(scaled.x);
		int viewportHeight = Math.round(scaled.y);

		// Center.
		setScreenBounds((screenWidth - viewportWidth) / 2, (screenHeight - viewportHeight) / 2, viewportWidth, viewportHeight);

		apply(false);
	}

}

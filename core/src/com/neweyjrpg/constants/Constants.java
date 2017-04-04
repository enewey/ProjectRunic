package com.neweyjrpg.constants;

public class Constants {
	
	public final static int CHARA_WIDTH = 24;
	public final static int CHARA_HEIGHT = 32;
	public final static int CHARA_MARGIN = 0;
	
	public final static int TILE_WIDTH = 16;
	public final static int TILE_HEIGHT = 16;
	public final static int TILE_MARGIN = 1;
	
	public final static int GAME_WIDTH = 426;
	public final static int GAME_HEIGHT = 240;
	
	public final static float LOWER_BOUND_X = (GAME_WIDTH / 2.0f) - (GAME_WIDTH / 8.0f) - (CHARA_WIDTH/2);
	public final static float UPPER_BOUND_X = (GAME_WIDTH /2.0f) + (GAME_WIDTH / 8.0f) - (CHARA_WIDTH/2);
	public final static float LOWER_BOUND_Y = (GAME_HEIGHT / 2.0f) - (GAME_HEIGHT / 8.0f) - (CHARA_HEIGHT/2);
	public final static float UPPER_BOUND_Y = (GAME_HEIGHT / 2.0f) + (GAME_HEIGHT / 8.0f) - (CHARA_HEIGHT/2);
	
	//Window properties
	public final static float POPUP_DURATION = 1f;
	
	//Actor Animation
	public final static float FRAME_DURATION = 0.125f;
	public final static int FRAME_COUNT = 4;
	public final static int IDLE_FRAME = 1; //the frame of animation which a character appears standing still
	
	//Physics
	public final static float TIME_STEP = 1 / 60.0f;
	public final static int VELOCITY_ITERATIONS = 8;
	public final static int POSITION_ITERATIONS = 2; 
	
	public final static float MAX_MOVEMENT_SPEED = 2f;
	
	public final static float CHARA_PHYS_WIDTH = 12f;
	public final static float CHARA_PHYS_HEIGHT = 12f;
	
	public final static float CHARA_PHYS_SIZE = (float) Math.sqrt(288);
	
	public final static float DEFAULT_ACTION_SPEED = 0.01f;
}

package com.neweyjrpg.constants;

public class Constants {
	
	public final static int CHARA_WIDTH = 24;
	public final static int CHARA_HEIGHT = 32;
	public final static int CHARA_MARGIN = 0;
	
	public final static int TILE_WIDTH = 16;
	public final static int TILE_HEIGHT = 16;
	public final static int TILE_MARGIN = 1;
	
	public final static int GAME_WIDTH = 320;
	public final static int GAME_HEIGHT = 240;
	
	//Actor Animation
	public final static float FRAME_DURATION = 0.125f;
	public final static int FRAME_COUNT = 4;
	public final static int IDLE_FRAME = 1; //the frame of animation which a character appears standing still
	
	//Physics
	public final static float TIME_STEP = 1 / 60.0f;
	public final static int VELOCITY_ITERATIONS = 6;
	public final static int POSITION_ITERATIONS = 2; 
	
}

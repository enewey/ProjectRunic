package com.neweyjrpg.manager;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.neweyjrpg.enums.Enums.Priority;
import com.neweyjrpg.interaction.Interaction;
import com.neweyjrpg.interfaces.IManagesGraphics;
import com.neweyjrpg.map.GameMap;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public class MapManager extends Manager implements IManagesGraphics {

	private GameMap map;
	public GameMap getMap() { return this.map; }
	
	public MapManager(String file) {
		this.map = new GameMap(file);
	}
	
	@Override
	public boolean handle(Interaction interaction) {
		return false;
	}

	@Override
	public boolean handleButtonPress(int button) {
		return false;
	}

	@Override
	public boolean handleDirectionPress(int button) {
		return false;
	}

	@Override
	public boolean handleButtonState(ButtonInput button) {
		return false;
	}

	@Override
	public boolean handleDirectionState(DirectionalInput dir) {
		return false;
	}

	@Override
	public void draw(float deltaTime, int yaxis, float offsetX, float offsetY, Batch batch, Priority priority) {
		this.map.draw(batch, yaxis, deltaTime, offsetX, offsetY, priority);
	}

	@Override
	public boolean act(float deltaTime) {
		return false;
	}

	@Override
	public void dispose() {
		this.map.dispose();
	}

	@Override
	public void massColorLerp(float r, float g, float b, float a, float factor) {
		map.getColor().lerp(r,g,b,a, factor);
	}
	
	public void block() {}
	public void unblock() {}

	@Override
	public void onInteractionComplete(Interaction i) {
		// TODO Auto-generated method stub
		
	}

}

package com.neweyjrpg.controller;

import com.neweyjrpg.enums.Enums.Dir;
import com.neweyjrpg.interfaces.IProducesInputs;
import com.neweyjrpg.models.ButtonInput;
import com.neweyjrpg.models.DirectionalInput;

public class PatternController implements IProducesInputs {

	private DirectionalInput dirs;
	private Dir[] pattern;
	public void setPattern(Dir[] pattern) { 
		this.pattern = pattern;
	}
	
	private int callCounter;
	
	private boolean looping;
	
	public PatternController(boolean looping) {
		this.looping = looping;
		
		this.callCounter = 0;
		this.dirs = new DirectionalInput();
		this.pattern = new Dir[]{ 
				Dir.UP, Dir.UP, Dir.UP, Dir.UP, Dir.UP, Dir.UP, Dir.UP, Dir.UP, Dir.UP, Dir.UP,
				Dir.RIGHT, Dir.RIGHT, Dir.RIGHT, Dir.RIGHT, Dir.RIGHT, Dir.RIGHT, Dir.RIGHT, Dir.RIGHT, Dir.RIGHT, Dir.RIGHT,
				Dir.DOWN, Dir.DOWN, Dir.DOWN, Dir.DOWN, Dir.DOWN, Dir.DOWN, Dir.DOWN, Dir.DOWN, Dir.DOWN, Dir.DOWN,
				Dir.LEFT, Dir.LEFT, Dir.LEFT, Dir.LEFT, Dir.LEFT, Dir.LEFT, Dir.LEFT, Dir.LEFT, Dir.LEFT, Dir.LEFT
			};
	}
	
	@Override
	public DirectionalInput getDirectionalState() {
		dirs.clear();
		
		if (++callCounter >= pattern.length && looping) {
			callCounter -= pattern.length;
		}
			
		if (callCounter < pattern.length && pattern.length > 0)
			dirs.push(pattern[callCounter]);
		else
			dirs.clear();
		
		return dirs;
	}

	@Override
	public ButtonInput getButtonState() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.neweyjrpg.sequences;

public class MessageSequence extends Sequence<Character> {

	private int idle;
	private int idlingTime;
	private boolean done;
	private String fullMessage;
	public String getMessage() { return this.fullMessage; } 
	public boolean isDone() { return this.done; }
	
	public MessageSequence(String s, int repeats, int idle) {
		this.fullMessage = s;
		this.idle = idle;
		this.idlingTime = 0;
		for (char c : s.toCharArray()) {
			this.add(c, repeats);
			idle += repeats;
		}
	}
	
	public String stepMessage() {
		this.step();
		if (!this.isLooped()) {
			StringBuilder builder = new StringBuilder();
			for (int i=0; i<this.pointer; i++)
				builder.append(this.data.get(i).data);
			return builder.toString();
		}
		else {
			idlingTime++;
			if (idlingTime > idle)
				this.done = true;
			return fullMessage;
		}
		
		
	}
}

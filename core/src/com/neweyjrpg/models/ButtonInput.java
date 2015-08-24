package com.neweyjrpg.models;

public class ButtonInput extends Input {

	public void pushOne() 	{ this.data[0] = true; this.time[0] = System.currentTimeMillis(); }
	public void pushTwo() 	{ this.data[1] = true; this.time[1] = System.currentTimeMillis(); }
	public void pushThree() { this.data[2] = true; this.time[2] = System.currentTimeMillis(); }
	public void pushFour() 	{ this.data[3] = true; this.time[3] = System.currentTimeMillis(); }
	public void pushFive() 	{ this.data[4] = true; this.time[4] = System.currentTimeMillis(); }
	public void pushSix() 	{ this.data[5] = true; this.time[5] = System.currentTimeMillis(); }
	public void pushSeven() { this.data[6] = true; this.time[6] = System.currentTimeMillis(); }
	public void pushEight() { this.data[7] = true; this.time[7] = System.currentTimeMillis(); }
	public void pushNine() 	{ this.data[8] = true; this.time[8] = System.currentTimeMillis(); }
	public void pushTen() 	{ this.data[9] = true; this.time[9] = System.currentTimeMillis(); }
	public void push(int b) { this.data[b] = true; this.time[b] = System.currentTimeMillis(); }
	
	public void liftOne() 	{ this.data[0] = false; this.time[0] = 0; }
	public void liftTwo() 	{ this.data[1] = false; this.time[1] = 0; }
	public void liftThree() { this.data[2] = false; this.time[2] = 0; }
	public void liftFour() 	{ this.data[3] = false; this.time[3] = 0; }
	public void liftFive() 	{ this.data[4] = false; this.time[4] = 0; }
	public void liftSix() 	{ this.data[5] = false; this.time[5] = 0; }
	public void liftSeven() { this.data[6] = false; this.time[6] = 0; }
	public void liftEight() { this.data[7] = false; this.time[7] = 0; }
	public void liftNine() 	{ this.data[8] = false; this.time[8] = 0; }
	public void liftTen() 	{ this.data[9] = false; this.time[9] = 0; }
	public void lift(int b) { this.data[b] = false; this.time[b] = 0; }
	
	public ButtonInput() {
		this.data = new boolean[10];
	}
	
	@Override
	public boolean isEmpty() {
		return !(data[0] || data[1] || data[2] || data[3] || data[4] ||
				 data[5] || data[6] || data[7] || data[8] || data[9]);
	}
}

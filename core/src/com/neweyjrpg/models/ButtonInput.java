package com.neweyjrpg.models;

public class ButtonInput extends Input {

	public void pushOne() 	{ this.data[0] = true; }
	public void pushTwo() 	{ this.data[1] = true; }
	public void pushThree() { this.data[2] = true; }
	public void pushFour() 	{ this.data[3] = true; }
	public void pushFive() 	{ this.data[4] = true; }
	public void pushSix() 	{ this.data[5] = true; }
	public void pushSeven() { this.data[6] = true; }
	public void pushEight() { this.data[7] = true; }
	public void pushNine() 	{ this.data[8] = true; }
	public void pushTen() 	{ this.data[9] = true; }
	public void push(int b) { this.data[b] = true; }
	
	public void liftOne() 	{ this.data[0] = false; }
	public void liftTwo() 	{ this.data[1] = false; }
	public void liftThree() { this.data[2] = false; }
	public void liftFour() 	{ this.data[3] = false; }
	public void liftFive() 	{ this.data[4] = false; }
	public void liftSix() 	{ this.data[5] = false; }
	public void liftSeven() { this.data[6] = false; }
	public void liftEight() { this.data[7] = false; }
	public void liftNine() 	{ this.data[8] = false; }
	public void liftTen() 	{ this.data[9] = false; }
	public void lift(int b) { this.data[b] = false; }
	
	public ButtonInput() {
		this.data = new boolean[10];
	}
	
	@Override
	public boolean isEmpty() {
		return !(data[0] || data[1] || data[2] || data[3] || data[4] ||
				 data[5] || data[6] || data[7] || data[8] || data[9]);
	}
}

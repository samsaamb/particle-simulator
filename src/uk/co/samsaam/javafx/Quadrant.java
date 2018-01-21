package uk.co.samsaam.javafx;

public class Quadrant {
	private double midX;
	private double midY;
	private double quadLength;
	
	public Quadrant(double midX, double midY, double quadLength) { //constructor
		this.midX = midX;
		this.midY = midY;
		this.quadLength = quadLength;
	}
	public double getQuadLength() { //return the length of this quadrant, size = one of the lengths, since it is a square.
		return this.quadLength;
	}
	
	public boolean contains(double x, double y) { //function to check if a body is within this quadrant.
		double halfQuadLength = this.quadLength/2;
		return (x <= this.midX + halfQuadLength &&
				x >= this.midX - halfQuadLength &&
				y <= this.midY + halfQuadLength &&
				y >= this.midY - halfQuadLength);
	}
	
	public Quadrant NW() { //function to return the north west quadrant of this Quadrant.
		double x = this.midX - this.quadLength / 4; //declares x from left to middle of the quadrant
		double y = this.midY + this.quadLength / 4; //declares y from middle to top of quadrant
		double newQuadLength = this.quadLength / 2; //length of the quadrant is half the size of the original quadrant
		Quadrant NW = new Quadrant(x, y, newQuadLength); //construct this new quadrant, calling it NW (north west)
		return NW; //return this quadrant
	}
	
	public Quadrant NE() { //works the same as the NW version
		double x = this.midX + this.quadLength / 4; //declares x from middle to right of the quadrant
		double y = this.midY + this.quadLength / 4; //declares y from middle to top of quadrant 
		double newQuadLength = this.quadLength / 2;
		Quadrant NE = new Quadrant(x, y, newQuadLength); //make a new quadrant from these parameters
		return NE; //return the new quadrant
	}
	
	public Quadrant SW() {
		double x = this.midX - this.quadLength / 4; //declares the x from left to middle of quadrant
		double y = this.midY - this.quadLength / 4; //declares the y from middle to bottom of quadrant
		double newQuadLength = this.quadLength / 2;
		Quadrant SW = new Quadrant(x, y, newQuadLength); //make a new quadrant from these parameters
		return SW; //return the new quadrant
	}
	
	public Quadrant SE() {
		double x = this.midX + this.quadLength / 4; //declares the x from middle to right of quadrant
		double y = this.midY - this.quadLength / 4; //declares the y from middle to bottom of quadrant
		double newQuadLength = this.quadLength / 2;
		Quadrant SE = new Quadrant(x, y, newQuadLength); //make a new quadrant from these parameters
		return SE; //return this new quadrant
	}
}

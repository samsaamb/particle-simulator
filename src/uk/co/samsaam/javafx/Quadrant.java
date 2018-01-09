package uk.co.samsaam.javafx;

public class Quadrant {
	private double midX;
	private double midY;
	private double quadLength;
	
	public Quadrant(double midX, double midY, double quadLength) {
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
		double x = this.midX - this.quadLength/4; //declares x from left to middle of the quadrant
		double y = this.midY + this.quadLength/4; //declares y from middle to top of quadrant
		double newQuadLength = this.quadLength / 2; //length of the quadrant is half the size of the original quadrant
		Quadrant NW = new Quadrant(x, y, newQuadLength); //construct this new quadrant, calling it NW (north west)
		return NW; //return this quadrant
	}
	
	public Quadrant NE() {
		double x = this.midX + this.quadLength/4;
		double y = this.midY + this.quadLength/4;
		double newQuadLength = this.quadLength/2;
		Quadrant NE = new Quadrant(x,y,newQuadLength);
		return NE;
	}
	
	public Quadrant SW() {
		double x = this.midX - this.quadLength/4;
		double y = this.midY - this.quadLength/4;
		double newQuadLength = this.quadLength/2;
		Quadrant SW = new Quadrant(x,y,newQuadLength);
		return SW;
	}
	
	public Quadrant SE() {
		double x = this.midX + this.quadLength/4;
		double y = this.midY - this.quadLength/4;
		double newQuadLength = this.quadLength/2;
		Quadrant SE = new Quadrant(x,y,newQuadLength);
		return SE;
	}
}

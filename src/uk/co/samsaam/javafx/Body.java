package uk.co.samsaam.javafx;

public class Body {
	private double x;
	private double y;
	private int width;
	private int height;
	private double velocity;
	
	public Body(double x, double y, int width, int height, double velocity) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.velocity = velocity;
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getVelocity() {
		return velocity;
	}
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	
	
}

package uk.co.samsaam.javafx;

public class Body {
	private double x;
	private double y;
	private int width;
	private int height;
	private double xvelocity;
	private double yvelocity;
	private double forceX;
	private double forceY;
	private double accelerationX;
	private double accelerationY;
	private double mass;

	public Body(double x, double y, int width, int height, double xvelocity, double yvelocity, double mass) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xvelocity = xvelocity;
		this.yvelocity = yvelocity;
		this.mass = mass;
	}
	
	public double getForceX() {
		return forceX;
	}

	public void setForceX(double forceX) {
		this.forceX = forceX;
	}

	public double getForceY() {
		return forceY;
	}

	public void setForceY(double forceY) {
		this.forceY = forceY;
	}

	public double getAccelerationX() {
		return accelerationX;
	}

	public void setAccelerationX(double accelerationX) {
		this.accelerationX = accelerationX;
	}

	public double getAccelerationY() {
		return accelerationY;
	}

	public void setAccelerationY(double accelerationY) {
		this.accelerationY = accelerationY;
	}
	
	public double getMass() {
		return mass;
	}
	public void setMass(double mass) {
		this.mass = mass;
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
	public double getxVelocity() {
		return xvelocity;
	}
	public void setxVelocity(double xvelocity) {
		this.xvelocity = xvelocity;
	}
	public double getyVelocity() {
		return yvelocity;
	}
	public void setyVelocity(double yvelocity) {
		this.yvelocity = yvelocity;
	}
	
	
}
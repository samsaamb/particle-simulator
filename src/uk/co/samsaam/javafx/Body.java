package uk.co.samsaam.javafx;

import javafx.scene.paint.Color;

public class Body {
	private double x;
	private double y;
	private int width;
	private int height;
	private double xvelocity;
	private double yvelocity;
	private double forceX;
	private double forceY;
	private double mass;
	private Color colour;
	
	public Body(double x, double y, int width, int height, double xvelocity, double yvelocity, double mass, Color colour) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xvelocity = xvelocity;
		this.yvelocity = yvelocity;
		this.mass = mass;
		this.colour = colour;
	}
	
	public double distanceFrom(Body otherBody) { //calculates the distance between this body and the body passed in
		double dx = x - otherBody.x;
		double dy = y - otherBody.y;
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public void resetForce() {
		forceX = 0.0;
		forceY = 0.0;
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

	public Color getColour() {
		return colour;
	}

	public void setColour(Color colour) {
		this.colour = colour;
	}
	
	
}
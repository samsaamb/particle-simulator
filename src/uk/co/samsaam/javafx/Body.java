package uk.co.samsaam.javafx;

import javafx.scene.canvas.GraphicsContext;
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
		double dx = otherBody.x - this.x; //calculating the difference in x between two bodies
		double dy = otherBody.y - this.y; //calculating the difference in y between two bodies
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public void calculateForce(Body otherBody, double G) {
		double dx = otherBody.x - this.x;
		double dy = otherBody.y - this.y;
		double distance = distanceFrom(otherBody);
		double force = (G * (otherBody.getMass() * this.mass) / distance*distance); //calculating the force between two bodies using distance between them, and their masses.
		this.forceX += force * (dx / distance); //vector sum of the forces on the body
		this.forceY += force * (dy / distance); //reintroduces the sign that was removed by the squaring of the distance
	}
	
	public void updateParameters(double dt) {
		double normalisation = dt * 60;
		forceX = forceX * normalisation; //normalisaation feature to smoothen speeds over varying fps.
		forceY = forceY * normalisation;
		xvelocity += (forceX/mass); //appending velocity with acceleration
		yvelocity += (forceY/mass);
		x += xvelocity; //appending positions with velocity
		y += yvelocity;
		int r = (int)  (255 -(( (1/ (Math.abs(xvelocity) +1) * 255)))); //Mapping the velocity of an object to its colour
		int g = (int)  (255 -(( (1/ (Math.abs(yvelocity) +1) * 255)))); 
		int b = (int) 50;
		colour = Color.rgb(r, g, b);
	}
	
	public void screenRestrict(Body body, GraphicsContext graphics) {
		if (body.x <= 0.0 || body.x >= graphics.getCanvas().getWidth()) {
			body.xvelocity *= -1;
		}
		if (body.y <= 0.0 || body.y >= graphics.getCanvas().getHeight()) {
			body.yvelocity *= -1;
		} 
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
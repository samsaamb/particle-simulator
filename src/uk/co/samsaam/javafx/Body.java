package uk.co.samsaam.javafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uk.co.samsaam.javafx.Body;
import uk.co.samsaam.javafx.Main;
import uk.co.samsaam.javafx.Quadrant;

public class Body {
	private double x;
	private double y;
	private int width;
	private int height;
	private double xVelocity;
	private double yVelocity;
	private double forceX;
	private double forceY;
	private double mass;
	private Color colour;
	
	public Body(double x, double y, int width, int height, double xvelocity, double yvelocity, double mass, Color colour) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xVelocity = xvelocity;
		this.yVelocity = yvelocity;
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
		xVelocity += (forceX/mass); //appending velocity with acceleration
		yVelocity += (forceY/mass);
		x += xVelocity; //appending positions with velocity
		y += yVelocity;
		int r = (int)  (255 - (255 / (Math.abs(xVelocity) + 1))); //Mapping the velocity of an object to its colour
		int g = (int)  (255 - (255 / (Math.abs(xVelocity) + 1))); 
		int b = (int) 50;
		colour = Color.rgb(r, g, b);
	}
	
	public void screenRestrict(Body body) { 
		if (body.x <= 0.0 || body.x >= Main.screenWidth) {
			body.xVelocity *= -1; //negates the xvelocity if the body is outside of the screen width
		}
		if (body.y <= 0.0 || body.y >= Main.screenHeight) { 
			body.yVelocity *= -1; //negates the yvelocity if the body is outside of the screen height
		} 
	}
	
	public void resetForce() {
		forceX = 0.0; //resets the force on the body in both directions
		forceY = 0.0;
	}
	
	public boolean in(Quadrant quadrant) {
		return (quadrant.contains(this.x, this.y)); //returns true if a quadrant contains this body.
	}
	
	public Body addBody(Body otherBody) { //calculates and instantiates the aggregate body of this body and the body passed in
		double combinedMass = this.mass + otherBody.mass; //combine the mass
		double newX = (this.x*this.mass + otherBody.x*otherBody.mass)/combinedMass; //calculate the centre-of-mass x-coordinate
		double newY = (this.y*this.mass + otherBody.y*otherBody.mass)/combinedMass;//calculate the centre-of-mass y-coordinate
		return new Body(newX, newY, 0, 0, 0, 0, combinedMass, null); //return the new aggregate body
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
		return xVelocity;
	}
	public void setxVelocity(double xvelocity) {
		this.xVelocity = xvelocity;
	}
	public double getyVelocity() {
		return yVelocity;
	}
	public void setyVelocity(double yvelocity) {
		this.yVelocity = yvelocity;
	}
	public Color getColour() {
		return colour;
	}
	public void setColour(Color colour) {
		this.colour = colour;
	}
}
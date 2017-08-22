package uk.co.samsaam.javafx;

//TODO Resizable screen
//TODO Collision
//TODO Implement equation for gravity, F=GmM/r^2, and use this to create attraction between bodies. Give objects different colours for different F's to show their attraction strength
//TODO Version control and documentation

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Loop extends AnimationTimer {
	
	public ArrayList<Body> bodies = new ArrayList<Body>();
	private GraphicsContext graphics;
	private static final int NUM_GENERATED = 2;
	
	public Loop(GraphicsContext graphics) {
		this.graphics = graphics;
		graphics.setFill(Color.WHITE);
		generateBodies();
	}

	@Override
	public void handle(long now) {
		draw();
		update();
	}
	
	//Clears screen, loops through array list of body particles and draws them
	private void draw() {
		graphics.setFill(Color.BLACK);
		graphics.fillRect(0, 0, 512, 512);
		for (Body body : bodies) {
			graphics.setFill(Color.WHITE);
			graphics.fillOval(body.getX(), body.getY(), body.getWidth(), body.getHeight());
		}
	}
	
	//loops through array list of bodies and increments velocity (x only so far)
	private void update() {
		for (Body body : bodies) {
			double currentX = body.getX();
			body.setX(currentX + body.getVelocity());
		}
	}
	
	//function to generate a random number between min and max parameters
	public double rand(double min, double max) {
		double random = ThreadLocalRandom.current().nextDouble(min, max);
		return random;
	}
	
	//generate "NUM_GENERATED" amount of bodies, with random x and y positions, sizes and velocities. adds to the bodies arraylist of body objects
	public void generateBodies() {
		for (int i = 0; i < NUM_GENERATED; i++) {
			double x = ThreadLocalRandom.current().nextDouble(0, graphics.getCanvas().getWidth());
			double y = ThreadLocalRandom.current().nextDouble(0, graphics.getCanvas().getHeight());
			int width =  ThreadLocalRandom.current().nextInt(20);
			int height = width;
			double velocity = ThreadLocalRandom.current().nextDouble(-5, 5);
			bodies.add(new Body(x, y, width, height, velocity));
			
		}
	}
}


package uk.co.samsaam.javafx;

//TODO Resizable screen
//TODO Pan
//TODO Distance function
//TODO Collision
//TODO Edge box collision
//TODO Implement equation for
//TODO gravity, F=GmM/r^2, and use this to create attraction between bodies. Give objects different colours for different F's to show their attraction strength

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Loop extends AnimationTimer {
	
	public static long before = 0L;
	
	public static final boolean edgeRestriction = false;
	public ArrayList<Body> bodies = new ArrayList<Body>();
	private GraphicsContext graphics;
	private static final int NUM_GENERATED = 100;
	public static final double G = 6.674 * Math.pow(10, -11);

	public Loop(GraphicsContext graphics) {
		this.graphics = graphics;
		graphics.setFill(Color.WHITE);
		generateBodies();
	}

	@Override
	public void handle(long now) {
		
		long dt = now - before;
		before = now;
		
		System.out.println(dt / 1000000000.0);
		
		draw();
		update();
	}

	// Clears screen, loops through array list of body particles and draws them
	private void draw() {
		graphics.setFill(Color.BLACK);
		graphics.fillRect(0, 0, 1680, 1050);
		for (Body body : bodies) {
			graphics.setFill(Color.WHITE);
			graphics.fillOval(body.getX(), body.getY(), body.getWidth(), body.getHeight());
		}
	}

	// loops through array list of bodies and increments velocity (x only so far)
	private void update() {
		for (Body body : bodies) {
			double currentX = body.getX();
			double currentY = body.getY();
			double currentXVelocity = body.getxVelocity();
			double currentYVelocity = body.getyVelocity();
			
			body.setForceX(0);
			body.setForceY(0);
			if (edgeRestriction == true) {
				body.setxVelocity(edgeXDetection(currentX, currentXVelocity));
				body.setyVelocity(edgeYDetection(currentY, currentYVelocity));
			}
			
			for (Body otherBodies : bodies) {
				if (body.getX() - otherBodies.getX() != 0 && body.getY() - otherBodies.getY() != 0) {
					double dx = otherBodies.getX() - body.getX();
					double dy = otherBodies.getY() - body.getY();
					double distance = Math.sqrt((Math.pow((dx), 2)) + (Math.pow((dy), 2)));
					double force = (G * (otherBodies.getMass() * (body.getMass())) / distance*distance);
					
					body.setForceX(body.getForceX() + (force * (dx / distance)));
					body.setForceY(body.getForceY() + (force * (dy / distance)));
				}
			}
			body.setxVelocity(body.getxVelocity() + ((body.getForceX() / body.getMass())));
			body.setyVelocity(body.getyVelocity() + ((body.getForceY() / body.getMass())));
			body.setX(currentX + (body.getxVelocity()));
			body.setY(currentY + (body.getyVelocity()));
			
		}
	}

	// function to generate a random number between min and max parameters
	public double rand(double min, double max) {
		double random = ThreadLocalRandom.current().nextDouble(min, max);
		return random;
	}

	// generate "NUM_GENERATED" amount of bodies, with random x and y positions,
	// sizes and x velocities. adds to the bodies arraylist of body objects
	public void generateBodies() {
		for (int i = 0; i < NUM_GENERATED; i++) {
			double x = rand(0, graphics.getCanvas().getWidth());
			double y = rand(0, graphics.getCanvas().getHeight());
			double mass = rand(500000, 5000000);
			int width = (int) mass / 500000;
			int height = width;
			double xvelocity = rand(0,1);
			double yvelocity = rand(0,1);
			bodies.add(new Body(x, y, width, height, xvelocity, yvelocity, mass));
		}
	}

	public double edgeYDetection(double currentY, double yvelocity) {
		if (currentY <= 0.0 || currentY >= graphics.getCanvas().getHeight()) {
			return -yvelocity;
		} else {
			return yvelocity;
		}
	}

	public double edgeXDetection(double currentX, double xvelocity) {
		if (currentX <= 0.0 || currentX >= graphics.getCanvas().getWidth()) {
			return -xvelocity;
		} else {
			return xvelocity;
		}
	}

}

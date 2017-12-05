package uk.co.samsaam.javafx;

//TODO Resizable screen
//TODO Pan
//TODO Distance function
//TODO Collision
//TODO Edge box collision


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Loop extends AnimationTimer {
	
	private static long before = System.nanoTime(); //Used in calculating fps.
	private static final boolean edgeRestriction = false; //Setting, if true bodies won't leave the screen.
	private ArrayList<Body> bodies = new ArrayList<Body>(); //declaring the arraylist of bodies
	private GraphicsContext graphics; //declaring the graphics object
	private static final int NUM_GENERATED = 1000; //number of bodies to generate
	private static final double G = 6.674 * Math.pow(10, -11); //Gravitational Constant, G
	private static final double SECONDS_IN_NANOSECONDS = 1E9; //Number of nanoseconds in a second, used to calculate dt in seconds and fps.
	

	public Loop(GraphicsContext graphics) {
		this.graphics = graphics; //Setting the graphics object so it is ready for use
		graphics.setFill(Color.WHITE); //Setting the default fill colour to white
		generateBodies(); //Generates/Initialises the bodies
	}

	@Override
	public void handle(long now) {
		long dt = now - before;
		before = now; //Each loop, the before variable will hold the nanotime of the frame before the current frame, allowing dt to be calculated
		
		int fps = (int) Math.round(1/((dt/SECONDS_IN_NANOSECONDS))); //calculates the fps of the program at the current frame.
		draw(fps);
		update(dt/SECONDS_IN_NANOSECONDS);
	}

	// Clears screen, loops through array list of body particles and draws them
	private void draw(int fps) {
		graphics.setFill(Color.BLACK);
		graphics.fillRect(0, 0, 1680, 1050); //Colouring the background black
		for (Body body : bodies) {
			graphics.setFill(body.getColour()); //Paint all of the bodies in the body arraylist
			graphics.fillOval(body.getX(), body.getY(), body.getWidth(), body.getHeight());
		}
		graphics.setFill(Color.WHITE); 
		String FPS = "FPS: " + Integer.toString(fps);
		graphics.fillText(FPS, 0, 12); //Painting the fps to the top left corner
	}

	// loops through array list of bodies and increments velocity
	private void update(double dt) {
		
		double normalisation = dt * 60; //A method of keeping the animation speed constant whilst fps varies.
		
		for (Body body : bodies) { //for each body in the arraylist of bodies
			double currentX = body.getX(); //making the code easier to read later on
			double currentY = body.getY();
			double currentXVelocity = body.getxVelocity();
			double currentYVelocity = body.getyVelocity();
			
			body.resetForce(); //resetting the force on the body before calculating it again
			
			if (edgeRestriction == true) {
				body.setxVelocity(edgeXDetection(currentX, currentXVelocity)); //making sure no bodies can leave the screen boundary if edgeRestriction is true
				body.setyVelocity(edgeYDetection(currentY, currentYVelocity));
			}
			
			for (Body otherBodies : bodies) {
				if (body.getX() - otherBodies.getX() != 0 && body.getY() - otherBodies.getY() != 0) { //checking that a body is not itself
					body.calculateForce(otherBodies, G); //calculating the force between the two bodies
				}
			}
			body.setForceX(body.getForceX() * normalisation); //Setting the x and y forces, utilising the normalisation to ensure that a bod
			body.setForceY(body.getForceY() * normalisation);
			int r = (int)  (255 -(((1	/(Math.abs(body.getxVelocity())+1) * 255)))); //Mapping the velocity of an object to its colour
			int g = (int)  (255 -(((1	/(Math.abs(body.getyVelocity())+1) * 255)))); 
			int b = (int) 50;
			body.setColour(Color.rgb(r, g, b));
			body.setxVelocity((body.getxVelocity() + ((body.getForceX() / body.getMass())))); //Appending to the velocity with the acceleration in the x and y
			body.setyVelocity((body.getyVelocity() + ((body.getForceY() / body.getMass()))));
			body.setX(currentX + (body.getxVelocity())); //Appending the x and y positions with the velocities.
			body.setY(currentY + (body.getyVelocity()));
		}
	}

	// function to generate a random number between min and max parameters
	public double rand(double min, double max) {
		double random = ThreadLocalRandom.current().nextDouble(min, max); //Generates a random double between two specified values
		return random;
	}

	/* generate "NUM_GENERATED" amount of bodies, with random x and y positions,
	sizes, x velocities, y velocities and mass. Adds to the bodies arraylist of body objects */
	public void generateBodies() {
		for (int i = 0; i < NUM_GENERATED; i++) {
			double x = rand(0, graphics.getCanvas().getWidth());
			double y = rand(0, graphics.getCanvas().getHeight());
			double mass = rand(500000, 5000000);
			int width = (int) mass / 500000;  //This makes it so that a body's mass is relational to it's size.
			int height = width;
			double xvelocity = 0;
			double yvelocity = 0;
			Color colour = Color.WHITE;
			bodies.add(new Body(x, y, width, height, xvelocity, yvelocity, mass, colour));
		}
	}

	public double edgeYDetection(double currentY, double yvelocity) { //takes the bodies velocity and position, if the position is outside of the screen then velocity is multiplied by -1.
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

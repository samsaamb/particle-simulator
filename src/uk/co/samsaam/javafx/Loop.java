package uk.co.samsaam.javafx;

//TODO Resizable screen
//TODO Pan
//TODO Collision
//TODO Use timestep in calculations.

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Loop extends AnimationTimer {
	
	private static long before = System.nanoTime(); //Used in calculating fps.
	private static final boolean screenRestrict = false; //Setting, if true bodies won't leave the screen.
	private ArrayList<Body> bodies = new ArrayList<Body>(); //declaring the arraylist of bodies
	private GraphicsContext graphics; //declaring the graphics object
	private static final int NUM_GENERATED = 1000; //number of bodies to generate
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
		graphics.fillText(String.valueOf(bodies.size()) + " bodies", 45, 12); //Drawing the number of bodies to the top left corner
	}

	// loops through array list of bodies and increments velocity
	private void update(double dt) {	
		for (Body body : bodies) { //for each body in the arraylist of bodies
			body.resetForce(); //resetting the force on the body before calculating it again
			
			if (screenRestrict) 
				body.screenRestrict(body); //if the screen restriction is enabled, restrict the bodies to the screen
			
			for (Body otherBody : bodies) {
				if (body != otherBody) { //checking that a body is not itself
					body.calculateForce(otherBody); //calculating the force between the two bodies
				}
			}
			
			body.updateParameters(dt); //update the positions of the bodies
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
			double xvelocity = 0; //bodies are initialised with 0 velocity
			double yvelocity = 0;
			Color colour = Color.WHITE;
			bodies.add(new Body(x, y, width, height, xvelocity, yvelocity, mass, colour)); //add the new body to the list of bodies
		}
	}

}

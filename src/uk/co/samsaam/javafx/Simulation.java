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

public class Simulation extends AnimationTimer {
	
	public static double theta;
	private static long before = System.nanoTime(); //Used in calculating fps.

	private static final double SECONDS_IN_NANOSECONDS = 1E9; //Number of nanoseconds in a second, used to calculate dt in seconds and fps.
	private static final Color DEFAULT_BODY_COLOUR = Color.WHITE;

	private ArrayList<Body> bodies = new ArrayList<Body>(); //declaring the arraylist of bodies
	private GraphicsContext graphics; //declaring the graphics object

	private int numBodies;
	private boolean screenRestricted;
	private boolean colourMapped;
	

	public Simulation(GraphicsContext graphics, int numBodies, double theta, boolean screenRestricted, boolean colourMapped) {
		this.graphics = graphics; //Setting the graphics object so it is ready for use
		this.numBodies = numBodies;
		this.screenRestricted = screenRestricted;
		this.colourMapped = colourMapped;
		
		Simulation.theta = theta;
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
		graphics.fillRect(0, 0, NBodyApplication.SCREEN_WIDTH, NBodyApplication.SCREEN_HEIGHT); //Colouring the background black

		for (Body body : bodies) {
			graphics.setFill(colourMapped ? body.getColour() : DEFAULT_BODY_COLOUR);
			graphics.fillOval(body.getX(), body.getY(), body.getWidth(), body.getHeight());
		}
		
		graphics.setFill(DEFAULT_BODY_COLOUR); 
		String FPS = "FPS: " + Integer.toString(fps);
		graphics.fillText(FPS, 0, 12); //Painting the fps to the top left corner
		graphics.fillText(String.valueOf(bodies.size()) + " bodies", 45, 12); //Drawing the number of bodies to the top left corner
	}

	// loops through array list of bodies and increments velocity
	private void update(double dt) {	
		double midX = NBodyApplication.SCREEN_WIDTH / 2;
		double midY = NBodyApplication.SCREEN_WIDTH / 2;
		
		Quadrant quad = new Quadrant(midX, midY, NBodyApplication.SCREEN_WIDTH);
		Quadtree tree = new Quadtree(quad); //declare the quadtree
		
		for (Body body : bodies) { //add each body to the quadtree
			if (body.in(quad)) {
				tree.insert(body);
			}
		}
		
		
		bodies.parallelStream().forEach(body -> {
			body.resetForce(); //resetting the force on the body before calculating it again
			tree.updateForce(body);
			body.updateParameters(dt);
			
			if (screenRestricted)
				body.screenRestrict(body);
		});
	}

	// function to generate a random number between min and max parameters
	public double rand(double min, double max) {
		double random = ThreadLocalRandom.current().nextDouble(min, max); //Generates a random double between two specified values
		return random;
	}

	/* generate "NUM_GENERATED" amount of bodies, with random x and y positions,
	sizes, x velocities, y velocities and mass. Adds to the bodies arraylist of body objects */
	public void generateBodies() {
		for (int i = 0; i < numBodies; i++) {
			double x = rand(0, graphics.getCanvas().getWidth());
			double y = rand(0, graphics.getCanvas().getHeight());
			double mass = 5E4;
			double width = 0.6;  //This makes it so that a body's mass is relational to it's size.
			double height = width;
			double xvelocity = 0; //bodies are initialised with 0 velocity
			double yvelocity = 0;
			Color colour = Color.WHITE;
			bodies.add(new Body(x, y, width, height, xvelocity, yvelocity, mass, colour)); //add the new body to the list of bodies
		}
	}

}

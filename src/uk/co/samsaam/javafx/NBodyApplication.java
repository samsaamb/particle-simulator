package uk.co.samsaam.javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.stage.Stage;

public class NBodyApplication extends Application {
	
		public static final double SCREEN_WIDTH = 1680;
		public static final double SCREEN_HEIGHT = 1050; 
		
	    @Override 
	    public void start(Stage theStage) 
	    {
	        theStage.setTitle( "N-Body Simulator" );
	     
	        Group root = new Group();
	        Scene theScene = new Scene(root);
	        theStage.setScene(theScene);
	     
	        
	        Canvas canvas = new Canvas( SCREEN_WIDTH, SCREEN_HEIGHT );
	        root.getChildren().add(canvas);
	     
	        GraphicsContext graphics = canvas.getGraphicsContext2D();
	    
	        Simulation simulation = new Simulation(graphics);
	        simulation.start();

	        theStage.show();
	    }

	    public static void main(String[] args) {
	        Application.launch(NBodyApplication.class, args);
	    }

}

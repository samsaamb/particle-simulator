package uk.co.samsaam.javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.stage.Stage;

public class Main extends Application {
		public static double screenWidth = 1680;
		public static double screenHeight = 1050; 
	    @Override 
	    public void start(Stage theStage) 
	    {
	        theStage.setTitle( "N-Body Simulator" );
	     
	        Group root = new Group();
	        Scene theScene = new Scene( root );
	        theStage.setScene( theScene );
	     
	        
	        Canvas canvas = new Canvas( screenWidth, screenHeight );
	        root.getChildren().add( canvas );
	     
	        GraphicsContext graphics = canvas.getGraphicsContext2D();
	    
	        Loop loop = new Loop(graphics);
	        loop.start();

	        theStage.show();
	    }

	    public static void main(String[] args) {
	        Application.launch(args);
	    }

}

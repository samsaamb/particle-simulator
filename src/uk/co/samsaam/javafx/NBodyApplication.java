package uk.co.samsaam.javafx;


import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NBodyApplication extends Application {

		public static final double SCREEN_WIDTH = 1600;
		public static final double SCREEN_HEIGHT = 1000;
		
		private static final String DEFAULT_NUMBER_OF_BODIES = "10000";
		private static final String DEFAULT_THETA_VALUE = "0.5";
		
	    @Override 
	    public void start(Stage simulationStage) 
	    {
	        simulationStage.setTitle( "N-Body Simulator" );
	     
	        Group root = new Group();
	        Scene theScene = new Scene( root );
	        simulationStage.setScene(theScene );
	 
	        Canvas simulationCanvas = new Canvas( SCREEN_WIDTH, SCREEN_HEIGHT );
	        root.getChildren().add(simulationCanvas);
	    
	        Stage settingsStage = makeSettingsStage(simulationStage, simulationCanvas);
	        settingsStage.show();
	    }

		private Stage makeSettingsStage(Stage simulationStage, Canvas simulationCanvas) {
			Stage settingsStage = new Stage();
	        settingsStage.setTitle("NBody Simulator Settings");
	        
	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));
	        
	        Scene scene = new Scene(grid, 575, 265);
	        settingsStage.setScene(scene);
	        
	        Text scenetitle = new Text("NBody Simulator Settings");
	        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        grid.add(scenetitle, 0, 0, 2, 1);

	        Label numBodiesLabel = new Label("Number of bodies (integer): ");
	        grid.add(numBodiesLabel, 0, 1);

	        TextField numBodiesField = new TextField();
	        numBodiesField.setText(DEFAULT_NUMBER_OF_BODIES);
	        grid.add(numBodiesField, 1, 1);

	        Label thetaLabel = new Label("Theta (decimal): ");
	        grid.add(thetaLabel, 0, 2);

	        TextField thetaField = new TextField();
	        thetaField.setText(DEFAULT_THETA_VALUE);
	        grid.add(thetaField, 1, 2);
	        
	        CheckBox screenRestrictionCheckBox = new CheckBox("Screen restriction");
	        screenRestrictionCheckBox.setSelected(false);
	        grid.add(screenRestrictionCheckBox, 0, 3);
	        
	        CheckBox colouredMappedBodiesCheckBox = new CheckBox("Colour mapped bodies");
	        colouredMappedBodiesCheckBox.setSelected(false);
	        grid.add(colouredMappedBodiesCheckBox, 0, 4);
	        
	        Button startSimulationButton = new Button("Start simulation");
	        grid.add(startSimulationButton, 0, 6);
	        
	        Label status = new Label("Enter settings to proceed");
	        status.setMinWidth(360);
	        grid.add(status, 0, 8);
	        
	        startSimulationButton.setOnAction((event) -> {
	        	String numBodiesText = numBodiesField.getText();
	        	String thetaText = thetaField.getText();
				
				boolean numBodiesValid = isValidNumBodies(numBodiesText);
				boolean thetaValid = isValidTheta(thetaText);
				
				if (!numBodiesValid || !thetaValid) {
					setInvalidSettingsStatus(status, numBodiesValid, thetaValid);
					return;
				}
				
				int numBodies = Integer.parseInt(numBodiesText);
				double theta = Double.parseDouble(thetaText);
				
				boolean screenRestricted = screenRestrictionCheckBox.isSelected();
				boolean colourMapped = colouredMappedBodiesCheckBox.isSelected();
				
				settingsStage.close();
	        	startSimulation(simulationStage, simulationCanvas, numBodies, theta, screenRestricted, colourMapped);
	        });
	        
			return settingsStage;
		}


		private void startSimulation(Stage simulationStage, Canvas simulationCanvas, int numBodies, double theta, boolean screenRestricted, boolean colourMapped) {

			GraphicsContext simulationGraphics = simulationCanvas.getGraphicsContext2D();
			Simulation simulation = new Simulation(simulationGraphics, numBodies, theta, screenRestricted, colourMapped);
			
			simulationStage.show();
			simulation.start();
		}
		
		private boolean isValidNumBodies(String numBodies) {
			
			try {
				
				int num = Integer.parseInt(numBodies);
				
				if (num < 0) {
					return false;
				}
				
			} catch (NumberFormatException e) {
				return false;
			}
			
			return true;
		}
		
		private boolean isValidTheta(String thetaText) {
			
			try {
				
				double num = Double.parseDouble(thetaText);
				
				if (num < 0) {
					return false;
				}
				
			} catch (NumberFormatException e) {
				return false;
			}
			
			return true;
		}
		
		private void setInvalidSettingsStatus(Label status, boolean numBodiesValid, boolean thetaValid) {
			List<String> invalidFields = new LinkedList<String>();
			
			if (!numBodiesValid) {
				invalidFields.add("number of bodies");
			}
			
			if (!thetaValid) {
				invalidFields.add("theta quotient");
			}
			
			String commaSeperatedFields = String.join(", ", invalidFields);
			status.setTextFill(Color.RED);
			status.setText("The following settings are invalid: " + commaSeperatedFields);
		}

	    public static void main(String[] args) {
	        Application.launch(NBodyApplication.class, args);
	    }

}
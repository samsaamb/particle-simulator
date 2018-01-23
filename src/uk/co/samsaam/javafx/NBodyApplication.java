package uk.co.samsaam.javafx;


import java.util.ArrayList;
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
import javafx.stage.Stage; //necessary imports

public class NBodyApplication extends Application {

		public static final double SCREEN_WIDTH = 1600; //declaring canvas width
		public static final double SCREEN_HEIGHT = 1000; //declaring canvas height
		
		private static final String DEFAULT_NUMBER_OF_BODIES = "10000"; //default number of bodies
		private static final String DEFAULT_THETA_VALUE = "0.5"; //default value for theta
		
	    @Override 
	    public void start(Stage simulationStage) 
	    {
	        simulationStage.setTitle( "N-Body Simulator" ); //set title of simulation
	     
	        Group root = new Group(); //making root
	        Scene theScene = new Scene( root ); //adding group to scene
	        simulationStage.setScene(theScene ); //setting the scene on the stage
	 
	        Canvas simulationCanvas = new Canvas( SCREEN_WIDTH, SCREEN_HEIGHT ); //creating the canvas
	        root.getChildren().add(simulationCanvas); //adding it to the group
	    
	        Stage settingsStage = makeSettingsStage(simulationStage, simulationCanvas); //creating the settingsStage
	        settingsStage.show(); //showing the settingsStage
	    }

		private Stage makeSettingsStage(Stage simulationStage, Canvas simulationCanvas) { //function makes the ui and event handler for the settings menu
			Stage settingsStage = new Stage(); //make the settingsstage
	        settingsStage.setTitle("NBody Simulator Settings"); //title it this
	        
	        GridPane grid = new GridPane(); //create gridpane
	        grid.setAlignment(Pos.BASELINE_LEFT); //set alignment to mid-left
	        grid.setHgap(10); //set gap between objects to 10 in vertical and horizontal
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25)); //padding for frame around objects
	        
	        Scene scene = new Scene(grid, 575, 265); //add the grid to the scene
	        settingsStage.setScene(scene); //set the scene on the stage
	        
	        Text scenetitle = new Text("NBody Simulator Settings"); //add text to the screen
	        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20)); //properties of the text
	        grid.add(scenetitle, 0, 0, 2, 1); //add this text to the grid

	        Label numBodiesLabel = new Label("Number of bodies (integer): "); //create label
	        grid.add(numBodiesLabel, 0, 1); //add to the grid

	        TextField numBodiesField = new TextField(); //create text field
	        numBodiesField.setText(DEFAULT_NUMBER_OF_BODIES); //set it's default to default declared above
	        grid.add(numBodiesField, 1, 1); //add to the grid

	        Label thetaLabel = new Label("Theta (decimal): "); //create label
	        grid.add(thetaLabel, 0, 2); //add to the grid

	        TextField thetaField = new TextField(); //create textfield for theta
	        thetaField.setText(DEFAULT_THETA_VALUE); //set it's default to default declared above
	        grid.add(thetaField, 1, 2); //add it to the grid
	        
	        CheckBox screenRestrictionCheckBox = new CheckBox("Screen restriction"); //create checkbox with this text
	        screenRestrictionCheckBox.setSelected(false); //set default to false
	        grid.add(screenRestrictionCheckBox, 0, 3); //add it to the grid
	        
	        CheckBox colouredMappedBodiesCheckBox = new CheckBox("Colour mapped bodies"); //create checkbox with this text
	        colouredMappedBodiesCheckBox.setSelected(false); //set default to false
	        grid.add(colouredMappedBodiesCheckBox, 0, 4); //add it to the grid
	        
	        Button startSimulationButton = new Button("Start simulation"); //create button with this text
	        grid.add(startSimulationButton, 0, 6); //add this button to the grid
	        
	        Label status = new Label("Enter settings to proceed"); //create this label with this text
	        status.setMinWidth(360); //set it's minimum width so it does not get cut off
	        grid.add(status, 0, 8); //add to the grid
	        
	        startSimulationButton.setOnAction((event) -> { //when button is clicked
	        	String numBodiesText = numBodiesField.getText(); //collect value of numBodies
	        	String thetaText = thetaField.getText(); //collect value of theta
				
				boolean numBodiesValid = isValidNumBodies(numBodiesText); //check if numBodies input was valid
				boolean thetaValid = isValidTheta(thetaText); //check if theta input was valid
				
				if (!numBodiesValid || !thetaValid) {
					setInvalidSettingsStatus(status, numBodiesValid, thetaValid); //if they were not valid, update status
					return; //reset state to button unclicked
				}
				
				int numBodies = Integer.parseInt(numBodiesText); //value must have been valid to get here, parse the string for int value of numBodies
				double theta = Double.parseDouble(thetaText); //parse the string for double value of theta
				
				boolean screenRestricted = screenRestrictionCheckBox.isSelected(); //collect screenRestrict true/false
				boolean colourMapped = colouredMappedBodiesCheckBox.isSelected();//collect colourMapped true/false
				
				settingsStage.close(); //close the settingsStage
	        	startSimulation(simulationStage, simulationCanvas, numBodies, theta, screenRestricted, colourMapped); //start the simulation with the input values
	        });
	        
			return settingsStage; //return this settingsStage so it can be used
		}


		private void startSimulation(Stage simulationStage, Canvas simulationCanvas, int numBodies, double theta, boolean screenRestricted, boolean colourMapped) {
			//starts simulation
			GraphicsContext simulationGraphics = simulationCanvas.getGraphicsContext2D(); //collect the simulationCanvas' graphics object for use
			Simulation simulation = new Simulation(simulationGraphics, numBodies, theta, screenRestricted, colourMapped); //create a new Simulation with the inputs
			
			simulationStage.show(); //show the simulation
			simulation.start(); //start the simulation
		}
		
		private boolean isValidNumBodies(String numBodies) { //returns true if numBodies is valid, false otherwise
			
			try { //try to parse the string for an integer
				
				int num = Integer.parseInt(numBodies);
				
				if (num < 0) { //numBodies cannot be < 0
					return false;
				}
				
			} catch (NumberFormatException e) { //catch error if couldnt parse, return false
				return false;
			}
			
			return true; //return true if could parse
		}
		
		private boolean isValidTheta(String thetaText) { //same as above function but with double parse instead
			
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
			List<String> invalidFields = new ArrayList<String>(); //arraylist of invalid fields
			
			if (!numBodiesValid) { //if numBodies invalid
				invalidFields.add("number of bodies"); //add this string to the list
			}
			
			if (!thetaValid) { //if theta invalid
				invalidFields.add("theta quotient"); //add this string to the list
			}
			
			String commaSeperatedFields = String.join(", ", invalidFields); //build a string which is a list of the invalidFields
			status.setTextFill(Color.RED); //set text colour to red
			status.setText("The following settings are invalid: " + commaSeperatedFields); //show the user the invalid settings
		}

	    public static void main(String[] args) {
	        Application.launch(NBodyApplication.class, args); //launch the application with this class
	    }

}
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RaceTrack extends Application {
	
	//All JavaFX widgets declarations
	Button startButton;
	Button pauseButton;
	Button resetButton;
	ImageView firstCar;
	ImageView secondCar;
	ImageView thirdCar;
	
	//Global conditional variables to moniter threads 
	private boolean suspended;
	private boolean raceOver = false;
	private boolean reset = false;
	private boolean raceStart = false;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	public void start(Stage primaryStage) throws Exception {
		
		//Sets up GUI Interface
		primaryStage.setTitle("Richmond Raceway");
		startButton = new Button();
		startButton.setText("Start");
		startButton.setLayoutX(100);
		startButton.setLayoutY(25);
		
		pauseButton = new Button();
		pauseButton.setText("Pause");
		pauseButton.setLayoutX(225);
		pauseButton.setLayoutY(25);
		
		resetButton = new Button();
		resetButton.setText("Reset");
		resetButton.setLayoutX(350);
		resetButton.setLayoutY(25);
		
		//Handles all button events
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Uninitialized threads
				Thread firstThread = null;
				Thread secondThread = null;
				Thread thirdThread = null;
				
				//Starts threads if the race hasn't been started, else resumes race
				if(event.getSource() == startButton) {
					reset = false;
					if(!raceStart) {
						raceOver = false;
						startRace(firstCar, firstThread);
						startRace(secondCar, secondThread);
						startRace(thirdCar, thirdThread);
						raceStart = true;
					}
					suspended = false;
				} else if(event.getSource() == pauseButton) {
					//Pauses race
					suspended = true;
				} else if(event.getSource() == resetButton) {
					//Sets condition for threads to terminate and resets position of cars
					reset = true;
					firstCar.setX(25);
					secondCar.setX(25);
					thirdCar.setX(25);
					firstThread = null;
					secondThread = null;
					thirdThread = null;
					raceStart = false;
				}
				
			}
			
		};
		//Binds buttons to event handler
		startButton.setOnAction(event);
		pauseButton.setOnAction(event);
		resetButton.setOnAction(event);
		
		//Creates racetracks
		Rectangle firstRect = new Rectangle(25, 75, 450, 10);
		Rectangle secRect = new Rectangle(25, 125, 450, 10);
		Rectangle thirdRect = new Rectangle(25, 175, 450, 10);
		firstRect.setFill(Color.TRANSPARENT);
		secRect.setFill(Color.TRANSPARENT);
		thirdRect.setFill(Color.TRANSPARENT);
	    firstRect.setStroke(Color.BLACK);
	    secRect.setStroke(Color.BLACK);
	    thirdRect.setStroke(Color.BLACK);

		//Creates Images
		firstCar = createImage(25, 70);
		secondCar = createImage(25, 120);
		thirdCar = createImage(25, 170);
	    
		//Adds widgets to Pane and sets stage.
		Pane layout = new Pane();
		layout.getChildren().addAll(startButton, pauseButton, resetButton, firstRect, 
				secRect, thirdRect, firstCar, secondCar, thirdCar);
		Scene scene = new Scene(layout, 500, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	private ImageView createImage(int x, int y) {
		//If you are not using Eclipse, please change to appropriate filepath
		File file = new File("./src/sportive-car.png");
		Image car = null;
		try {
			FileInputStream	inputStream = new FileInputStream(file);
			car = new Image(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//Initializes image and places it in appropriate place
		ImageView oneImage = new ImageView(car);
		oneImage.setX(x);
		oneImage.setY(y);
		oneImage.setFitHeight(20);
		oneImage.setFitHeight(20);
	    oneImage.setPreserveRatio(true);
	    return oneImage;
	}
	public void startRace(ImageView carImage, Thread thread) {
		//Creates thread for the specified car
		thread = new Thread(new Runnable() {
				public void run() {
					//Checks to see if the race is either over or reset
					while(carImage.getX() != 475 && !raceOver && !reset) {
						//Pause Condition
						while(suspended) {
							try {
								Thread.sleep(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						//Generates random drive distance and updates GUI to reflect distance driven
						int carMove = (int)(Math.random() * 10);
						Platform.runLater(new Runnable() {
							public void run() {
								carImage.setX(carImage.getX() + carMove);
								//If car reaches finish line, the winner is displayed
								if(carImage.getX() >= 475) {
									Alert alert = new Alert(AlertType.INFORMATION);
									if(carImage.getY() == 70)
										alert.setContentText("Car 1 is the Winner!");
									else if(carImage.getY() == 120)
										alert.setContentText("Car 2 is the Winner!");
									else
										alert.setContentText("Car 3 is the Winner!");
									alert.show();
									raceOver = true;
									raceStart = false;
									return;
								}
							}
						});
						//After each iteration, thread sleeps for 50 ms
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		);
		thread.setDaemon(true);
		thread.start();
	}
}

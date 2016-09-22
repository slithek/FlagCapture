package flagcapture;



import java.awt.Graphics2D;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class AppRunner extends Application {

	public static final int CANVAS_WIDTH = 1200;
	public static final int CANVAS_HEIGHT = 720;
	
	private GameManager gameManager;
	
	private boolean displayArrows;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
			gameManager = new GameManager(1);
			displayArrows = true;
		
	//SETUP FOR STAGE, SCENE, AND CANVAS GRAPHICS
			primaryStage.setTitle("FlagCapture v0.1.0");
			
			Group root = new Group();
			Scene baseScene = new Scene(root);
			
			primaryStage.setScene(baseScene);
			
			Canvas mainCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
			root.getChildren().add(mainCanvas);
			
			GraphicsContext graphics = mainCanvas.getGraphicsContext2D();
			gameManager.setGraphicsContext(graphics);
	
	//ARRAY LIST FOR USER INPUT
			ArrayList<String> input = new ArrayList<String>();
			
	//EVENT LISTENERS
		//KEY LISTENERS
			baseScene.setOnKeyPressed(
					new EventHandler<KeyEvent>() {
						@Override
						public void handle(KeyEvent e) {
							String code = e.getCode().toString();
							
							if (!input.contains(code)) {
								gameManager.startUser(code);
								input.add(code);
							}
						}
					}
			);
			
			baseScene.setOnKeyReleased(
					new EventHandler<KeyEvent>() {
						@Override
						public void handle(KeyEvent e) {
							String code = e.getCode().toString();
							
							if (input.contains(code)) {
								gameManager.stopUser(code);
								input.remove(code);
							}
						}
					}
			);
		
	//================================MAIN GAME LOOP================================//
			new AnimationTimer() {
				@Override
				public void handle(long currentNanoTime) {
					//DRAWS BACKGROUND
						gameManager.drawBackground();
					
					//MOVES, RENDERS, AND GENERATES FRICTION ON USER
						if (input.size() > 0) {
							for (String s : input) {
								gameManager.moveUser(s);
							}
						}
						gameManager.tickUser();
						gameManager.renderUser();
						if (displayArrows) {
							gameManager.displayDirectionalArrows();
						}
						
					
				}
			}.start();
			
		//DISPLAYS THE STAGE
			primaryStage.show();
	}
	

}

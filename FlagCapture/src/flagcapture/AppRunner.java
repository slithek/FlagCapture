package flagcapture;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.input.KeyEvent;

public class AppRunner extends Application {

	public static final int CANVAS_WIDTH = 1200;
	public static final int CANVAS_HEIGHT = 720;
	private ArrayList<String> input;
	
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
			input = new ArrayList<String>();
			
	//EVENT LISTENERS
		//KEY LISTENERS
			
//vvv===This is old code I used before I understood :: syntax===vvv//
//			baseScene.setOnKeyPressed(
//					new EventHandler<KeyEvent>() {
//						@Override
//						public void handle(KeyEvent e) {
//							String code = e.getCode().toString();
//							
//							if (!input.contains(code)) {
//								gameManager.startUser(code);
//								input.add(code);
//							}
//						}
//					}
//			);
//			baseScene.setOnKeyPressed(
//				e -> {	String code = e.getCode().toString();
//							
//						if (!input.contains(code)) {
//							gameManager.startUser(code);
//							input.add(code);
//						}
//					}
//			);
//^^^===This is old code I used before I understood :: syntax===^^^//
			
			baseScene.setOnKeyPressed(this::handleKeyPressed);
			baseScene.setOnKeyReleased(this::handleKeyReleased);
		
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
//================================END GAME LOOP================================//
			
		//DISPLAYS THE STAGE
			primaryStage.show();
	}
	
	private void handleKeyPressed(KeyEvent e) {
		String code = e.getCode().toString();
		
		if (!input.contains(code)) {
			gameManager.startUser(code);
			input.add(code);
		}
	}
	
	private void handleKeyReleased(KeyEvent e) {
		String code = e.getCode().toString();
		
		if (input.contains(code)) {
			gameManager.stopUser(code);
			input.remove(code);
		}
	}
	

}

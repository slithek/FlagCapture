package flagcapture;

import java.awt.Graphics2D;


import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class GameManager {

	private GraphicsContext graphics;
	private UserSprite user1;
	private int[] ticksSinceLastStop;
	private int i;
	
	public GameManager(int numPlayers) {
		if (numPlayers == 1) {
			user1 = new UserSprite(AppRunner.CANVAS_WIDTH/4.0, AppRunner.CANVAS_HEIGHT/2.0, "home");
		}
	}
	
	public void drawBackground() {
		graphics.setFill(Color.DARKSEAGREEN);
		graphics.fillRect(0, 0, AppRunner.CANVAS_WIDTH/2.0, AppRunner.CANVAS_HEIGHT);
		graphics.setFill(Color.DARKOLIVEGREEN);
		graphics.fillRect(AppRunner.CANVAS_WIDTH/2.0, 0, AppRunner.CANVAS_WIDTH/2.0, AppRunner.CANVAS_HEIGHT);
		graphics.setFill(Color.SADDLEBROWN);
		graphics.setLineWidth(3);
		graphics.strokeLine(AppRunner.CANVAS_WIDTH/2.0, 0, AppRunner.CANVAS_WIDTH/2.0, AppRunner.CANVAS_HEIGHT);
	}
	
	public void setGraphicsContext(GraphicsContext graphics) {
		this.graphics = graphics;
	}
	
	public void moveUser(String direction) {
		switch (direction) {
			case "UP" :
				user1.move(UserDirection.UP);
				break;
			case "RIGHT" :
				user1.move(UserDirection.RIGHT);
				break;
			case "DOWN" :
				user1.move(UserDirection.DOWN);
				break;
			case "LEFT" :
				user1.move(UserDirection.LEFT);
				break;
			default:
				throw new RuntimeException("Invalid direction string");
		}
	}
	
	public void renderUser() {
		user1.render(graphics);
	}
	
	public void startUser(String direction) {
		switch (direction) {
		case "UP" :
			user1.startUp();
			break;
		case "RIGHT" :
			user1.startRight();
			break;
		case "DOWN" :
			user1.startDown();
			break;
		case "LEFT" :
			user1.startLeft();
			break;
		default:
			throw new RuntimeException("Invalid direction string");
		}
	}
	
	public void stopUser(String direction) {
		switch (direction) {
		case "UP" :
			user1.stopUp();
			break;
		case "RIGHT" :
			user1.stopRight();
			break;
		case "DOWN" :
			user1.stopDown();
			break;
		case "LEFT" :
			user1.stopLeft();
			break;
		default:
			throw new RuntimeException("Invalid direction string");
		}
	}
	
	public void tickUser() {
		if (user1.isStoppedUp()) {
			user1.upTick();
			user1.move(UserDirection.UP);
		}
		if (user1.isStoppedRight()) {
			user1.rightTick();
			user1.move(UserDirection.RIGHT);
		}
		if (user1.isStoppedDown()) {
			user1.downTick();
			user1.move(UserDirection.DOWN);
		}
		if (user1.isStoppedLeft()) {
			user1.leftTick();
			user1.move(UserDirection.LEFT);
		}
	}
	
	public void displayDirectionalArrows() {
		if (!user1.isStoppedUp()) {
			user1.displayUpArrow(graphics);
		}
		if (!user1.isStoppedRight()) {
			user1.displayRightArrow(graphics);
		}
		if (!user1.isStoppedDown()) {
			user1.displayDownArrow(graphics);
		}
		if (!user1.isStoppedLeft()) {
			user1.displayLeftArrow(graphics);
		}
	}
	
	
}

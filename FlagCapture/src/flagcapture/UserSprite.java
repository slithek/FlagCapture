package flagcapture;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;

public class UserSprite implements Sprite {

	private double posX;
	private double posY;
	private double velX;
	private double velY;
	private Color USER_DEFENSE_COLOR;
	private Color USER_ATTACK_COLOR;
	private static final Color ARROW_HOME_COLOR = Color.DARKRED;
	private static final Color ARROW_AWAY_COLOR = Color.LIGHTPINK;
	
	private static final int ARROW_DISTANCE = 3;
	private static final int ARROW_SIZE = 5;
	
	private double frictionValue;
	
	private int upTicker;
	private int rightTicker;
	private int downTicker;
	private int leftTicker;
	
	private boolean isStoppedUp;
	private boolean isStoppedRight;
	private boolean isStoppedDown;
	private boolean isStoppedLeft;
	
	private double[] upArrowXValues;
	private double[] upArrowYValues;
	private double[] rightArrowXValues;
	private double[] rightArrowYValues;
	private double[] downArrowXValues;
	private double[] downArrowYValues;
	private double[] leftArrowXValues;
	private double[] leftArrowYValues;
	private double[] upLeftArrowXValues;
	private double[] upLeftArrowYValues;
	private double[] upRightArrowXValues;
	private double[] upRightArrowYValues;
	private double[] downRightArrowXValues;
	private double[] downRightArrowYValues;
	private double[] downLeftArrowXValues;
	private double[] downLeftArrowYValues;
	
	private boolean isOnHomeSide;
	
	public UserSprite(double initX, double initY, String side) {
		if (side.equalsIgnoreCase("HOME")) {
			USER_DEFENSE_COLOR = Color.DARKOLIVEGREEN;
			USER_ATTACK_COLOR = Color.DARKSEAGREEN;
		} else if (side.equalsIgnoreCase("AWAY")) {
			USER_DEFENSE_COLOR = Color.LIGHTSKYBLUE;
			USER_ATTACK_COLOR = Color.DARKBLUE;
		} else {
			throw new RuntimeException("Must specify HOME or AWAY");
		}
		
		posX = initX;
		posY = initY;
		velX = 2.5;
		velY = 2.5;
		
		upTicker = 1;
		rightTicker = 1;
		downTicker = 1;
		leftTicker = 1;
		isStoppedUp = false;
		isStoppedRight = false;
		isStoppedDown = false;
		isStoppedLeft = false;
		
		upArrowXValues = new double[3];
		upArrowYValues = new double[3];
		rightArrowXValues = new double[3];
		rightArrowYValues = new double[3];
		downArrowXValues = new double[3];
		downArrowYValues = new double[3];
		leftArrowXValues = new double[3];
		leftArrowYValues = new double[3];
	}
	
	@Override
	public Rectangle2D getBoundary() {
		return new Rectangle2D(posX, posY, posX+SPRITE_WIDTH, posY+SPRITE_HEIGHT);
	}

	@Override
	public boolean intersects(Sprite s) {
		return getBoundary().intersects(s.getBoundary());
	}

	@Override
	public void render(GraphicsContext graphics) {
		if (posX < AppRunner.CANVAS_WIDTH/2.0) {
			isOnHomeSide = true;
			graphics.setFill(USER_DEFENSE_COLOR);
		} else {
			isOnHomeSide = false;
			graphics.setFill(USER_ATTACK_COLOR);
		}
		graphics.fillOval(posX, posY, SPRITE_WIDTH, SPRITE_HEIGHT);
	}
	
	public void move(UserDirection direction) {
		switch (direction) {
			case UP:
				if (posY>=velY) {
					if (Math.pow(upTicker, Sprite.FRICTION) >= velY) {
						if (isStoppedUp) {
							frictionValue = velY;
						} else {
							frictionValue = 1;
						}
					} else {
						frictionValue = Math.pow(upTicker, Sprite.FRICTION);
					}
					posY-=(velY-frictionValue);
				}
				break;
			case RIGHT:
				if (posX+SPRITE_WIDTH<=AppRunner.CANVAS_WIDTH-velX) {
					if (Math.pow(rightTicker, Sprite.FRICTION) >= velX) {
						if (isStoppedRight) {
							frictionValue = velX;
						} else {
							frictionValue = 1;
						}
					} else {
						frictionValue = Math.pow(rightTicker, Sprite.FRICTION);
					}
					posX+=(velX-frictionValue);
				}
				break;
			case DOWN:
				if (posY+SPRITE_HEIGHT<=AppRunner.CANVAS_HEIGHT-velY) {
					if (Math.pow(downTicker, Sprite.FRICTION) >= velY) {
						if (isStoppedDown) {
							frictionValue = velY;
						} else {
							frictionValue = 1;
						}
					} else {
						frictionValue = Math.pow(downTicker, Sprite.FRICTION);
					}
					posY+=(velY-frictionValue);
				}
				break;
			case LEFT:
				if (posX>=velX) {
					if (Math.pow(leftTicker, Sprite.FRICTION) >= velY) {
						if (isStoppedLeft) {
							frictionValue = velX;
						} else {
							frictionValue = 1;
						}
					} else {
						frictionValue = Math.pow(leftTicker, Sprite.FRICTION);
					}
					posX-=(velX-frictionValue);
				}
				break;
		}
	}
	
	public void upTick() {
		upTicker++;
	}
	public void rightTick() {
		rightTicker++;
	}
	public void downTick() {
		downTicker++;
	}
	public void leftTick() {
		leftTicker++;
	}
	
	public boolean isStoppedUp() {
		return isStoppedUp;
	}
	public boolean isStoppedRight() {
		return isStoppedRight;
	}
	public boolean isStoppedDown() {
		return isStoppedDown;
	}
	public boolean isStoppedLeft() {
		return isStoppedLeft;
	}
	
	public void startUp() {
		isStoppedUp = false;
		upTicker = 1;
	}
	public void startRight() {
		isStoppedRight = false;
		rightTicker = 1;
	}
	public void startDown() {
		isStoppedDown = false;
		downTicker = 1;
	}
	public void startLeft() {
		isStoppedLeft = false;
		leftTicker = 1;
	}
	
	public void stopUp() {
		isStoppedUp = true;
	}
	public void stopRight() {
		isStoppedRight = true;
	}
	public void stopDown() {
		isStoppedDown = true;
	}
	public void stopLeft() {
		isStoppedLeft = true;
	}
	
	public boolean isOnHomeSide() {
		return isOnHomeSide;
	}
	
	public void displayUpArrow(GraphicsContext graphics) {
		fillUpArrowArray();
		if (isOnHomeSide) {
			graphics.setFill(ARROW_HOME_COLOR);
		} else {
			graphics.setFill(ARROW_AWAY_COLOR);
		}
		graphics.fillPolygon(upArrowXValues, upArrowYValues, 3);
	}
	public void displayRightArrow(GraphicsContext graphics) {
		fillRightArrowArray();
		if (isOnHomeSide) {
			graphics.setFill(ARROW_HOME_COLOR);
		} else {
			graphics.setFill(ARROW_AWAY_COLOR);
		}
		graphics.fillPolygon(rightArrowXValues, rightArrowYValues, 3);
	}
	public void displayDownArrow(GraphicsContext graphics) {
		fillDownArrowArray();
		if (isOnHomeSide) {
			graphics.setFill(ARROW_HOME_COLOR);
		} else {
			graphics.setFill(ARROW_AWAY_COLOR);
		}
		graphics.fillPolygon(downArrowXValues, downArrowYValues, 3);
	}
	public void displayLeftArrow(GraphicsContext graphics) {
		fillLeftArrowArray();
		if (isOnHomeSide) {
			graphics.setFill(ARROW_HOME_COLOR);
		} else {
			graphics.setFill(ARROW_AWAY_COLOR);
		}
		graphics.fillPolygon(leftArrowXValues, leftArrowYValues, 3);
	}
	
	private void fillUpArrowArray() {
			upArrowXValues[0] = posX+Sprite.SPRITE_WIDTH*.2;
			upArrowXValues[1] = posX+Sprite.SPRITE_WIDTH*.8;
			upArrowXValues[2] = posX+Sprite.SPRITE_WIDTH*.5;

			upArrowYValues[0] = posY-ARROW_DISTANCE;
			upArrowYValues[1] = posY-ARROW_DISTANCE;
			upArrowYValues[2] = posY-ARROW_DISTANCE-ARROW_SIZE;
	}
	
	private void fillRightArrowArray() {
		rightArrowXValues[0] = posX+Sprite.SPRITE_WIDTH+ARROW_DISTANCE;
		rightArrowXValues[1] = posX+Sprite.SPRITE_WIDTH+ARROW_DISTANCE;
		rightArrowXValues[2] = posX+Sprite.SPRITE_WIDTH+ARROW_DISTANCE+ARROW_SIZE;
	
		rightArrowYValues[0] = posY+Sprite.SPRITE_HEIGHT*.2;
		rightArrowYValues[1] = posY+Sprite.SPRITE_HEIGHT*.8;
		rightArrowYValues[2] = posY+Sprite.SPRITE_HEIGHT*.5;
	}
	
	private void fillDownArrowArray() {
		downArrowXValues[0] = posX+Sprite.SPRITE_WIDTH*.2;
		downArrowXValues[1] = posX+Sprite.SPRITE_WIDTH*.8;
		downArrowXValues[2] = posX+Sprite.SPRITE_WIDTH*.5;
	
		downArrowYValues[0] = posY+Sprite.SPRITE_HEIGHT+ARROW_DISTANCE;
		downArrowYValues[1] = posY+Sprite.SPRITE_HEIGHT+ARROW_DISTANCE;
		downArrowYValues[2] = posY+Sprite.SPRITE_HEIGHT+ARROW_DISTANCE+ARROW_SIZE;
	}
	
	private void fillLeftArrowArray() {
		leftArrowXValues[0] = posX-ARROW_DISTANCE;
		leftArrowXValues[1] = posX-ARROW_DISTANCE;
		leftArrowXValues[2] = posX-ARROW_DISTANCE-ARROW_SIZE;
	
		leftArrowYValues[0] = posY+Sprite.SPRITE_HEIGHT*.2;
		leftArrowYValues[1] = posY+Sprite.SPRITE_HEIGHT*.8;
		leftArrowYValues[2] = posY+Sprite.SPRITE_HEIGHT*.5;
	}
		
}

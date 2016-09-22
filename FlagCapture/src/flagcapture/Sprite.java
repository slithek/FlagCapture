package flagcapture;

import javafx.geometry.*;

import javafx.scene.canvas.*;

public interface Sprite {
		
		final int SPRITE_WIDTH = 14;
		final int SPRITE_HEIGHT = 14;
		final double FRICTION = .18;  //.15-.35 is a good range

		public Rectangle2D getBoundary();
		public boolean intersects(Sprite s);
		public void render(GraphicsContext graphics);
	
}

package spaaace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends GameObject {

	public Enemy(int x, int y, ID id) {
		super(x, y, id);
		velX = 5;
		velY = 5;
	}

	public void tick() {
		x += velX;
		y += velY;

		if (y < 0 || y > Game.HEIGHT - 32)
			velY *= -1;
		if (x < 0 || x > Game.WIDTH - 16)
			velX *= -1;
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 16, 16);
	}
}

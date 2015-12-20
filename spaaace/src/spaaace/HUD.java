package spaaace;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {

	public static int HEALTH = 100;

	public void tick() {

		HEALTH = Game.clamp(HEALTH, 0, 100);
		
	}

	public void render(Graphics g) {

		// health bar
		g.setColor(new Color(150, 150, 150, 55));
		g.fillRect(15, 15, 400, 32);
		g.setColor(new Color(0, 255, 0, 155));
		g.fillRect(15, 15, HEALTH * 4, 32);
		g.setColor(new Color(255, 255, 255, 255));
		g.drawRect(15, 15, 400, 32);
	}
}

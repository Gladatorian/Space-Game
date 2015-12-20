package spaaace;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

	private Thread thread;
	private boolean running = false;
	public BufferedImage background = null;

	private Random r;
	private Handler handler;
	private HUD hud;

	// SOURCE OF THIS CODE:
	// https://www.youtube.com/user/RealTutsGML/
	public Game() {
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));// initialization of key listener

		new Window(WIDTH, HEIGHT, "Spaaace", this);// makes window

		hud = new HUD();// new instance of HUD
		r = new Random();// new instance of random

		handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler));// 
//		handler.addObject(new Enemy(r.nextInt(WIDTH)-1, r.nextInt(HEIGHT)-1, ID.Enemy, handler));
//		int randx = 411;// DO NOT GO PAST 624
//		int randy = 445;// DO NOT GO PAST 445
		int randx = r.nextInt(WIDTH-11)-5;// negative bounds -5
		int randy = r.nextInt(HEIGHT-30)-5;// negative bounds -5
		handler.addObject(new Enemy(randx, randy, ID.Enemy, handler));
		System.out.println("(640) Start X: "+randx);
		System.out.println("(480) Start Y: "+randy);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();// kills thread
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		handler.tick();
		hud.tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);

		hud.render(g);

		g.dispose();
		bs.show();
	}

	public static int clamp(int var, int min, int max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	public static void main(String[] args) {
		new Game();
	}

}

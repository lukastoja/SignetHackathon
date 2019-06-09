package Engine;

import javax.swing.AbstractAction;
import java.awt.AWTEvent;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class EngineCore implements AWTEventListener {
	private int w, h;
	JFrame app;
	Canvas canvas;
	BufferStrategy buffer;
	BufferedImage bi;

	// Objects needed for rendering...
	Graphics graphics = null;
	Graphics2D g2d = null;
	Color background = Color.BLACK;

	public boolean isPaused = false;
	long renderStartTime = -1;
	long renderStopTime = -1;
	long renderDeltaTime = 0;

	EngineEpisode episode;
	EngineEpisode nextEpisode = null;
	HashMap<Integer, Boolean> keyStates = new HashMap<Integer, Boolean>();

	public EngineCore(int w, int h, boolean fullscreen) {
		this.w = w;
		this.h = h;

		// Create game window...
		app = new JFrame();
		app.setIgnoreRepaint(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.getToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
		app.getContentPane().setPreferredSize(new Dimension(w, h));

		if (fullscreen) {
			app.setUndecorated(true);

			// Get graphics configuration...
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gd.getDefaultConfiguration();

			// Change to full screen, 640 x 480, 32 bit color
			gd.setFullScreenWindow(app);
			if (gd.isDisplayChangeSupported()) {
				DisplayMode[] modovi = gd.getDisplayModes();
				for (int i = 0; i < modovi.length; i++) {
					System.out.println(modovi[i].getWidth() + " " + modovi[i].getHeight() + " "
							+ modovi[i].getBitDepth() + " " + modovi[i].getRefreshRate());
				}

				gd.setDisplayMode(new DisplayMode(w, h, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN));
			}
		}

		// Create canvas for painting...
		canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		canvas.setSize(w, h);

		// Add canvas to game window...
		app.add(canvas);
		app.pack();
		app.setVisible(true);

		// Create BackBuffer...
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();

		// Get graphics configuration...
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();

		// Create off-screen drawing surface
		bi = gc.createCompatibleImage(w, h);

		JPanel content = (JPanel) app.getContentPane();
		content.requestFocus();
	}

	public void setEpisode(EngineEpisode episode) {
		this.nextEpisode = episode;
	}

	public void start() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						if (nextEpisode != null) {
							episode = nextEpisode;
							JPanel content = (JPanel) app.getContentPane();
							content.setActionMap(episode.am);
							content.getInputMap().setParent(episode.im);
							nextEpisode = null;
							episode.init(EngineCore.this);
						}
						if (episode != null)
							mainLoop();
					} finally {
						// release resources
						if (graphics != null)
							graphics.dispose();
						if (g2d != null)
							g2d.dispose();
					}
				}
			}
		}.start();
	}

	private void mainLoop() {
		renderStartTime = System.currentTimeMillis();
		episode.getCameraController().setEngine(this);
		addNewElementsToArrays();
		g2d = bi.createGraphics();
		g2d.setColor(background);
		g2d.fillRect(0, 0, w, h);
		
		// Apply camera zoom
		//float zoom = episode.getCameraController().getZoom();
		//g2d.scale(zoom, zoom);

		episode.mainLoop(renderDeltaTime);

		for (int i = 0; i < episode.threads.size(); i++) {
			EngineThread thread = episode.threads.get(i);
			thread.loop(renderDeltaTime);

			if (thread.stopFlag == true) {
				episode.threads.remove(i);
				System.out.println("Running threads " + episode.threads.size());
				i--;
				continue;
			}
		}
		
		checkCollisions();

		for (ViewComponent v : episode.viewComponents) {
			v.draw(g2d, app, episode.cameraController);
		}

		// Blit image and flip...
		graphics = buffer.getDrawGraphics();
		graphics.drawImage(bi, 0, 0, null);
		if (!buffer.contentsLost())
			buffer.show();

		// Let the OS have a little time...
		Thread.yield();
		renderStopTime = System.currentTimeMillis();
		renderDeltaTime = renderStopTime - renderStartTime;
		//System.out.println(renderDeltaTime);
	}

	private void checkCollisions() {
		for(int i=0; i < episode.collisionGroups.size(); i++) {
			CollisionGroup grp = episode.collisionGroups.get(i);
			if(grp.removeFlag) {
				episode.collisionGroups.remove(i);
				i--;
				continue;
			}
			
			grp.checkCollision();
		}
		
	}

	private void addNewElementsToArrays() {
		for(ViewComponent v: episode.viewComponentsToAdd) {
			episode.viewComponents.add(v);
		}
		episode.viewComponentsToAdd.clear();
		
		for(EngineThread thread: episode.threadsToAdd) {
			episode.threads.add(thread);
		}
		episode.threadsToAdd.clear();
		
		for(CollisionGroup grp: episode.collisionGroupsToAdd) {
			episode.collisionGroups.add(grp);
		}
		episode.collisionGroupsToAdd.clear();
	}

	public void attachThread(EngineThread t) {
		episode.threadsToAdd.add(t);
		t.setEngine(this);
	}

	@Override
	public void eventDispatched(AWTEvent event) {
		/*
		 * if(this.keyboardEvent == null)return; if(event instanceof KeyEvent){ KeyEvent
		 * key = (KeyEvent)event; keyboardEvent.keyPressed(key, key.getKeyChar()); }
		 */
	}

	public boolean isKeyPressed(int key) {
		return keyStates.getOrDefault(new Integer(key), false).booleanValue();
	}

	public StisakTipke createStisakTipke(int key, boolean state) {
		return new StisakTipke(key, state);
	}

	public int getWidth() {
		return this.w;
	}

	public int getHeight() {
		return this.h;
	}

	public void hideCursor() {
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		app.getContentPane().setCursor(blankCursor);
	}

	public class StisakTipke extends AbstractAction {
		int key;
		boolean state;

		public StisakTipke(int key, boolean state) {
			this.key = key;
			this.state = state;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (EngineCore.this.episode == null)
				return;

			int stateVal;
			if (state)
				stateVal = KeyEvent.KEY_PRESSED;
			else
				stateVal = KeyEvent.KEY_RELEASED;
			keyStates.put(new Integer(key), new Boolean(state));
			EngineCore.this.episode.keyPressed(key, stateVal);
		}

	}
}

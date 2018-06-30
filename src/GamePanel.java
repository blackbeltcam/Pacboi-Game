import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
	ObjectManager om;
	MazeObject maze;
	PacboiObject po;
	ArrayList<GhostObject> ghostList = new ArrayList();
	static final int numRows = 17;
	static final int numCols = 15;
	static final int empty = 0;
	static final int fill = 1;
	static final int locked = 2;
	static final int restricted = 3;
	static final int key = 4;
	static final int pacboi = 5;
	static final int ghost = 6;
	static final int watermark = 7;
	static final int scoreboard = 8;
	Font titleFont;
	Font subFont;
	public static BufferedImage ghostImg;
	public static BufferedImage pacboiImg;
	int pacboiCol = 0;
	int pacboiRow = 13;
	int currentState = 0;
	int speed = 5;
	int PacboiStartX = 0;
	int PacboiStartY = 697;
	Timer timer;
	boolean keyPressedR = false;
	boolean keyPressedD = false;
	boolean keyPressedL = false;
	boolean keyPressedU = false;
	int[][] block ={ { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}, { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8}, { 1, 0, 0, 0, 0, 0, 0/* this one */, 0, 0, 1, 1, 1, 7, 7, 7 },
			{ 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 }, { 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 4 },
			{ 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0/* this one */, 0, 0, 0, 0 },
			{ 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0 }, { 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0 },
			{ 1, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 }, { 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0 },
			{ 1, 0, 1, 0, 1, 1, 0, 0, 0, 0/* this one */, 0, 0, 0, 0, 0 },
			{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 }, { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 } };

	MazeObject[][] grid = new MazeObject[numRows][numCols];

	public GamePanel() throws IOException {
		timer = new Timer(1000 / 60, this);
		po = new PacboiObject(PacboiStartX, PacboiStartY);
		ghostList.add(new GhostObject(275, 92));
		ghostList.add(new GhostObject(550, 230));
		ghostList.add(new GhostObject(330, 645));
		om = new ObjectManager(po, ghostList);

		ghostImg = ImageIO.read(this.getClass().getResourceAsStream("orangeGhost2.png"));
		pacboiImg = ImageIO.read(this.getClass().getResourceAsStream("Pacboi.png"));
		drawMaze();
		titleFont = new Font("Arial", Font.BOLD, 48);
		subFont = new Font("Arial", Font.PLAIN, 20);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		if (currentState == 0) {
			drawMenuState(g);
		} else if (currentState == 1) {
			om.draw(g);
		}
		repaint();
	}

	public void drawMaze() {
		for (int i = 0; i < numRows; i += 1) {
			for (int j = 0; j < numCols; j += 1) {
				MazeObject m = new MazeObject(i, j, block[i][j]);
				om.addMazeObject(m);
			}
		}
	}

	public void drawMenuState(Graphics g) {
		// To activate, change currentState variable to 0
		g.setColor(Color.PINK);
		g.fillRect(0, 0, Pacboi.height, Pacboi.width);
		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("Welcome To Pacboi!", 90, 100);
		g.setFont(subFont);
		g.setColor(Color.WHITE);
		g.drawString("Press Space to Start", 200, 280);
		g.setColor(Color.BLUE);
		g.drawString("Press I for Instructions", 350, 400);
		g.setColor(Color.BLACK);
		g.drawString("A frusterating game for people who have a lot of time.", 90, 125);

	}

	public void move() {

		// if (keyPressedL && keyPressedU) {
		// po.y -= speed;
		// po.x -= speed;
		// } else if (keyPressedL && keyPressedD) {
		// po.x -= speed;
		// po.y += speed;
		// } else if (keyPressedR && keyPressedU) {
		// po.x += speed;
		// po.y -= speed;
		// } else if (keyPressedR && keyPressedD) {
		// po.x += speed;
		// po.y += speed;
		// }

		if (keyPressedR == true) {
			Rectangle R = new Rectangle(po.x + speed, po.y, po.width, po.height);
			if (om.checkMazeCollision(R)) {
				die();
				keyPressedR=false;
			} else {
				po.x += speed;
			}

		} else if (keyPressedL == true) {
			Rectangle L = new Rectangle(po.x - speed, po.y, po.width, po.height);
			if (om.checkMazeCollision(L)) {
				die();
				keyPressedL=false;
			} else {
				po.x -= speed;
			}

		}
		if (keyPressedU == true) {
			Rectangle U = new Rectangle(po.x, po.y - speed, po.width, po.height);
			if (om.checkMazeCollision(U)) {
				die();
				keyPressedU=false;
			} else {
				po.y -= speed;
			}

		} else if (keyPressedD == true) {
			Rectangle D = new Rectangle(po.x, po.y + speed, po.width, po.height);
			if (om.checkMazeCollision(D)) {
				die();
				keyPressedD=false;
			} else {
				po.y += speed;
			}

		}
		if (po.x + PacboiObject.width > Pacboi.width) {
			po.x = Pacboi.width - PacboiObject.width;
		}
		if (po.y < 0) {
			po.y = 0;
		}
		if (po.y + PacboiObject.height > Pacboi.height) {
			po.y = Pacboi.height - PacboiObject.height;
		}
		if (po.x < 0) {
			po.x = 0;
		}
		om.checkGhostCollision(po.pacCollision);
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		e.consume();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			currentState = 1;
		}

		if (e.getKeyCode() == KeyEvent.VK_I) {
			JOptionPane.showMessageDialog(null,
					"Use Arrow Keys to Move \n The aim of the game is ot grab the key, unlock the second area, and finish the level. \n It is not as simple as it seems though as PacBoi moves very fast so it is hard to control him.");
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keyPressedR = true;

		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyPressedR = true;

		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			keyPressedU = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_W) {
			keyPressedU = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keyPressedL = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			keyPressedL = true;

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keyPressedD = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			keyPressedD = true;
		}
		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			keyPressedR = false;

		} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			keyPressedU = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			keyPressedL = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			keyPressedD = false;
		}
		e.consume();
	}

	public void die() {
		JOptionPane.showMessageDialog(null, "u suck scrub");
		 po.x=PacboiStartX;
		 po.y=PacboiStartY;
		 om.incrementDeath();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		om.update();
		// om.checkCollision();
		move();
		
	}
}

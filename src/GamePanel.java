import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
	static final int numRows = 15;
	static final int numCols = 15;
	static final int empty = 0;
	static final int fill = 1;
	static final int locked = 2;
	static final int restricted = 3;
	static final int key = 4;
	static final int pacboi = 5;
	static final int ghost = 6;
	static final int watermark = 7;
	Font titleFont;
	Font subFont;
	public static BufferedImage ghostImg;
	public static BufferedImage pacboiImg;
	int pacboiCol = 0;
	int pacboiRow = 13;
	int currentState = 0;
	double speed = 10;
	Timer timer;
	boolean keyPressedR = false;
	boolean keyPressedD = false;
	boolean keyPressedL = false;
	boolean keyPressedU = false;
	int[][] block = { { 1, 0, 0, 0, 0, 0, 0/* this one */, 0, 0, 1, 1, 1, 7, 7, 7 },
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
		po = new PacboiObject(50, 0);
		ghostList.add(new GhostObject(275, 0));
		ghostList.add(new GhostObject(550, 138));
		ghostList.add(new GhostObject(330, 553));
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

			po.x += speed;

		} else if (keyPressedL == true) {
			po.x -= speed;

		}
		if (keyPressedU == true) {
			po.y -= speed;

		} else if (keyPressedD == true) {
			po.y += speed;

		}
		if (po.x + MazeObject.blockWidth > Pacboi.width) {
			po.x = Pacboi.width - MazeObject.blockWidth;
		}
		if (po.y < 0) {
			po.y = 0;
		}
		if (po.y + MazeObject.blockHeight > Pacboi.height) {
			po.y = Pacboi.height - MazeObject.blockHeight;
		}
		if (po.x < 0) {
			po.x = 0;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

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

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		om.update();
		om.checkCollision();
		move();
	}
}

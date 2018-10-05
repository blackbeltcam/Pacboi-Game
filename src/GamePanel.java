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
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
	ObjectManager om;
	MazeObject maze;
	PacboiObject po;
	ArrayList<GhostObject> ghostList = new ArrayList<GhostObject>();
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
	static final int ending = 9;
	static final Integer countdownMax = 3;
	static final int titleState = 0;
	static final int gameState = 1;
	Font titleFont;
	Font subFont;
	Date startDieTime;
	Integer timeLeft;
	Font deathFont;
	Integer score = 10000;
	private static Integer deathCounter = 0;
	int currentState = titleState;
	public static BufferedImage ghostImg;
	public static BufferedImage pacboiImg;
	public static BufferedImage keyImg;
	public static BufferedImage keyholeImg;
	public static BufferedImage pbacImg;
	public static BufferedImage gbacImg;
	public static BufferedImage endImg;
	public static ImageIcon questionmarkImg;
	
	int pacboiCol = 0;
	int pacboiRow = 13;
	boolean startScore = false;

	int speed = 5;
	int PacboiStartX = 0;
	int PacboiStartY = 697;
	int dyingTime = 3;
	int fps = 60;
	Timer timer;

	boolean keyPressedR = false;
	boolean keyPressedD = false;
	boolean keyPressedL = false;
	boolean keyPressedU = false;
	boolean dying;
	int[][] block = { { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8 }, { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8 },
			{ 1, 0, 0, 0, 0, 0, 0/* this one */, 0, 0, 1, 1, 1, 7, 7, 7 },
			{ 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 }, { 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 4 },
			{ 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0/* this one */, 0, 0, 0, 0 },
			{ 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 9 }, { 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 9 },
			{ 1, 0, 2, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 9 }, { 1, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 0, 9 },
			{ 1, 0, 1, 0, 1, 1, 0, 0, 0, 0/* this one */, 0, 0, 0, 0, 9 },
			{ 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 9 }, { 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 } };

	MazeObject[][] grid = new MazeObject[numRows][numCols];

	public GamePanel() throws IOException {
		timer = new Timer(1000 / fps, this);

		po = new PacboiObject(PacboiStartX, PacboiStartY);
		ghostList.add(new GhostObject(275, 92));
		ghostList.add(new GhostObject(550, 230));
		ghostList.add(new GhostObject(330, 645));
		om = new ObjectManager(po, ghostList, fps);

		ghostImg = ImageIO.read(this.getClass().getResourceAsStream("gbac.png"));
		pacboiImg = ImageIO.read(this.getClass().getResourceAsStream("Pacboi.png"));
		keyImg = ImageIO.read(this.getClass().getResourceAsStream("Key.png"));
		keyholeImg = ImageIO.read(this.getClass().getResourceAsStream("KeyHole.png"));
		pbacImg = ImageIO.read(this.getClass().getResourceAsStream("derpybac.png"));
		gbacImg = ImageIO.read(this.getClass().getResourceAsStream("gbac.png"));
		endImg = ImageIO.read(this.getClass().getResourceAsStream("ending.png"));
		questionmarkImg = new ImageIcon(getClass().getResource("questionmark.png"));
		drawMaze();
		deathFont = new Font("Comic Sans MS", Font.BOLD, 30);
		titleFont = new Font("Arial", Font.BOLD, 48);
		subFont = new Font("Arial", Font.PLAIN, 20);
		timer.start();
	}

	public void paintComponent(Graphics g) {

		if (currentState == titleState) {
			drawMenuState(g);
		} else if (currentState == gameState) {
			om.draw(g);

			g.setColor(Color.WHITE);
			// deaths

			g.setFont(deathFont);
			g.drawString("Deaths", 2, 30);
			g.drawString(deathCounter.toString(), 50, 80);
			// score
			g.setFont(deathFont);
			g.drawString("Score", 515, 30);
			g.drawString(score.toString(), 515, 80);
		}
		if (dying) {

			Integer c = (countdownMax - timeLeft / 1000);

			g.setColor(Color.RED);
			g.fillRect(220, 250, 230, 200);
			g.setColor(Color.WHITE);
			g.drawString(c.toString(), 325, 400);
			g.drawString("  YOU DIED", 230, 280);
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

		g.setColor(new Color(110088));
		g.fillRect(0, 0, Pacboi.width, Pacboi.height);
		// pbacImg.
		g.drawImage(pbacImg, 230, 300, 60, 60, null);
		g.drawImage(gbacImg, 330, 300, 60, 60, null);
		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("Welcome To Pacboi!", 90, 100);
		g.setFont(subFont);
		g.setColor(Color.WHITE);
		g.drawString("A frustrating game for people who have a lot of time.", 90, 125);
		g.setFont(deathFont);
		g.drawString("Press Space to Start", 180, 420);
		g.setColor(Color.BLUE);
		g.drawString("Press I for Instructions", 150, 460);
		g.setColor(Color.BLACK);

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
			startScore = true;
			Rectangle R = new Rectangle(po.x + speed, po.y, po.width, po.height);
			//if (!MazeObject.keyCollide && R.intersects(MazeObject.getLockRectangle())) {
			//}

			if (om.checkMazeCollision(R)) {
				die();
				keyPressedR = false;
			} else {
				po.x += speed;

			}
		} else if (keyPressedL == true) {
			Rectangle L = new Rectangle(po.x - speed, po.y, po.width, po.height);
			if (om.checkMazeCollision(L)) {
				die();
				keyPressedL = false;
			} else {
				po.x -= speed;
			}

		}
		if (keyPressedU == true) {
			Rectangle U = new Rectangle(po.x, po.y - speed, po.width, po.height);
			if (om.checkMazeCollision(U)) {
				die();
				keyPressedU = false;
			} else {
				po.y -= speed;
			}

		} else if (keyPressedD == true) {
			Rectangle D = new Rectangle(po.x, po.y + speed, po.width, po.height);
			if (om.checkMazeCollision(D)) {
				die();
				keyPressedD = false;
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
		boolean gCollide = om.checkGhostCollision(po.pacCollision);
		if (gCollide && !dying) {
			die();
		}
		boolean kCollide = om.checkKeyCollision(po.pacCollision);
		if (kCollide) {
			MazeObject.KeyCollide(true);
		}
		boolean eCollide = om.checkEndCollision(po.pacCollision);
		if (eCollide) {
			win();
		}
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
		if (dying) {
			clearKeyFlags();
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			currentState = 1;
		}

		if (e.getKeyCode() == KeyEvent.VK_I) {
			JOptionPane.showMessageDialog(this,
					"Use Arrow Keys to Move \n The aim of the game is to grab the key, "
							+ "unlock the second area, and finish the level. \n It is not as simple"
							+ " as it seems though as PacBoi moves very fast so it is hard to control him.",
					"Pacboi Instructions", JOptionPane.INFORMATION_MESSAGE, questionmarkImg);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keyPressedR = true;

		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			keyPressedR = true;

		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keyPressedU = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_W) {
			keyPressedU = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keyPressedL = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			keyPressedL = true;

		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keyPressedD = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_S) {
			keyPressedD = true;
		}
		e.consume();
	}

	private void clearKeyFlags() {
		keyPressedR = false;
		keyPressedL = false;
		keyPressedD = false;
		keyPressedU = false;
	}

	public void incrementDeath() {
		deathCounter++;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			keyPressedR = false;

		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			keyPressedU = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			keyPressedL = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			keyPressedD = false;
		}
		e.consume();
	}

	public void die() {
		startDieTime = new Date();
		dying = true;
		incrementDeath();
		MazeObject.keyCollide = false;
	}
	
	public void win() {
	System.out.println("working");
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		om.update();

		// om.checkCollision();
		move();
		if (currentState == gameState && !dying && startScore) {
			score--;
		}
		if (score == 0 && dying == false) {
			die();
		}

		if (dying) {

			Date now = new Date();
			timeLeft = (int) (now.getTime() - startDieTime.getTime());
			if (timeLeft > 3000) {
				clearKeyFlags();
				po.x = PacboiStartX;
				po.y = PacboiStartY;
				MazeObject.keyCollide = false;
				startScore = false;
				score = 10000;
				dying = false;

			}
		}
	}

}

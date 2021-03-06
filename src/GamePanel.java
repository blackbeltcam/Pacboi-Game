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
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	static final int menuState = 0;
	static final int gameState = 1;
	static final int winState = 2;
	Font titleFont;
	Font subFont;
	Font winFont;
	Font deathFont;
	Font subwinFont;
	Date startDieTime;
	Integer timeLeft;
	Integer score = 3000;
	private static Integer deathCounter = 0;
	int currentState = menuState;
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
	int PacboiStartX = 5;
	int PacboiStartY = 695;
	int dyingTime = 3;
	int fps = 60;
	Timer timer;

	boolean keyPressedR = false;
	boolean keyPressedD = false;
	boolean keyPressedL = false;
	boolean keyPressedU = false;
	boolean ghostDie = false;
	boolean wallDie = false;
	boolean timeDie = false;
	boolean dying;
	boolean winning;
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
		winFont = new Font("Phosphate", Font.BOLD, 60);
		subwinFont = new Font("Raanana", Font.BOLD, 40);
		timer.start();
	}

	public void paintComponent(Graphics g) {

		if (currentState == menuState) {
			drawMenuState(g);
		} else if (currentState == gameState) {
			om.draw(g);

			g.setColor(Color.WHITE);

			g.setFont(deathFont);
			g.drawString("Deaths", 2, 30);
			g.drawString(deathCounter.toString(), 50, 80);
			g.setFont(deathFont);
			g.drawString("Score", 515, 30);
			g.drawString(score.toString(), 515, 80);
			g.setFont(subFont);
			g.setColor(new Color(0x5522AA));
			g.drawString("A game by", 555, 110);
			g.drawString("Cameron T", 555, 135);

		} else if (currentState == winState) {
			drawWinState(g);
		}
		if (dying) {

			Integer c = (countdownMax - timeLeft / 1000);

			g.setFont(deathFont);
			g.setColor(Color.RED);
			g.fillRect(220, 250, 230, 200);
			g.setColor(Color.YELLOW);
			g.drawString(c.toString(), 325, 400);
			g.drawString("  YOU DIED", 230, 280);
			g.setColor(Color.CYAN);
			if (wallDie) {
				g.drawString("You hit a wall", 230, 310);
			} else if (ghostDie) {
				g.drawString("You hit a ghost", 226, 310);
			} else if (timeDie) {
				g.drawString("Time caught up", 223, 310);
				g.drawString("to you!", 285, 350);
			}
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
		g.drawImage(pbacImg, 230, 300, 60, 60, null);
		g.drawImage(gbacImg, 330, 300, 60, 60, null);
		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("Welcome To Pacboi!", 90, 100);
		g.setFont(subFont);
		g.setColor(Color.WHITE);
		g.drawString("A frustrating game for people who have a lot of time.", 90, 125);
		g.setColor(Color.YELLOW);
		g.drawString("Use Arrow Keys to Move. The aim of the game is to grab the key to unlock ", 20, 570);
		g.drawString("the second area, and finish the level. It is not as simple as it seems though", 20, 600);
		g.drawString("as PacBoi moves very fast so it is hard to control him.", 100, 630);
		g.setFont(deathFont);
		g.drawString("Instructions", 250, 500);
		g.setColor(Color.BLUE);
		g.drawString("Press Space to Start", 180, 420);

		g.setColor(Color.BLACK);

	}

	public void drawWinState(Graphics g) {
		PacboiObject.x = PacboiStartX;
		PacboiObject.y = PacboiStartY;
		g.setColor(new Color(65, 105, 225));
		g.fillRect(0, 0, Pacboi.width, Pacboi.height);
		g.setColor(new Color(225, 221, 0));
		g.setFont(winFont);
		g.drawString("CONGRATULATIONS!", 70, 200);
		g.setFont(subwinFont);
		g.setColor(Color.CYAN);
		g.drawString("Try to beat your ", 175, 350);
		g.drawString("time of ", 220, 500);
		g.setColor(Color.MAGENTA);
		g.drawString("" + score, 380, 500);
		g.setColor(Color.BLACK);
		g.drawString("Press ENTER to Restart", 100, 700);

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
			Rectangle R = new Rectangle(PacboiObject.x + speed, PacboiObject.y, PacboiObject.width,
					PacboiObject.height);
			if (!MazeObject.keyCollide && R.intersects(MazeObject.getLockRectangle())) {

			}

			else if (om.checkMazeCollision(R)) {
				die();
				wallDie = true;
				keyPressedR = false;
			} else {
				PacboiObject.x += speed;

			}
		} else if (keyPressedL == true) {
			Rectangle L = new Rectangle(PacboiObject.x - speed, PacboiObject.y, PacboiObject.width,
					PacboiObject.height);
			if (om.checkMazeCollision(L)) {
				die();
				wallDie = true;
				keyPressedL = false;
			} else {
				PacboiObject.x -= speed;
			}

		}
		if (keyPressedU == true) {
			Rectangle U = new Rectangle(PacboiObject.x, PacboiObject.y - speed, PacboiObject.width,
					PacboiObject.height);
			if (om.checkMazeCollision(U)) {
				die();
				wallDie = true;
				keyPressedU = false;
			} else {
				PacboiObject.y -= speed;
			}

		} else if (keyPressedD == true) {
			Rectangle D = new Rectangle(PacboiObject.x, PacboiObject.y + speed, PacboiObject.width,
					PacboiObject.height);
			if (om.checkMazeCollision(D)) {
				die();
				wallDie = true;
				keyPressedD = false;
			} else {
				PacboiObject.y += speed;
			}

		}
		if (PacboiObject.x + PacboiObject.width > Pacboi.width) {
			PacboiObject.x = Pacboi.width - PacboiObject.width;
		}
		if (PacboiObject.y < 0) {
			PacboiObject.y = 0;
		}
		if (PacboiObject.y + PacboiObject.height > Pacboi.height) {
			PacboiObject.y = Pacboi.height - PacboiObject.height;
		}
		if (PacboiObject.x < 0) {
			PacboiObject.x = 0;
		}
		boolean gCollide = om.checkGhostCollision(po.pacCollision);
		if (gCollide && !dying) {
			die();
			ghostDie = true;
		}
		boolean kCollide = om.checkKeyCollision(po.pacCollision);
		if (kCollide) {
			MazeObject.KeyCollide(true);
		}
		boolean eCollide = om.checkEndCollision(po.pacCollision);
		if (eCollide && currentState == gameState) {
			currentState = winState;

			winning = true;
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		e.consume();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (dying) {
			clearKeyFlags();
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER && currentState == winState) {
			startScore = false;
			startDieTime = new Date();
			MazeObject.keyCollide = false;
			winning = false;
			currentState = menuState;
			repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE && currentState == menuState) {
			currentState = gameState;
			winning = false;
			score = 3000;
			repaint();
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT && winning == false && currentState == gameState) {
			keyPressedR = true;

		}

		if (e.getKeyCode() == KeyEvent.VK_D && winning == false && currentState == gameState) {
			keyPressedR = true;

		}
		if (e.getKeyCode() == KeyEvent.VK_UP && winning == false && currentState == gameState) {
			keyPressedU = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_W && winning == false && currentState == gameState) {
			keyPressedU = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && winning == false && currentState == gameState) {
			keyPressedL = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_A && winning == false && currentState == gameState) {
			keyPressedL = true;

		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && winning == false && currentState == gameState) {
			keyPressedD = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_S && winning == false && currentState == gameState) {
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

	@Override
	public void actionPerformed(ActionEvent e) {
		om.update();
		move();
		if (currentState == gameState && !dying && startScore) {
			score--;
		}
		if (score == 0 && dying == false) {
			die();
			timeDie = true;
		}

		if (dying) {

			Date now = new Date();
			timeLeft = (int) (now.getTime() - startDieTime.getTime());
			if (timeLeft > 3000) {
				clearKeyFlags();
				PacboiObject.x = PacboiStartX;
				PacboiObject.y = PacboiStartY;
				wallDie = false;
				ghostDie = false;
				timeDie = false;
				MazeObject.keyCollide = false;
				startScore = false;
				score = 3000;
				dying = false;

			}
		}
	}

}

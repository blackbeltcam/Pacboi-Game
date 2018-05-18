import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import javafx.scene.input.KeyCode;

public class GamePanel extends JPanel implements KeyListener {
	ObjectManager om;
	MazeObject maze;
	PacboiObject po;
	GhostObject go;
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
	int currentState = 1;
	int speed = 10;
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
		timer = new Timer(1000 / 60, null);
		po = new PacboiObject(0, 0);
		ghostList.add(new GhostObject(275, 0));
		ghostList.add(new GhostObject(550, 138));
		ghostList.add(new GhostObject(330, 553));
		om = new ObjectManager(po, ghostList, this);
		go = new GhostObject(100, 100);
		
		
		ghostImg = ImageIO.read(this.getClass().getResourceAsStream("orangeGhost2.png"));
		pacboiImg = ImageIO.read(this.getClass().getResourceAsStream("Pacboi.png"));
		drawMaze();
		titleFont = new Font("Arial", Font.BOLD, 48);
		subFont = new Font("Arial", Font.PLAIN, 20);
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
		g.setColor(Color.PINK);
		g.fillRect(0, 0, Pacboi.height, Pacboi.width);
		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("Welcome To Pacboi!", 90, 100);
		g.setFont(subFont);
		g.drawString("a frusterating game for people who have a lot of time", 105, 500);

	}

	public void move() {

		if (keyPressedR == true) {
			System.out.println("it move");
			if (po.x + MazeObject.blockWidth > Pacboi.width) {
				po.x = Pacboi.width - MazeObject.blockWidth;
			}
			else {
				po.x += speed;
			}

		} else if (keyPressedU == true) {
			po.y -= speed;
			if (po.y < 0) {
				po.y = 0;
			}
		} else if (keyPressedL == true) {
			po.x -= speed;
			if (po.x < 0) {
				po.x = 0;
			}
		} else if (keyPressedD == true) {
			po.y += speed;
			if (po.y + MazeObject.blockHeight > Pacboi.height) {
				po.y = Pacboi.height - MazeObject.blockHeight;
			}
		}
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("it move pt. 2");
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
			keyPressedR = true;
			System.out.println("it move pt. 42");

		} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar()=='w') {
			keyPressedU = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar()=='a') {
			keyPressedL = true;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar()=='s') {
			keyPressedD = true;
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
			keyPressedR = false;
			System.out.println("it move pt. 11");

		}
		else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar()=='w') {
			keyPressedU = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar()=='a') {
			keyPressedL = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar()=='s') {
			keyPressedD = false;
		}
	}
}

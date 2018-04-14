import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {
	ObjectManager om;
	Maze maze;
	static final int numRows = 15;
	static final int numCols = 15;
	static final int empty = 0;
	static final int fill = 1;
	static final int locked=2;
	static final int restricted=3;
	static final int key=4;
	static final int pacboi=5;
	static final int ghost = 6;
	static final int watermark=7;
	Font titleFont;
	Font subFont;
	public static BufferedImage ghostImg;
	int currentState = 1;
	int [][] block= {{1,0,0,0,0,0,6,0,0,1,1,1,7,7,7},
	                 {1,0,1,1,0,0,6,0,0,1,1,1,1,1,1},
	                 {1,0,1,1,0,0,6,0,0,1,1,1,1,1,4},
	                 {1,0,1,1,0,0,6,0,0,0,6,0,0,0,0},
	                 {1,0,1,1,0,0,1,0,0,0,6,0,0,0,0},
	                 {1,0,0,0,0,0,1,0,0,0,6,0,0,0,0},
	                 {1,0,0,0,0,0,1,0,0,0,6,0,0,0,0},
	                 {1,0,1,1,1,1,1,1,1,1,1,1,1,1,1},
	                 {1,0,1,0,0,0,0,0,0,1,0,0,1,0,0},
	                 {1,0,1,0,1,1,0,0,0,1,0,1,0,0,0},
	                 {1,0,2,0,0,0,0,0,0,1,0,0,0,0,0},
	                 {1,0,1,0,1,1,0,1,1,1,0,0,1,0,0},
	                 {1,0,1,0,1,1,0,0,0,6,0,0,0,0,0},
	                 {5,0,1,0,0,0,0,0,0,6,0,0,1,0,0},
	                 {1,0,1,0,0,0,0,0,0,6,0,0,0,1,1}};
	                
	Maze[][] grid = new Maze[numRows][numCols];

	public GamePanel() throws IOException {
		om = new ObjectManager();
		ghostImg=ImageIO.read(this.getClass().getResourceAsStream("orangeGhost2.png"));
		
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
				Maze m=new Maze(i,j, block[i][j]);
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			System.out.println("u press write");
		}
		else if(e.getKeyCode()==KeyEvent.VK_UP) {
			System.out.println("u press up");
		}
		else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			System.out.println("u press left");
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN) {
			System.out.println("u press down");
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

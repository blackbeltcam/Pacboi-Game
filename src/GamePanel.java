import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	ObjectManager om;
	Maze maze;
	static final int numRows = 15;
	static final int numCols = 15;
	Font titleFont;
	Font subFont;
	int currentState = 1;
	int [][] controlGrid;
	Maze[][] grid = new Maze[numRows][numCols];

	public GamePanel() {
		om = new ObjectManager();
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
				Maze m=new Maze(i,j);
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
}

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	ObjectManager om;
	Maze maze;
	static final int numRows = 20;
	static final int numCols = 20;
	public GamePanel() {
		om = new ObjectManager();
		maze = new Maze(350, 350);
		
		om.addMazeObject(maze);
	}

	public void paintComponent(Graphics g) {
		om.draw(g);
		repaint();
	}
	public void DrawMaze() {
		for (int i = 0; i < numRows; i += 1) {
			for (int j = 0; j < numCols; i += 1) {

			}
		}
	}
}

import java.awt.Color;
import java.awt.Graphics;

public class Maze {
	
	
	int height = 700;
	int x = 300;
	int y = 400;

	public Maze(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		
		g.setColor(Color.BLUE);
		g.fillRect(x, y, Pacboi.width/numCols, Pacboi.height/numRows);
	}
}
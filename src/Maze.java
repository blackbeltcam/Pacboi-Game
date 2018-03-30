import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Maze {
	int row;
	int col;
	int blockHeight = Pacboi.height / GamePanel.numRows;
	int blockWidth = Pacboi.width / GamePanel.numCols;
	int x;
	int y;
	Random ran = new Random();

	public Maze(int row, int col) {
		 this.row=row;
		 this.col=col;
		 x= col*blockWidth;
		 y= row*blockHeight;
	}

	public void draw(Graphics g) {
		
//		g.setColor(new Color(ran.nextInt(0xFFFFFF)));
		g.fillRect(x, y, blockWidth, blockHeight);

	}
}
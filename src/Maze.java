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
	int block;
	Random ran = new Random();

	public Maze(int row, int col, int block) {
		this.row = row;
		this.col = col;
		this.block = block;
		x = col * blockWidth;
		y = row * blockHeight;
	}

	public void draw(Graphics g) {

		// g.setColor(new Color(ran.nextInt(0xFFFFFF)));

		if (block == GamePanel.empty) {
			g.setColor(Color.GRAY);
		} else if (block == GamePanel.fill) {
			g.setColor(Color.BLUE);
		}

		else if (block == GamePanel.key) {
			g.setColor(Color.YELLOW);
		} else if (block == GamePanel.locked) {
			g.setColor(Color.CYAN);
		
		} else if (block == GamePanel.watermark) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.BLACK);
		}
		g.fillRect(x, y, blockWidth, blockHeight);

		if (block == GamePanel.ghost) {
			g.drawImage(GamePanel.ghostImg, x, y, blockWidth, blockHeight, null);
			
		}
		else if (block == GamePanel.pacboi) {
			g.drawImage(GamePanel.pacboiImg, x, y, blockWidth, blockHeight, null);
		}

	}
	public void moveRight () {
		col++;
		x=col*blockWidth;
	}
}
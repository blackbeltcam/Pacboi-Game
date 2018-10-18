import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MazeObject {
	int row;
	int col;
	static int blockHeight = Pacboi.height / GamePanel.numRows;
	static int blockWidth = Pacboi.width / GamePanel.numCols;
	int x;
	int y;
	int block;
	static boolean keyCollide = false;
	Rectangle mazeCollision;
	Rectangle keyCollision;
	static Rectangle endCollision;
	static Rectangle lockCollision;
	Random ran = new Random();

	public MazeObject(int row, int col, int block) {
		this.row = row;
		this.col = col;
		this.block = block;
		x = col * blockWidth;
		y = row * blockHeight;
		mazeCollision = new Rectangle(x, y, MazeObject.blockWidth, MazeObject.blockHeight);
		keyCollision = new Rectangle(x, y, MazeObject.blockWidth, MazeObject.blockHeight);
		endCollision = new Rectangle(x, 460, MazeObject.blockWidth, 276);
		if (block == GamePanel.locked) {
			lockCollision = new Rectangle(x, y, MazeObject.blockWidth, MazeObject.blockHeight);
		}
	}

	public static void KeyCollide(boolean collide) {
		keyCollide = collide;
	}

	public void draw(Graphics g) {

		// g.setColor(new Color(ran.nextInt(0xFFFFFF)));

		if (block == GamePanel.empty) {
			g.setColor(Color.GRAY);

		} else if (block == GamePanel.fill) {
			g.setColor(Color.BLUE);
			
		}  else if (block == GamePanel.key) {
			g.setColor(Color.YELLOW);

		} else if (block == GamePanel.locked) {
			g.setColor(Color.CYAN);

		} else if (block == GamePanel.watermark) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.BLACK);
		}
		g.fillRect(x, y, blockWidth, blockHeight);
		if (block == GamePanel.key && !keyCollide) {
			g.drawImage(GamePanel.keyImg, x, y, 40, 40, null);
		}

		if (block == GamePanel.locked) {
			g.drawImage(GamePanel.keyholeImg, x + 3, y + 3, 40, 40, null);

		}
		
		if (block == GamePanel.ending) {
			 g.drawImage(GamePanel.endImg, x, y, 70, 70, null);
		
		}

		mazeCollision.setBounds(x, y, MazeObject.blockWidth, MazeObject.blockHeight);
	}

	public static Rectangle getLockRectangle() {
		return lockCollision;
	}

	public void update() {

	}
}
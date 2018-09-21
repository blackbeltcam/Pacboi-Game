import java.awt.Graphics;
import java.awt.Rectangle;

public class GhostObject {
	int x;
	int y;
	int direction = 6;
	Rectangle ghostCollision;

	public GhostObject(int x, int y) {
		this.x = x;
		this.y = y;
		ghostCollision = new Rectangle(x+10, y+10, MazeObject.blockWidth-20, MazeObject.blockHeight-20);
	}

	public void draw(Graphics g) {
		g.drawImage(GamePanel.ghostImg, (int) x, (int) y, MazeObject.blockWidth, MazeObject.blockHeight, null);
		ghostCollision.setBounds(x+10, y+10, MazeObject.blockWidth-20, MazeObject.blockHeight-20);
	}

	public void update() {
		if (y > Pacboi.height-MazeObject.blockHeight || y < 0) {
			direction = -direction;
		}
		y += direction;
		ghostCollision.setBounds(x+10, y+10, MazeObject.blockWidth-20, MazeObject.blockHeight-20);
	}

}

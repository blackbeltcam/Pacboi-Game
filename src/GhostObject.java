import java.awt.Graphics;

public class GhostObject {
	int x;
	int y;

	public GhostObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		g.drawImage(GamePanel.ghostImg, x, y, MazeObject.blockWidth, MazeObject.blockHeight, null);
	}
}

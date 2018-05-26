import java.awt.Graphics;

public class GhostObject extends GameObject {
	double x;
	double y;

	public GhostObject(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Graphics g) {
		g.drawImage(GamePanel.ghostImg, (int)x, (int)y, MazeObject.blockWidth, MazeObject.blockHeight, null);
	}
	public void update() {
		
	}
}

import java.awt.Graphics;

public class PacboiObject extends GameObject {
	int x;
	int y;
public PacboiObject(int x, int y) {
	this.x=x;
	this.y=y;
}
public void draw(Graphics g) {
	g.drawImage(GamePanel.pacboiImg, x, y, MazeObject.blockWidth, MazeObject.blockHeight, null);
}
public void update() {
	
}
}

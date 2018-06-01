import java.awt.Graphics;
import java.awt.Rectangle;

public class PacboiObject  {
	int x;
	int y;
	Rectangle pacCollision;
public PacboiObject(int x, int y) {
	this.x=x;
	this.y=y;
	pacCollision=new Rectangle (x,y,MazeObject.blockWidth, MazeObject.blockHeight);
}
public void draw(Graphics g) {
	g.drawImage(GamePanel.pacboiImg, x, y, MazeObject.blockWidth, MazeObject.blockHeight, null);
	pacCollision.setBounds(x,y,MazeObject.blockWidth, MazeObject.blockHeight);
}
public void update() {
	
}
}

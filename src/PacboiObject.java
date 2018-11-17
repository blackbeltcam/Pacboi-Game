import java.awt.Graphics;
import java.awt.Rectangle;

public class PacboiObject  {
	static public int x;
	static public int y;
	static final int width=35;
	static final int height=35;
	Rectangle pacCollision;
public PacboiObject(int newX, int newY) {
	x=newX;
	y=newY;
	pacCollision=new Rectangle (x,y,width, height);
}
public void draw(Graphics g) {
	if (MazeObject.keyCollide) {
		g.drawImage(GamePanel.keyImg, PacboiObject.x-20, PacboiObject.y-10, 20, 20, null);
		}
	g.drawImage(GamePanel.pacboiImg, x, y, width, height, null);
	pacCollision.setBounds(x,y, width, height);
}
public void update() {
	
}
}

import java.awt.Graphics;
import java.awt.Rectangle;

public class PacboiObject  {
	static public int x;
	static public int y;
	static final int width=35;
	static final int height=35;
	Rectangle pacCollision;
public PacboiObject(int x, int y) {
	this.x=x;
	this.y=y;
	pacCollision=new Rectangle (x,y,width, height);
}
public void draw(Graphics g) {
	g.drawImage(GamePanel.pacboiImg, x, y, width, height, null);
	pacCollision.setBounds(x,y, width, height);
}
public void update() {
	
}
}

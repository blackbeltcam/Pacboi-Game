import java.awt.Rectangle;

public class GameObject {
	Rectangle collisionBox;
	int x, y, w, h;
	
public GameObject(int x, int y, int w, int h) {
	this.x=x;
	this.y=y;
	this.w=w;
	this.h=h;
	collisionBox=new Rectangle(x, y, w, h);
}
}

import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
	PacboiObject pacboiobj;
	GamePanel gp;
	//GhostObject ghostobj;
	ArrayList<MazeObject> mazeList;
	ArrayList<GhostObject> ghostList;

	public ObjectManager(PacboiObject pacboiobj, ArrayList<GhostObject> ghostList, GamePanel gp) {
		mazeList = new ArrayList<MazeObject>();
		this.pacboiobj=pacboiobj;
		this.ghostList=ghostList;
		this.gp=gp;
	}

	public void draw(Graphics g) {
		for (MazeObject m : mazeList) {

			m.draw(g);
		}
		for (GhostObject gh : ghostList) {
			gh.draw(g);
			moveGhost();
		}
		pacboiobj.draw(g);
		gp.move();
	}
public void moveGhost() {
	for (GhostObject gh : ghostList) {
		gh.y+=0.01;
	}
}
	public void addMazeObject(MazeObject m) {
		mazeList.add(m);
	}
	

}

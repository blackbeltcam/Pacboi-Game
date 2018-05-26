import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
	PacboiObject pacboiobj;
	GamePanel gp;
	//GhostObject ghostobj;
	ArrayList<MazeObject> mazeList;
	ArrayList<GhostObject> ghostList;
	double direction=0.3;

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
		if (gh.y>Pacboi.height || gh.y<0) {
			direction=-direction;
		}
		gh.y+=direction;
	}
}
	public void addMazeObject(MazeObject m) {
		mazeList.add(m);
	}
	public void update() {
for (MazeObject m : mazeList) {
			
			m.update();
		}
		for (GhostObject gh : ghostList) {
			gh.update();
		}
		pacboiobj.update();
	}

}

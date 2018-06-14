import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
	PacboiObject pacboiobj;
	GamePanel gp;
	// GhostObject ghostobj;
	ArrayList<MazeObject> mazeList;
	ArrayList<GhostObject> ghostList;
	double direction = 1;

	public ObjectManager(PacboiObject pacboiobj, ArrayList<GhostObject> ghostList) {
		mazeList = new ArrayList<MazeObject>();
		this.pacboiobj = pacboiobj;
		this.ghostList = ghostList;

	}

	public void draw(Graphics g) {
		for (MazeObject m : mazeList) {
			m.draw(g);
			if (m.block == GamePanel.watermark) {
				if (pacboiobj.pacCollision.intersects(m.mazeCollision)) {
					System.out.println("HELP ME IM TRAPED");
				}
			}
		}
		for (GhostObject gh : ghostList) {
			gh.draw(g);
			moveGhost(gh);
		}
		pacboiobj.draw(g);
	}

	public void moveGhost(GhostObject gh) {

		if (gh.y > Pacboi.height || gh.y < 0) {
			direction = -direction;
		}
		gh.y += direction;

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

	public void checkCollision() {

	}
}

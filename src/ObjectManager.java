import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ObjectManager {
	PacboiObject pacboiobj;
	GamePanel gp;
	// GhostObject ghostobj;
	ArrayList<MazeObject> mazeList;
	ArrayList<GhostObject> ghostList;
	
	private static Integer deathCounter = 0;

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
			

		
		for (GhostObject gh : ghostList) {
			gh.draw(g);
			moveGhost(gh);
		}
		pacboiobj.draw(g);
		g.setColor(Color.WHITE);

		g.drawString("Deaths", 2, 20);
		g.drawString(deathCounter.toString(), 20, 35);
		}
	}

	public void incrementDeath() {
		deathCounter++;
	}

	public void moveGhost(GhostObject gh) {

		if (gh.y > Pacboi.height || gh.y < 0) {
			gh.direction = -gh.direction;
		}
		gh.y += gh.direction;

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

	public boolean checkMazeCollision(Rectangle P) {
		for (MazeObject m : mazeList) {
			if (m.block == GamePanel.fill) {
				if (P.intersects(m.mazeCollision)) {
					return true;
				}
			}
		}
		return false;
	}
}

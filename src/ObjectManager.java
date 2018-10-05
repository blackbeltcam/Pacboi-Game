import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class ObjectManager {
	PacboiObject pacboiobj;
	GamePanel gp;

	Rectangle respawn;
	int fps;
	// GhostObject ghostobj;
	ArrayList<MazeObject> mazeList;
	ArrayList<GhostObject> ghostList;

	public ObjectManager(PacboiObject pacboiobj, ArrayList<GhostObject> ghostList, int fps) {
		mazeList = new ArrayList<MazeObject>();
		this.pacboiobj = pacboiobj;
		this.ghostList = ghostList;
		this.fps = fps;

	}

	public void draw(Graphics g) {
		for (MazeObject m : mazeList) {
			m.draw(g);
		}
		for (GhostObject gh : ghostList) {
			gh.draw(g);

		}
		pacboiobj.draw(g);

	}

	public void addMazeObject(MazeObject m) {
		mazeList.add(m);
	}

	public void update() {

		for (GhostObject gh : ghostList) {
			Rectangle ghostBox = new Rectangle(gh.x, gh.y + gh.direction, MazeObject.blockWidth,
					MazeObject.blockHeight);

			if (checkMazeCollision(ghostBox)) {
				gh.direction = -gh.direction;
			}
			gh.update();
		}

		pacboiobj.update();
	}

	public boolean checkGhostCollision(Rectangle F) {
		for (GhostObject g : ghostList) {
			if (g.ghostCollision.intersects(F)) {
				return true;
			}
		}
		return false;
	}

	public boolean checkKeyCollision(Rectangle K) {
		for (MazeObject m : mazeList) {
			if (m.block == GamePanel.key) {
				if (K.intersects(m.keyCollision)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkMazeCollision(Rectangle P) {
		for (MazeObject m : mazeList) {
			// if (m.block == GamePanel.fill && MazeObject.keyCollide)
			if (m.block == GamePanel.fill || m.block == GamePanel.scoreboard) {
				if (P.intersects(m.mazeCollision)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkLockCollision(Rectangle G) {
		for (MazeObject m : mazeList) {
			if (m.block == GamePanel.locked) {
				if (G.intersects(m.lockCollision)) {
					return true;
				}

			}
		}
		return false;
	}
	
	public boolean checkEndCollision(Rectangle C) {
		for (MazeObject m : mazeList) {
			if (m.block == GamePanel.ending) {
				if (C.intersects(m.endCollision)) {
					System.out.println("working");
					return true;
				}
			}
		}
		return false;
	}
}
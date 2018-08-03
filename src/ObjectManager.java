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
		this.fps=fps;
		

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
	
	public boolean checkMazeCollision(Rectangle P) {
		for (MazeObject m : mazeList) {
			if (m.block == GamePanel.fill || m.block == GamePanel.scoreboard) {
				if (P.intersects(m.mazeCollision)) {
					return true;
				}
			}
		}
		return false;
	}
}

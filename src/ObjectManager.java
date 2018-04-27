import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
	PacboiObject pacboiobj;
	ArrayList<MazeObject> mazeList;

	public ObjectManager(PacboiObject pacboiobj) {
		mazeList = new ArrayList<MazeObject>();
		this.pacboiobj=pacboiobj;
	}

	public void draw(Graphics g) {
		for (MazeObject m : mazeList) {

			m.draw(g);
		}
		pacboiobj.draw(g);
	}

	public void addMazeObject(MazeObject m) {
		mazeList.add(m);
	}
	

}

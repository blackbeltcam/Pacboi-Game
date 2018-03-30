import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
	ArrayList<Maze> mazeList;

	public ObjectManager() {
		mazeList = new ArrayList<Maze>();
	}

	public void draw(Graphics g) {
		for (Maze m : mazeList) {

			m.draw(g);
		}
	}

	public void addMazeObject(Maze m) {
		mazeList.add(m);
	}

}

import java.io.IOException;
import javax.swing.JFrame;


public class Pacboi {
	static final int width = 690;
	static final int height = 782;
	

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame();
		GamePanel gp = new GamePanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(gp);
		frame.add(gp);
		frame.setVisible(true);
		frame.setSize(width, height+22);
		frame.setResizable(false);

	}
}

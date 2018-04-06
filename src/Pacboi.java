import java.io.IOException;

import javax.swing.JFrame;

public class Pacboi {
	static final int width = 690;
	static final int height = 690;

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame();
		frame.add(new GamePanel());
		frame.setVisible(true);
		frame.setSize(width, height+22);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}

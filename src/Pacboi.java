import javax.swing.JFrame;


public class Pacboi {
	static final int width=700;
	static final int height=700;
	
public static void main(String[] args) {
	JFrame frame = new JFrame();
	frame.add(new GamePanel());
	frame.setVisible(true);
	frame.setSize(width, height);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
}
}

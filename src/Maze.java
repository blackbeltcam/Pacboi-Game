import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Maze {
	int currentScene=1;
	int height = 700;
	int x = 300;
	int y = 400;
	Font titleFont;
	Font subFont;

	public Maze(int x, int y) {
		
		this.x = x;
		
		this.y = y;
		titleFont=new Font("Arial", Font.BOLD, 48);
		subFont=new Font("Arial", Font.PLAIN, 20);
	}
	public void drawMenuState(Graphics g) {
		g.setColor(Color.PINK);
		g.fillRect(0, 0, 600, 600);
		g.drawString("Welcome To Pacboi", 200, 300);
	}
	public void drawGameState(Graphics g) {
		//g.setColor(Color.GREEN);
		//g.fillRect(x, y, Pacboi.width/GamePanel.numCols, Pacboi.height/GamePanel.numRows);
		g.setColor(Color.PINK);
		g.fillRect(0, 0, Pacboi.height, Pacboi.width);
		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("Welcome To Pacboi!", 90, 100);
		g.setFont(subFont);
		g.drawString("a frusterating game for people who have a lot of time", 90, 500);
	
	}
}
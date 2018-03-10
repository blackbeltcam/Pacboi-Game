import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Maze {
	int numRows=20;
	int numCols=20;
	int width=700;
	int height=700;
	
	


public Maze() {
	int variable = width/numCols;
	int variable2 = height/numRows;
JFrame frame = new JFrame();
	frame.setVisible(true);
	frame.setSize(width, height);
JPanel[][] mazePanels = new JPanel[20][20];
for (int i=0; i<numRows; i+=1){
	for(int j=0; j<numCols;i+=1) {
		mazePanels[i][j]=new JPanel();
		mazePanels[i][j]=new JPanel();
		mazePanels[i][j].setOpaque(true);
		mazePanels[i][j].setBackground(Color.GREEN);
		frame.add(mazePanels[i][j]);
		
	}
}
}



}
package bagooni;

import javax.swing.JFrame;

public class main extends JFrame {
	
	public static void main(String[] args) {
		Main_Frame_Set mainframeset = new Main_Frame_Set();
		mainframeset.numbergame = new NumberGame(mainframeset);
		mainframeset.movegame = new MoveGame(mainframeset);
		mainframeset.numbergame.start();
	}
}

class Main_Frame_Set extends JFrame{
	public NumberGame numbergame = null;
	public MoveGame movegame = null;
	int phase =1;
	
}
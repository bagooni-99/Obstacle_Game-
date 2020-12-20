package bagooni;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MoveGame extends JFrame {
	Main_Frame_Set mainframeset;
	JLabel title1 = new JLabel();
	JLabel time = new JLabel();
	JLabel time_right = new JLabel();
	JButton nextstep = new JButton("NEXT STEP");
	JButton reset = new JButton("RESET");
	MyPanel mypanel;
	int stage[] = {50,80, 100,120,150};
    public boolean time_run = false;
    public boolean flag = false;
    
    int stage_phase1;
    Container c;
    Vector<Point> obstacleVector = new Vector<Point>();
	Vector<Point> playerVector = new Vector<Point>();

	
    class MyActionNextStepListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!mypanel.game_status && mainframeset.phase!=5 ) {}
			else {
			mainframeset.numbergame.start_time=0;
			mainframeset.numbergame.time.setText("00:00:00");
			mainframeset.numbergame.gamepanel.CountDown(0);
			mainframeset.numbergame.time_run=false;
			mainframeset.numbergame.gamepanel.rect_select.clear();
			mainframeset.numbergame.gamepanel.gameStart(false);
			mainframeset.numbergame.gamepanel.check=0;
			
			mainframeset.numbergame.title.setText("숫자 맞추기 : "+ ++mainframeset.phase +"단계");
			
			mainframeset.movegame.mypanel.game_status=false;
			mainframeset.movegame.mypanel.game_time=false;
			playerVector.get(0).x = 10;
			playerVector.get(0).y = 720;
			mainframeset.movegame.mypanel.threadcheck=false;
			mainframeset.movegame.mypanel.time =null;
			
			obstacleVector.clear();
			for (int i = 0; i < stage[mainframeset.phase-1]; i++) {
				int x = (int) ((Math.random() * 1500));
				int y = (int) ((Math.random() * 800));
				obstacleVector.add(new Point(x, y));
			}
			mypanel.repaint(); 
			
			mainframeset.numbergame.start();
			mainframeset.movegame.dispose();
			setVisible(false);
			}
		}
    }
    
    class MyActionResetListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			mainframeset.numbergame.start_time=0;
			mainframeset.numbergame.time.setText("00:00:00");
			mainframeset.numbergame.gamepanel.CountDown(0);
			mainframeset.numbergame.time_run=false;
			mainframeset.numbergame.gamepanel.rect_select.clear();
			mainframeset.numbergame.gamepanel.gameStart(false);
			mainframeset.numbergame.gamepanel.check=0;
			
			mainframeset.numbergame.title.setText("숫자 맞추기 : "+"1단계");
			
			mainframeset.movegame.mypanel.game_status=false;
			mainframeset.movegame.mypanel.game_time=false;
			playerVector.get(0).x = 10;
			playerVector.get(0).y = 720;
			mainframeset.movegame.mypanel.threadcheck=false;
			mainframeset.movegame.mypanel.time = null;
			
			obstacleVector.clear();
			for (int i = 0; i < stage[0]; i++) {
				int x = (int) ((Math.random() * 1500));
				int y = (int) ((Math.random() * 800));
				obstacleVector.add(new Point(x, y));
			}
			mypanel.repaint(); 
			mainframeset.numbergame.start();
			mainframeset.movegame.dispose();
			setVisible(false);
		}
    }
    
	public MoveGame(Main_Frame_Set mainframeset) {
		this.mainframeset = mainframeset;
		
		Container c = getContentPane();
		c.setLayout(null);
		
		for (int i = 0; i < stage[mainframeset.phase-1]; i++) {
			int x = (int) ((Math.random() * 1500));
			int y = (int) ((Math.random() * 800));
			obstacleVector.add(new Point(x, y));
		}
		
		playerVector.add(new Point(10, 720)); 
		
		mypanel = new MyPanel(mainframeset, obstacleVector, playerVector);
		mypanel.setBounds(50, 100, 1500, 750);
		mypanel.setBackground(Color.DARK_GRAY);
		c.add(mypanel);
		//게임 범위
		
		nextstep.setFont(new Font("Default", Font.BOLD, 20));
		nextstep.setSize(150,30);
		nextstep.setLocation(1410, 875);
		nextstep.addActionListener(new MyActionNextStepListener());
		c.add(nextstep);
		// next step버튼  
		
		reset.setFont(new Font("Default", Font.BOLD, 20));
		reset.setSize(150,30);
		reset.setLocation(1200, 875);
		reset.addActionListener(new MyActionResetListener());
		c.add(reset);
		
		
		time_right.setText("00:00:00");
		time_right.setFont(new Font("Default", Font.BOLD, 20));
		time_right.setSize(300,30);
		time_right.setLocation(1460, 70);
		c.add(time_right);
		
		title1.setText("장애물 피하기");
		title1.setFont(new Font("Default", Font.BOLD, 20));
		title1.setSize(300,30);
		title1.setLocation(760, 25);
		c.add(title1);
		//타이틀 
		
		playerThread player = new playerThread(mypanel, playerVector);
		player.start();

	}
	
	public void start() {
		setTitle("장애물 피하기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1600, 1000);
		setVisible(true);
	}

}

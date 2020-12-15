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


public class NumberGame extends JFrame {
	Main_Frame_Set mainframeset;
	
	MoveGame movegame;
	JLabel title = new JLabel();
	JLabel time = new JLabel();
	JLabel time_right = new JLabel();
	JButton start = new JButton("START");
	JButton next = new JButton("NEXT");
	JButton nextstep = new JButton("NEXT STEP");
	GamePanel gamepanel;
	
	int n;
	public SimpleDateFormat time_format; //시간 값을 변환하기 위한 포맷
	public String show_time;//진행 시간 값을 받아들일 문자열
	public long start_time, current_time, actual_time; //게임 시작시간, 컴퓨터 시간, 실제 게임 진행 시간
    public boolean time_run = false;
	public boolean flag = false;

    
    class MyActionStartListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (!time_run && !gamepanel.game_status) {
				start_time = System.currentTimeMillis();//시작버튼눌렀을시시간값받기
				gamepanel.rect_select.clear(); //1-25 숫자 보관용 벡터
				time_run = true;
				gamepanel.gameStart(true);
		}
		}
    }
	
    class MyActionNextListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			mainframeset.movegame.mypanel.time = gamepanel.time.substring(gamepanel.time.length()-2,gamepanel.time.length());
			mainframeset.movegame.mypanel.setFocusable(true);
			mainframeset.movegame.mypanel.requestFocus();
			
			n = 60 - Integer.parseInt(mainframeset.movegame.mypanel.time);
			TimerThread_second timer_second = new TimerThread_second(mainframeset.movegame.mypanel,mainframeset.movegame.time_right, n);
			timer_second.start();
			obstacleThread obstacle = new obstacleThread(mainframeset.movegame, mainframeset.movegame.obstacleVector, mainframeset.movegame.playerVector);
			obstacle.start();
			ballThread ball = new ballThread(mainframeset.movegame.mypanel, mainframeset, mainframeset.movegame.obstacleVector, mainframeset.movegame.playerVector);
			ball.start();
			mainframeset.movegame.start();

			mainframeset.numbergame.setVisible(false);

		}
    }
    
	public NumberGame(Main_Frame_Set mainframeset) {
		this.mainframeset = mainframeset;
		
		Container c = getContentPane();
		c.setLayout(null);
		
		start.setFont(new Font("Default", Font.BOLD, 20));
		start.setSize(100,30);
		start.setLocation(50, 520);
		start.addActionListener(new MyActionStartListener());
		c.add(start);
		// start버튼  
		
		next.setFont(new Font("Default", Font.BOLD, 20));
		next.setSize(100,30);
		next.setLocation(350, 520);
		next.addActionListener(new MyActionNextListener());
		c.add(next);
		// next 버튼  
			
		gamepanel = new GamePanel();
		gamepanel.setBounds(50, 100, 400, 400);
		c.add(gamepanel);
		//바둑판 
		
		time_format = new SimpleDateFormat("HH:mm:ss");
		//시간포맷설정
		title.setText("숫자 맞추기 : "+"1단계");
		title.setFont(new Font("Default", Font.BOLD, 20));
		title.setSize(300,30);
		title.setLocation(175, 25);
		c.add(title);
		//타이틀 
		
		time.setText("00:00:00");
		time.setFont(new Font("Default", Font.BOLD, 20));
		time.setSize(150,30);
		time.setLocation(370, 70);
		c.add(time);
		//시간 
		
		TimerThread timer = new TimerThread(this);
		timer.start();
	}
	
	public void start() {
		setTitle("숫자 맞추기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setVisible(true);
	}

}

package bagooni;

import java.awt.Container;
import java.awt.Point;
import java.util.Vector;

import javax.swing.JLabel;

class TimerThread extends Thread {
	private NumberGame numbergame;

	public TimerThread(NumberGame numbergame) {
		this.numbergame = numbergame;

	}

	public void TimeCheck() {
		if (numbergame.time_run) {
			numbergame.current_time = System.currentTimeMillis();
			numbergame.actual_time = numbergame.current_time - numbergame.start_time;// 게임시작버튼을눌렀을때의시간값과 실제시간값으로게임진행시간계산.
			numbergame.gamepanel.CountDown((int) numbergame.actual_time / 1000); // 카운트다운표시용시간값전송

			if (!numbergame.gamepanel.game_status && numbergame.gamepanel.check <= 25) { // 게임세팅완료후게임시작되면게임진행시간갱신
				numbergame.show_time = numbergame.time_format.format(numbergame.actual_time - 32403000);
				numbergame.time.setText(numbergame.show_time);
			}
		}

		if (numbergame.gamepanel.check > 25) {
			numbergame.gamepanel.ClearTime(numbergame.time.getText());// 숫자25까지클릭이끝나면게임클리어메세지띄울준비
		}
	}

	public void run() {
		while (true) {
			try {
				numbergame.repaint();
				TimeCheck();
				Thread.sleep(15);
			} catch (Exception e) {
			}
		}
	}
}

class TimerThread_second extends Thread {
	private JLabel time_right;
	private MyPanel panel;
	private int n;
	public TimerThread_second(MyPanel panel, JLabel time_right,int n) {
		this.time_right = time_right;
		this.panel = panel;
		this.n = n;
	}

	public void run() {

		while (true) {
			try {
				if(panel.threadcheck) { 
					time_right.setText("00:00:00");
					return;
					}
				else {
					if (n < 10) {
						time_right.setText("00:00:0" + Integer.toString(n));
						n--;
					} 
					else {
						time_right.setText("00:00:" + Integer.toString(n));
						n--;
					}
				}
				sleep(1000);
			} catch (Exception e) {
				return;
			}
			if (n < 0) {
				panel.game_time = true;
				panel.threadcheck = true;
				return;
			}
		}
	}
}

class obstacleThread extends Thread {
	MoveGame movegame;
	Vector<Point> obstacleVector;
	Vector<Point> playerVector;

	public obstacleThread(MoveGame movegame, Vector<Point> obstacleVector, Vector<Point> playerVector) {
		this.movegame = movegame;
		this.obstacleVector = obstacleVector;
		this.playerVector = playerVector;
	}

	public void changeObstaclePosition() {
		for (int i = 0; i < obstacleVector.size(); i++) {
			obstacleVector.get(i).y += 5;
			if (obstacleVector.get(i).x < 40) {
				if (obstacleVector.get(i).y > 680)
					obstacleVector.get(i).y = 0;
			} else {
				if (obstacleVector.get(i).y >= 750)
					obstacleVector.get(i).y = 0;
			}
		}
	}

	public void run() {
		while (true) {
			try {
				if(movegame.mypanel.threadcheck) {
					continue;
				}
				else
					changeObstaclePosition();
				sleep(100);
			} catch (InterruptedException e) {
				return;
			}
			movegame.mypanel.repaint();
		}
	}
}

class playerThread extends Thread {
	MyPanel panel;
	Vector<Point> playerVector;
	String way;

	public playerThread(MyPanel panel, Vector<Point> playerVector) {
		this.panel = panel;
		this.playerVector = playerVector;
	}

	public void changePlayerPosition() {
		way = panel.get_way();

		if (way.equals("down")) {
			if (playerVector.get(0).y >= 740) {
				playerVector.get(0).y = 740;
			}
		}

		else if (way.equals("left")) {
			if (playerVector.get(0).x <= 0) {
				playerVector.get(0).x = 0;
			}
		}

		else if (way.equals("right")) {
			if (playerVector.get(0).x >= 1490) {
				playerVector.get(0).x = 1490;
			}
		}

		else if (way.equals("up")) {
			if (playerVector.get(0).y <= 0) {
				playerVector.get(0).y = 0;

			}
		}
	}

	public void run() {
		while (true) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
				System.out.println("error");
			}
			changePlayerPosition();
			panel.repaint();
		}
	}
}

class ballThread extends Thread {
	Vector<Point> obstacleVector;
	Vector<Point> playerVector;
	MyPanel panel;
	Main_Frame_Set mainframeset;

	public ballThread(MyPanel panel,Main_Frame_Set mainframeset, Vector<Point> obstacleVector, Vector<Point> playerVector) {
		this.obstacleVector = obstacleVector;
		this.playerVector = playerVector;
		this.panel = panel;
		this.mainframeset = mainframeset;
	}

	public void run() {
		while (true) {
			try {
				if (playerVector.get(0).x >= 1460 && playerVector.get(0).y <= 30) {
					panel.game_status = true;
					panel.threadcheck = true;
					
				}
				else {
				for (int i = 0; i < obstacleVector.size(); i++) {
					if (playerVector.get(0).y-5 <= obstacleVector.get(i).y+5
						&& playerVector.get(0).y+5 >= obstacleVector.get(i).y-5
						&& playerVector.get(0).x-5 <= obstacleVector.get(i).x+5
						&& playerVector.get(0).x+5 >= obstacleVector.get(i).x-5){
							panel.game_time = true;
							panel.threadcheck = true;
					}
				}
				}
				sleep(100);
			} catch (InterruptedException e) {
				System.out.println("error");
			}
		}
	}
}

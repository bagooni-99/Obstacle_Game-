package bagooni;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements MouseListener {
	int count = 3; // 카운트다운
	int check; // 숫자체크
	int x, y;
	public String time;// 클리어시간값표시용
	boolean game_status = false; 
	Random random = new Random(); // 랜덤함수
	Vector rect_select = new Vector();// 1-25 숫자보관용벡터
	SelectRect sr; // 숫자보관용객체클래스접근키

	public GamePanel() {
		addMouseListener(this);
	}
	
	public void ClearTime(String time) {
		this.time = time;
	}
	
	public void CountDown(int count) {
		switch (count) {
		case 0:
			this.count = 3;
			break;

		case 1:
			this.count = 2;
			break;

		case 2:
			this.count = 1;
			break;

		case 3:
			this.count = 0;
			game_status = false;
			break;
		}
	}

	public void gameStart(boolean game_status) {// 게임기본세팅 25개의사각박스와해당박스안에 랜덤으로난수를발생시켜나온숫자를받아입력한다.
		this.game_status = game_status;

		if (game_status) {
			check = 1;

			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					int n = 0;
					int x_box = i * 80;
					int y_box = j * 80; // 25개사각형좌표값들

					n = random.nextInt(25) + 1; // 1~25 난수

					for (int k = 0; k < rect_select.size(); k++) {
						sr = (SelectRect) rect_select.get(k);
						if (sr.number == n) {
							n = random.nextInt(25) + 1;
							k = -1;
						}
					} // 중복없는난수발생
					sr = new SelectRect(x_box, y_box, n);
					rect_select.add(sr);// 받은좌표및난수값을객체화시켜벡터로저장
				}
			}
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 400, 400);

		if (game_status) {// 카운트다운텍스트그리기
			g.setColor(Color.WHITE);
			g.setFont(new Font("Default", Font.BOLD, 100));
			g.drawString("" + count, 170, 250);
		}

		else if (!game_status && count == 0) {
			for (int i = 0; i < rect_select.size(); i++) {
				sr = (SelectRect) rect_select.get(i);
				g.setColor(Color.white);
				g.drawRect(sr.x, sr.y, 80, 80);
				g.setFont(new Font("Default", Font.BOLD, 30));
				g.drawString("" + sr.number, sr.x + 30, sr.y + 50); // 벡터에저장된각각의숫자값을받아사각형과숫자그리기
			}
			g.setColor(Color.red);
			g.drawRect(x * 80 , y * 80 , 80, 80);// 마우스로선택된사각박스를붉게표시
		}
		
		if (check > 25) {
			g.setColor(Color.blue);
			g.setFont(new Font("Default", Font.BOLD, 50));
			g.drawString("GAME CLEAR!", 25, 150);
			g.setColor(Color.red);
			g.setFont(new Font("Default", Font.BOLD, 40));
			g.drawString("" + time, 115, 250); //게임이클리어되면클리어화면표시

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		x = e.getX() / 80;
		y = e.getY() / 80;
		
		if (count == 0) {
			for (int i = 0; i < rect_select.size(); i++) {
				sr = (SelectRect) rect_select.get(i);
				
				if (x == sr.x / 80 && y == sr.y / 80) {
					if (sr.number - check == 0) {
						check++;
						rect_select.remove(i);//1-252 숫자순서대로클릭되면해당하는숫자제거
					}
				}
			}
		}	
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}

}

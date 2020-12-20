package bagooni;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MyPanel extends JPanel {
	ImageIcon background = new ImageIcon("images/space.jpg");
	ImageIcon star = new ImageIcon("images/star.png");
	ImageIcon player = new ImageIcon("images/player1.png");
	ImageIcon start = new ImageIcon("images/start.png");
	ImageIcon finish = new ImageIcon("images/finish.png");
	Image img = background.getImage();
	Image img_star = star.getImage();
	Image img_player = player.getImage();
	Image img_start = start.getImage();
	Image img_finish = finish.getImage();
	
	Vector<Point> obstacleVector;
	Vector<Point> playerVector;
	String time;
	String way = "left";
	Main_Frame_Set mainframeset;
	
	boolean game_status = false;//도착확인  
	boolean game_time = false; //시간 확인  
	boolean threadcheck = false; //시간 확인 
	
	public MyPanel(Main_Frame_Set mainframeset, Vector<Point> obstacleVector, Vector<Point> playerVector) {
		this.obstacleVector = obstacleVector;
		this.playerVector = playerVector;
		this.mainframeset = mainframeset;
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_UP) {
					way = "up";
					playerVector.get(0).y -= 10;
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					way = "left";
					playerVector.get(0).x -= 10;
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					way = "right";
					playerVector.get(0).x += 10;
				}
				else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					way = "down";
					playerVector.get(0).y += 10;
				}
				repaint();
			}
		});
		
	}
	
	public String get_way() {
		return way;
	}
	
	
	public void reset() {
		this.removeAll();
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, 1500, 750, this);
		
		g.setColor(Color.RED);
		g.drawImage(img_finish, 1460, 0, 40, 40, this);
		
		g.setColor(Color.RED);
		g.drawImage(img_start, 0, 710, 40, 40, this); //start
		
		Point A = playerVector.get(0);
		g.setColor(Color.GREEN);
		g.drawImage(img_player, A.x, A.y, 30, 30, this);
		
		g.setColor(Color.yellow);
		for (int i = 0; i < obstacleVector.size(); i++) {
			Point p = obstacleVector.get(i);
			g.drawImage(img_star, p.x, p.y, 10, 10, this);
		}
		
		if(game_status && mainframeset.phase != 5) {
			g.setColor(Color.blue);
			g.setFont(new Font("Default", Font.BOLD, 100));
			g.drawString("GAME CLEAR!", 450 ,400);
			setFocusable(false);
			mainframeset.numbergame.clip.stop();
			mainframeset.numbergame.clip_clear.start();
			requestFocus();
		}
		
		if(game_status && mainframeset.phase == 5) {
			g.setColor(Color.blue);
			g.setFont(new Font("Default", Font.BOLD, 100));
			g.drawString("ALL CLEAR!", 450 ,400);
			setFocusable(false);
			mainframeset.numbergame.clip.stop();
			mainframeset.numbergame.clip_clear.start();
			requestFocus();		
		}
		
		if(threadcheck&&game_time) {
			g.setColor(Color.RED);
			g.setFont(new Font("Default", Font.BOLD, 100));
			g.drawString("GAME OVER!", 450 ,400);
			setFocusable(false);
			mainframeset.numbergame.clip.stop();
			mainframeset.numbergame.clip_fail.start();
			requestFocus();
		}
		
	}
}

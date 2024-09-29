package BrickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JPanel;

public class GameOop extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;
	private int lives = 3;
	private int brickAmount = 21;
	private Timer timer;
	private int delay = 8;
	private int stickPositionX = 310;
	private int ballPositionX = 350;
	private int ballPositionY = 528;
	private double  ballDirectionX = 1.2;
	private double ballDirectionY = -2.1;
	private int ballRadius = 15;

	private Bricks map;

	public GameOop() {
		map = new Bricks(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics graph) {

		graph.setColor(Color.pink); // background
		graph.fillRect(1, 1, 700, 600);

		map.draw((Graphics2D) graph); // bricks

		graph.setColor(Color.darkGray); // stick
		graph.fillRect(stickPositionX, 550, 100, 8);

		graph.setColor(Color.blue); // ball
		graph.fillOval(ballPositionX, ballPositionY, 20, 20);

		if (brickAmount <= 0) {
			play = false;
			ballDirectionX = 0;
			ballDirectionY = 0;
			graph.setColor(Color.red);
			graph.setFont(new Font("serif", Font.BOLD, 30));
			graph.drawString("YOU WON!!!", 260, 300);
			graph.setFont(new Font("serif", Font.BOLD, 20));
			graph.drawString("Press enter 2 restart", 260, 350);
			graph.dispose();
		}

		switch (lives) {
		case 1:
			graph.setColor(Color.magenta);
			graph.setFont(new Font("serif", Font.BOLD, 40));
			graph.drawString("*", 50, 40);
			break;
		case 2:
			graph.setColor(Color.magenta);
			graph.setFont(new Font("serif", Font.BOLD, 40));
			graph.drawString("* *", 50, 40);
			break;
		case 3:
			graph.setColor(Color.magenta);
			graph.setFont(new Font("serif", Font.BOLD, 40));
			graph.drawString("* * *", 50, 40);
			break;
		}

		graph.setColor(Color.black); // score
		graph.setFont(new Font("serif", Font.BOLD, 25));
		graph.drawString("" + score, 620, 30);
				
		if (ballPositionY > 570) {
			play = false;
			ballDirectionX = 0;
			ballDirectionY = 0;

			graph.setColor(Color.red);
			if (lives == 1 && score != 105) {
				graph.setFont(new Font("serif", Font.BOLD, 30));
				graph.drawString("Game Over, Score: " + score, 190, 300);
			} else if (lives == 2 && score != 105) {
				graph.setFont(new Font("serif", Font.BOLD, 30));
				graph.drawString("This is your last chance", 170, 300);
			} else if (lives == 3 && score != 105) {
				graph.setFont(new Font("serif", Font.BOLD, 30));
				graph.drawString("You have 2 lives left", 190, 300);
			} 			
			graph.setFont(new Font("serif", Font.BOLD, 20));
			graph.drawString("Press enter 2 restart", 230, 350);
		}
	
		graph.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		timer.start();

		if (play) {
			if (new Rectangle(ballPositionX, ballPositionY, 20, 20)
					.intersects(new Rectangle(stickPositionX, 550, 100, 8))) {
				ballDirectionY = -ballDirectionY;
			}
			for (int i = 0; i < map.map.length; i++) {
			    for (int j = 0; j < map.map[i].length; j++) {   // map[i] önemli
			        if (map.map[i][j] > 0) {
			            int brickX = j * (map.brickWidth + 7) + 50;      // 7 brickler arasındaki boşluk
			            int brickY = i * (map.brickHeight + 7) + 40;

			            // Check if ball intersects with brick
			            if (ballPositionX + ballRadius > brickX &&
			                ballPositionX - ballRadius < brickX + map.brickWidth &&
			                ballPositionY + ballRadius > brickY &&
			                ballPositionY - ballRadius < brickY + map.brickHeight) {
			                
			                // Collision detected, change ball direction and remove brick
			                ballDirectionY = -ballDirectionY;
			                map.setBrickValue(0, i, j);
			                brickAmount--;
			                score += 5;
			            }
			        }
			    }
			}

			ballPositionX += ballDirectionX;
			ballPositionY += ballDirectionY;
			if (ballPositionX < 0) {
				ballDirectionX = -ballDirectionX;
			}
			if (ballPositionY < 0) {
				ballDirectionY = -ballDirectionY;
			}
			if (ballPositionX > 670) {
				ballDirectionX = -ballDirectionX;
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (stickPositionX >= 600) {
				stickPositionX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (stickPositionX < 10) {
				stickPositionX = 10;
			} else {
				moveLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) { // yandığın zaman
			
			if (brickAmount <= 0) {
				play = true;
				stickPositionX = 310;
				ballPositionX = 350;
				ballPositionY = 528;
				ballDirectionX = 1;
				ballDirectionY = -2;
				map = new Bricks(3, 7);
				score = 0;
				brickAmount = 21;
				repaint();
				lives = 3;
			}
			
			if (ballPositionY > 570) {
				play = true;
				stickPositionX = 310;
				ballPositionX = 350;
				ballPositionY = 528;
				ballDirectionX = 1;
				ballDirectionY = -2;
				lives -= 1;
				if (lives == 0) {
					map = new Bricks(3, 7);
					score = 0;
					brickAmount = 21;
					repaint();
					lives = 3;
				}
			}
		}
	}

	public void moveRight() {
		play = true;
		stickPositionX += 20;
	}

	public void moveLeft() {
		play = true;
		stickPositionX -= 20;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}		
}
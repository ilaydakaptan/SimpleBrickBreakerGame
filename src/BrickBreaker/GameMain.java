package BrickBreaker;

import javax.swing.JFrame;

public class GameMain {

	public static void main(String[] args) {

		JFrame obj = new JFrame();
		GameOop game = new GameOop();
		obj.setBounds(300,100,700,600);
		obj.setTitle("Layda's First Project");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		obj.add(game);
		
	}

}
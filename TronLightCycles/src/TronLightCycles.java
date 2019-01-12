//TronLightCyles.java
//Lucille Xiong
//Main class for Tron Lightcyles

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TronLightCycles extends JFrame implements ActionListener{
	//Players go through GameMenu, GamePanel, and then GameEnd.
		private GameMenu menu;
		private GamePanel game;
		private GameEnd end;
		//Score for both players. First player to reach three wins.
		private int p1score = 0, p2score = 0;
		Timer myTimer;
		
		public TronLightCycles(){
			super("Tron LightCycles");
			setSize(640,600);
			
			//Creates the menu
			menu = new GameMenu(this);
			
			myTimer = new Timer(10, this);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Makes the window not resizable
			setResizable(false);
		}
		
		public void start(){
			myTimer.start();
		}
		
		//Starts a new GamePanel
		public void startGame(){
			this.getContentPane().removeAll();
			this.game = new GamePanel(this);
			add(game);
			setVisible(true);
		}
		
		public void actionPerformed(ActionEvent evt){
			if (game!=null && game.ready){
				game.update();
				game.repaint();
			}
		}
		
		//Directs the players the endFrame and resets
		public void endFrame(){
			this.getContentPane().removeAll();
			game.ready = false; //Stops a new GamePanel from starting
			end = new GameEnd(this, p1score, p2score);
		}
		
		//Add the player's score
		public void player1Win(){
			p1score++;
		}
		public void player2Win(){
			p2score++;
		}
		
		//Handles the scores of players
		public int getP1Score(){
			return p1score;
		}
		public int getP2Score(){
			return p2score;
		}
		public int getMaxScore(){
			return Math.max(p1score, p2score);
		}
		
		//Resets the scores for a new game
		public void resetScores(){
			p1score = 0;
			p2score = 0;
		}
		
		public static void main(String []args){
			//Create a SimpleGame object
			TronLightCycles g = new TronLightCycles();
		}
}

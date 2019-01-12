//GamePanel.java
//Lucille Xiong
//A new GamePanel is made for every round of the game.

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*;

class GamePanel extends JPanel implements KeyListener{
	
	private TronLightCycles mainFrame;
	private GameBike []bikes;
	
	//Direction set for the bikes
	private final static int UP = 0, LEFT = 1, RIGHT = 2, DOWN = 3;
	
	//Delay the start time to get user a time to prepare
	private int wait = 75;
	
	private boolean []keys;
	public boolean ready = false;
	
	//Images used in the game
	private BufferedImage []nums = new BufferedImage[8]; //Array of numbers
	private BufferedImage title = null, side1 = null, side2 = null, score1 = null, score2 = null, boost1 = null, boost2 = null;

	public GamePanel(TronLightCycles m){
		keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		loadImages();
		
		//Creates the two bikes for the players
		//The first bike starts at position (90,310) heading RIGHT
		//The second bike starts at position (550,310) heading LEFT
		//Both bikes start with a speed of 2
		bikes = new GameBike [] {new GameBike(this, RIGHT, 90, 310, 2, Color.red), new GameBike(this, LEFT, 550, 310, 2, Color.blue)};
		
		setSize(640,600);
		addKeyListener(this);
	}
	
	//Loads the Images used in the game
	public void loadImages(){
		try{
    		title = ImageIO.read(new File("Title.png"));
		} 
		catch(IOException e) {
		}
		try{
    		side1 = ImageIO.read(new File("Player1Side.png"));
		} 
		catch (IOException e) {
		}
		try{
    		side2 = ImageIO.read(new File("Player2Side.png"));
		} 
		catch(IOException e) {
		}
		try{
    		score1 = ImageIO.read(new File("Player1Score.png"));
		} 
		catch(IOException e) {
		}
		try{
    		score2 = ImageIO.read(new File("Player2Score.png"));
		} 
		catch(IOException e) {
		}
		try{
    		boost1 = ImageIO.read(new File("Player1Boost.png"));
		} 
		catch(IOException e) {
		}
		try{
    		boost2 = ImageIO.read(new File("Player2Boost.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[0] = ImageIO.read(new File("Player1Zero.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[4] = ImageIO.read(new File("Player2Zero.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[1] = ImageIO.read(new File("Player1One.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[5] = ImageIO.read(new File("Player2One.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[2] = ImageIO.read(new File("Player1Two.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[6] = ImageIO.read(new File("Player2Two.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[3] = ImageIO.read(new File("Player1Three.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[7] = ImageIO.read(new File("Player2Three.png"));
		} 
		catch(IOException e) {
		}
	}
	
	//Paint on the contents of the JPanel
	public void paintComponent(Graphics g){
		//Converts g into a Graphics2D object
		Graphics2D g2 = (Graphics2D) g;
		
		//Fills in the black background
		g2.setColor(Color.black);
		g2.fillRect(0,0,getWidth(),getHeight());
		
		//Draws the title along with the stats of both players
		g2.drawImage(title,0,0,this);
		//player 1
		g2.drawImage(side1,0,60,this);
		g2.drawImage(score1,0,250,this);
		g2.drawImage(boost1,0,410,this);
		g2.drawImage(nums[mainFrame.getP1Score()],35,330,this); //score
		g2.drawImage(nums[bikes[0].getBoost()],35,490,this); //boost
		//player 2
		g2.drawImage(side2,570,60,this);
		g2.drawImage(score2,570,250,this);
		g2.drawImage(boost2,570,410,this);
		g2.drawImage(nums[mainFrame.getP2Score()+4],605,330,this); //score
		g2.drawImage(nums[bikes[1].getBoost()+4],605,490,this); //boost
		
		//Draws the grid
		g2.setStroke(new BasicStroke(1));
		g2.setColor(Color.white);
		for (int x = 70; x<571; x+=20){
			g2.setColor(Color.white);
			g2.drawLine(x,70,x,570);
			g2.drawLine(70,x,570,x);
		}
		
		//Draw the paths of the bike
		g2.setStroke(new BasicStroke(4));
		for (int i=0; i<bikes.length; i++){
			
			GameBike bike = bikes[i];
			ArrayList<Line2D> path = bike.getPath();
			
			//Sets the color for the current bike
			g2.setColor(bike.getColor());
			
			//Draws the past lines of the bike
			for (int j=0; j<bike.getSize();j++){
				g2.draw(path.get(j));
			}
			
			//Draws the current line the bike is making
			g2.drawLine(bike.getPosX(),bike.getPosY(),bike.getLastX(),bike.getLastY());
		}
	}
	
	//Updates the GamePanel
	public void update(){
		//Starts the wait counter whne it reaches zero
		if (wait == 0){
			
			//Handles the input from players (direction changes and boost)
			move();
			
			//Booleans to see if the player has won the round
			boolean p1Win = false, p2Win = false;
			
			//Moves each bike
			//The speed of the bike determines the number of loops
			//Checks for collision between the bikes
			double speed1 = bikes[0].getSpeed(), speed2 = bikes[1].getSpeed();
			double loop = Math.min(speed1,speed2);
			for (int i=0;i<loop;i++){
				p1Win = p1Win || moveBike(0);
				p2Win = p2Win || moveBike(1);
			}
			//Moves the bikes if one speed is greater than the other
			for (int i=0; i<speed1-speed2; i++){
				p1Win = p1Win || moveBike(0);
			}
			for (int i=0; i<speed2-speed1; i++){
				p2Win = p2Win || moveBike(1);
			}
			
			//Updates teh scores of the player if one wins
			//No player gets a score when there is a tie
			if (p1Win && !p2Win){
				mainFrame.player1Win();
			}
			else if (p2Win && !p1Win){
				mainFrame.player2Win();
			}
			
			//If a player has reached the max score of three, proceed to the endFrame
			if (mainFrame.getMaxScore()==3){
				mainFrame.setVisible(false);
				mainFrame.endFrame();
			}
			
			//Restarts for a new round if a player has won
			else if (p1Win || p2Win){
				mainFrame.startGame();
			}
			
			//Slowly reduces the boost of a bike
			bikes[0].reduceBoost();
			bikes[1].reduceBoost();
		}
		
		//Decreases the wait counter
		else{
			wait--;
		}
	}
	
	public boolean moveBike(int player){
		//Moves the given bike
		bikes[player].move();
		
		//Checks for collisions that would let player win.
		if (bikes[1-player].selfCollision() || bikes[player].enemyCollision(bikes[1-player])){
			return true;
		}
		return false;
	}
	
	//Handles the input from the players controlling the bikes
	public void move(){
		
		//Player 1 : WASD
		//If bike had changed directions, loop is exited.
		if (keys[KeyEvent.VK_W] && bikes[0].moveDirection(UP)){}
		else if (keys[KeyEvent.VK_A] && bikes[0].moveDirection(LEFT)){}
		else if (keys[KeyEvent.VK_S] && bikes[0].moveDirection(DOWN)){}
		else if (keys[KeyEvent.VK_D] && bikes[0].moveDirection(RIGHT)){}
		
		//Player 2: Arrow Keys
		if (keys[KeyEvent.VK_UP] && bikes[1].moveDirection(UP)){}
		else if (keys[KeyEvent.VK_LEFT] && bikes[1].moveDirection(LEFT)){}
		else if (keys[KeyEvent.VK_DOWN] && bikes[1].moveDirection(DOWN)){}
		else if (keys[KeyEvent.VK_RIGHT] && bikes[1].moveDirection(RIGHT)){}
		
		//Boosts
		//Player 1:Q and Player2:ENTER
		if (keys[KeyEvent.VK_Q]){
			bikes[0].boost();
		}
		if (keys[KeyEvent.VK_ENTER]){
			bikes[1].boost();
		}
	}
	
	public void addNotify() {
        super.addNotify();
        ready = true;
        requestFocus();
        mainFrame.start();
    }
    
    //KeyListener
    public void keyPressed(KeyEvent e){
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e){
        keys[e.getKeyCode()] = false;
	}
    public void keyTyped(KeyEvent e) {}
}

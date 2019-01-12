//GameEnd.java
//Lucille Xiong
//Ending menu of the game

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 

class GameEnd extends JFrame implements MouseListener{
	private TronLightCycles mainFrame;
	//Scores of the player
	private int p1score, p2score, winner;
	//Images used for the end
	private BufferedImage background = null, p1Win = null, p2Win = null, dash = null; 
	private BufferedImage []nums = new BufferedImage[8]; //Array of numbers
	
	public GameEnd(TronLightCycles m, int p1score, int p2score){
		super("Tron LightCycles");
		mainFrame = m;
		addMouseListener(this);
		
		//Sets the player scores and then clears tehm
		this.p1score = p1score;
		this.p2score = p2score;
		mainFrame.resetScores();
		
		//Loads the images
		loadImages();
		
		//Determines the winner
		winner = p1score > p2score ? 1 : 2;
		
		setSize(640,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g){
		g.drawImage(background,0,0,this);
		//Draws the images for the winner
		if (winner == 1){
			g.drawImage(p1Win,0,180,this);
		}
		else{
			g.drawImage(p2Win,0,180,this);
		}
		g.drawImage(nums[p1score],240,260,this);
		g.drawImage(dash,295,260,this);
		g.drawImage(nums[p2score+4],350,260,this);
	}
	
	//Loads the images
	public void loadImages(){
		try {
    		background = ImageIO.read(new File("Background.png"));
		} 
		catch (IOException e) {
		}
		try {
    		p1Win = ImageIO.read(new File("Player1Win.png"));
		} 
		catch (IOException e) {
		}
		try {
    		p2Win = ImageIO.read(new File("Player2Win.png"));
		} 
		catch (IOException e) {
		}
		try {
    		dash = ImageIO.read(new File("Dash.png"));
		} 
		catch (IOException e) {
		}
		try{
    		nums[0] = ImageIO.read(new File("One0.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[4] = ImageIO.read(new File("Two0.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[1] = ImageIO.read(new File("One1.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[5] = ImageIO.read(new File("Two1.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[2] = ImageIO.read(new File("One2.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[6] = ImageIO.read(new File("Two2.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[3] = ImageIO.read(new File("One3.png"));
		} 
		catch(IOException e) {
		}
		try{
    		nums[7] = ImageIO.read(new File("Two3.png"));
		} 
		catch(IOException e) {
		}
	}
	
	//MouseListener
	public void mousePressed(MouseEvent e){
		//Restarts the game
		if (e.getX()<500 && e.getX()>135 && e.getY()<410 && e.getY()>340){
			this.setVisible(false);
	    	mainFrame.startGame();
		}
		//Exits the game
		if (e.getX()<400 && e.getX()>220 && e.getY()<500 && e.getY()>435){
			System.exit(0);
		}
	}
	public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}    
    public void mouseClicked(MouseEvent e){} 
}
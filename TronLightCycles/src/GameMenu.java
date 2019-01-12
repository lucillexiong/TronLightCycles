//GameMenu.java
//Lucille Xiong
//Starting menu of the game

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 

class GameMenu extends JFrame implements MouseListener{
	
	TronLightCycles mainFrame;
	//Images used in the menu
	BufferedImage menu = null, instructions = null;
	private boolean pick = false; //Boolean for if the players are on the instructions
	
	public GameMenu(TronLightCycles g){
		super("Tron LightCycles");
		mainFrame = g;
		addMouseListener(this);
		setSize(640,600);
		loadImages();
		pick = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g){
		//Fills the background black
		g.setColor(Color.black);
		g.fillRect(0,0,getWidth(),getHeight());
		if (pick){
			//Instructions page
			g.drawImage(instructions,0,0,this);
		}
		else{
			//Menu page
			g.drawImage(menu,0,0,this);
		}
	}
	
	//Loads the images used
	public void loadImages(){
		try {
    		menu = ImageIO.read(new File("Menu.png"));
		} 
		catch (IOException e) {
		}
		try{
		    instructions = ImageIO.read(new File("Instructions.png"));
		} 
		catch(IOException e){
		}
	}
	
	//MouseListener
    public void mousePressed(MouseEvent e){
    	//Instructions Page
    	if (pick){
    		//Return to menu
    		if (e.getX()<620 && e.getX()>25 && e.getY()<580 && e.getY()>520){
    			pick = false;
    			repaint();
    		}
    	}
    	//Menu Page
    	else{
    		//Starts the game
	    	if (e.getX()<440 && e.getX()>175 && e.getY()<300 && e.getY()>230){
	    		setVisible(false);
	    		mainFrame.startGame();
	    	}
	    	//Goes to instructions page
    		if (e.getX()<590 && e.getX()>60 && e.getY()<390 && e.getY()>330){
    			pick = true;
    			repaint();
    		}
    		//Exits the game
    		if (e.getX()<390 && e.getX()>215 && e.getY()<480 && e.getY()>420){
    			System.exit(0);
    		}
    	}
	}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}    
    public void mouseClicked(MouseEvent e){}  
}
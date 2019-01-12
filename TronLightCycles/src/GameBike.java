//GameBike.java
//Lucille Xiong
//The class for a Tron Bike. In the GamePanel, there are two.

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*; 

class GameBike{
	
	GamePanel gamePanel;
	
	//Direction set for the bikes
	private final static int UP = 0, LEFT = 1, RIGHT = 2, DOWN = 3;
	
	//lastX and lastY describe the position of bike's last turn
	//Initial speed of bike is needed for boosts
	private int direction, posX, posY, lastX, lastY, boostNum = 3, startSpeed;
	private double speed;
	private Color color;
	
	//List of all the lines in the bike's path
	private ArrayList<Line2D> path = new ArrayList<Line2D> ();
	
	public GameBike(GamePanel gp, int d, int posX, int posY, int speed, Color c){
		gamePanel = gp;
		this.direction = d;
		//Sets both the current and last turn position to given start position
		this.posX = lastX = posX;
		this.posY = lastY = posY;
		//Sets both the current and start speed to given speed
		this.speed = startSpeed = speed;
		color = c;
	}
	
	//Changes the direction to the given one
	public boolean moveDirection(int newD){
		//Direction change is successful when it is perpendicular to the current one
		if (newD+direction != 3 && newD!=direction){
			//Adds a new Line2D when current line is broken
			path.add(new Line2D.Double(posX,posY,lastX,lastY));
			
			//Updates turn and position
			direction = newD;
			lastX = posX;
			lastY = posY;
			return true;
		}
		return false;
	}
	
	//Moves the bike
	public void move(){
		if (direction == UP){
			posY--;
		}
		else if (direction == DOWN){
			posY++;
		}
		else if (direction == LEFT){
			posX--;
		}
		else if (direction == RIGHT){
			posX++;
		}
	}
	
	//Checks for bike's collision with itself
	public boolean selfCollision(){
		
		//Checks for collision with past lines besides the last line in the path
		for (int i=0; i<path.size()-1; i++){
			if (path.get(i).intersectsLine(currentPath())){
				return true;
			}
		}
		
		//Checks for collsion with boundaries
		if (new Line2D.Double(70,80,570,80).ptSegDist(posX,posY) == 0.0 || new Line2D.Double(70,80,70,580).ptSegDist(posX,posY) == 0.0 || new Line2D.Double(70,580,570,580).ptSegDist(posX,posY) == 0.0 || new Line2D.Double(570,80,570,580).ptSegDist(posX,posY) == 0.0){
			return true;
		}
		return false;
	}
	
	//Checks for bike's collsion with the enemy
	public boolean enemyCollision(GameBike enemy){
		
		//Checks for collsion between enemy and current line
		if (new Line2D.Double(posX, posY, lastX, lastY).ptSegDist(enemy.getPosX(),enemy.getPosY()) <= 1.0){
			return true;
		}
		
		//Check if enemy shares position
		if (posX == enemy.getPosX() && posY == enemy.getPosY()){
			return true;
		}
		
		//Checks if enemy has collided with any lines in current player's path
		for (int i=0; i<path.size(); i++){
			if (path.get(i).intersectsLine(enemy.currentPath())){
				return true;
			}
		}
		return false;
	}
	
	//Returns the current path of the player
	public Line2D currentPath(){
		return new Line2D.Double(posX, posY, lastX, lastY);
	}
	
	//Boosts the bike if there are any left and boost is not currently in use
	public void boost(){
		if (boostNum>0 && speed==startSpeed){
			speed = startSpeed*2.5;
			boostNum--;
		}
	}
	
	//Decreases the speed of the bike slowly if there is a boost
	public void reduceBoost(){
		speed = Math.max(startSpeed, speed-0.1);
	}
	
	//Returns information about the bike
	public void direction(int d){
		direction = d;
	}
	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
	public int getLastX(){
		return lastX;
	}
	public int getLastY(){
		return lastY;
	}
	public int getDirection(){
		return direction;
	}
	public double getSpeed(){
		return speed;
	}
	public int getStartSpeed(){
		return startSpeed;
	}
	public Color getColor(){
		return color;
	}
	public int getBoost(){
		return boostNum;
	}
	public int getSize(){
		return path.size();
	}
	public ArrayList<Line2D> getPath(){
		return path;
	}
}
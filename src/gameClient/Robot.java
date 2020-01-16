package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.*;
import utils.Point3D;

public class Robot {
	
	public static final double EPS=1.0E-4D;

	private static int count;
	private int id;
	private Point3D location;
	private double speed;  
	private int src, dest;
	private graph g;
	private double money;
	
	
	public Robot() {}

	public Robot(String jsonSTR) {
	
		try {
			JSONObject robot = new JSONObject(jsonSTR);
			robot=robot.getJSONObject("Robot");
			this.money=robot.getDouble("value");
			this.id = robot.getInt("id");
			this.src = robot.getInt("src");
			this.dest = robot.getInt("dest");
			this.speed = robot.getInt("speed");
			this.location = new Point3D(robot.getString("pos"));
						
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
		
	void setMoney(double v) {
		this.money = v;
	}
	public double getMoney() {
		return this.money;
	}

	public void addMoney(double d) {
		this.money += d;
	}

	public boolean setNextNode(int dest) {return true;} //need to write code
		
	public int getKey(int id) {
		return id;
	}

	private void updateSpeed() {}//write code
	
	public void randomWalk() {} //write code
	
	public int getID() {
		return this.id;
	}

	public Point3D getLocation() {
		return new Point3D(this.location); //gui location
	}

	public int getNextNode() {return 0;} // write code

	public double getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(double v) {
		this.speed = v;
	}
	
	public int getSrc() {
		return this.src;
	}
	
	public void setSrc(int s) {
		this.src=s;
	}
	
	public void setDest(int d) {
		this.dest=d;
	}
	
	public int getDest() {
		return this.dest;
	}
	
	public String toString() {
		return " ID #"+this.id+" Node src: "+this.src+" Node dest: "+this.dest;
	}

}

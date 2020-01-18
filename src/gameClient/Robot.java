package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.*;
import utils.Point3D;

public class Robot {
	
	public static final double EPS=1.0E-4D;

	private int id;
	private Point3D location;
	private double speed;  
	private int src, dest;
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
	
	//Returns the robot id.	
	void setMoney(double v) {
		this.money = v;
	}
	public double getMoney() {
		return this.money;
	}

	public void addMoney(double d) {
		this.money += d;
	}
	
	//Returns robot id 
	public int getID() {
		return this.id;
	}
	//Returns robot position on graph
	public Point3D getLocation() {
		return new Point3D(this.location);
	}

	public int getNextNode() {return 0;} // write code

	//Returns robot speed
	public double getSpeed() {
		return this.speed;
	}
	//Sets robot speed
	public void setSpeed(double v) {
		this.speed = v;
	}
	
	//Returns robot source node
	public int getSrc() {
		return this.src;
	}
	//Sets robot source node
	public void setSrc(int s) {
		this.src=s;
	}
	//Sets robot destination node
	public void setDest(int d) {
		this.dest=d;
	}
	//Returns robot destination node
	public int getDest() {
		return this.dest;
	}
	//Returns string version of Robot
	public String toString() {
		return " ID #"+this.id+" Node src: "+this.src+" Node dest: "+this.dest;
	}

}

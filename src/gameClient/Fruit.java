package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.*;
import utils.Point3D;

public class Fruit {

	private int type;
	private double value;
	private Point3D location;
	private edge_data edge ;
	private int tagged;
	public static final Comperator comp = new Comperator();

	public Fruit() {}

	//initiate a fruit from a JSON String
	public Fruit(String jsonSTR) {
		tagged = -1;
		try {
			JSONObject fruit = new JSONObject(jsonSTR);
			fruit=fruit.getJSONObject("Fruit");
			this.type=fruit.getInt("type");
			this.value = fruit.getDouble("value");
			this.location = new Point3D(fruit.getString("pos"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public int isTagged() {
		return this.tagged;
	}

	public void setTag(int b) {
		tagged = b;
	}

	//Returns fruit location on the graph
	public Point3D getLocation() {
		return new Point3D(this.location);
	}
	//Sets fruit location on the graph
	public void setLocation(Point3D p) {
		this.location=p;
	}
	//Returns if fruit is an apple or banana
	public int getType() {
		return this.type; 
	}

	//Sets fruit to be this type
	public void setType(int t) {
		this.type=t;
	}
	//Returns value of fruit
	public double getValue() {
		return this.value;
	}
	//Sets fruit to be this value
	public void setValue(double v) {
		this.value=v;
	}
	//Returns edge fruit is on
	public edge_data getEdge() {
		return this.edge;
	}
	//Sets fruit to be on this edge
	public void setEdge(edge_data e) {
		this.edge=e;
	}

	// equal if locations are the same
	@Override
	public boolean equals(Object f1) 
	{
		if(f1 instanceof Fruit) {
			Fruit tmp = (Fruit) f1;
			return this.location.equals(tmp.getLocation());
		}

		return false;
	}
	// Returns string version of Robot
	public String toString() {
		return "Fruit " + this.edge;
	}


}

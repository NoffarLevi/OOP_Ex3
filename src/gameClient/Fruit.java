package gameClient;

import org.json.JSONException;
import org.json.JSONObject;
import gui.*;
import dataStructure.*;
import utils.Point3D;

public class Fruit {

	private int type;
	private double value;
	private Point3D location;
	private edge_data edge ;
	public static final Comperator comp = new Comperator();
	
	public Fruit() {}

	//	public Fruit(int t, int v, Point3D p) {
	//		this.location =p;
	//		this.type=t;
	//		this.value=v;
	//	}

	public Fruit(String jsonSTR) {
		
		try {
			JSONObject fruit = new JSONObject(jsonSTR);
			fruit=fruit.getJSONObject("Fruit");
			this.type=fruit.getInt("type");
			this.value = fruit.getDouble("value");
			this.edge = null;
			this.location = new Point3D(fruit.getString("pos"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	public Fruit(double v, Point3D p, edge_data e) {
		this.value = v;
		this.location = new Point3D(p);
		this.edge = e;
	}
	
	public Point3D getLocation() {
		return new Point3D(this.location);
	}

	public void setLocation(Point3D p) {
		this.location=p;
	}

	public int getType() {
		return this.type; //apple
	}

	public void setType(int t) {
		this.type=t;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double v) {
		this.value=v;
	}
	
	public edge_data getEdge() {
		return this.edge;
	}
	
	public void setEdge(edge_data e) {
		this.edge=e;
	}
	
	public String toString() {
		return "value" + this.value + "eSrc "+this.getEdge().getSrc();
	}
	
}

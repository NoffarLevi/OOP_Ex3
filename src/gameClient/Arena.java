package gameClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;


import Server.Game_Server;
import Server.game_service;
import dataStructure.*;
import utils.Point3D;

public class Arena {

	public static final double EPS1 = 0.001, EPS2 = Math.pow(EPS1,2);
	private game_service game;
	private graph grGame;
	private ArrayList<Fruit>fruits;
	private Hashtable<Integer,Robot> robots;
	private KML_Logger kml;
	private int currentlevel;

	public Arena() {}

	//Initiates arena with the level the server entered
	public Arena(int num_level) {
		currentlevel = num_level; 
		
		kml = KML_Logger.getObject(num_level); // initiate KML
		game = Game_Server.getServer(num_level);

		grGame = new DGraph();
		grGame.init(game.getGraph());
		GraphToKML();
		
		fruits= new ArrayList<Fruit>();
		robots = new Hashtable<Integer,Robot>(); //Integer is robot id
		fruitsInit();
		setPositionRo();
		robotsInit();
		
	}
	
	//adds placemarks of nodes and edges to kml
	private void GraphToKML() {
		for (node_data node : grGame.getV()) {
			kml.addNodeMark(node.getLocation());
			for (edge_data edge : grGame.getE(node.getKey())) {

				kml.addEdgeMark(grGame.getNode(edge.getSrc()).getLocation(), grGame.getNode(edge.getDest()).getLocation());
			}
		}
	}
	
	//needed for stats of Data Basa - num_level being playe
	public int getcurrentlevel() {
		return currentlevel;
	}

	//Initiates a Robot object from JSON String
	public void robotsInit() {

		for (String r : game.getRobots()) {			
			Robot ro = new Robot(r);
			kml.addMark("robot",ro.getLocation());
			robots.put(ro.getID(), ro);
		}
	}

	//sets Robot starting position near highest valued fruit
	private void setPositionRo() {

		try {
			JSONObject robot = new JSONObject(game.toString());
			int size= robot.getJSONObject("GameServer").getInt("robots");
			for (int i = 0; i < size; i++) {
				int src = fruits.get(i).getEdge().getSrc();
				game.addRobot(src);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}

	//Initiates a Fruit object from JSON String
	public void fruitsInit() {
		synchronized (fruits) 
		{ 
			fruits.clear();
			if(fruits.isEmpty()) {
				for(String f : game.getFruits()) {
					Fruit fr = new Fruit(f);
					getEdge(fr);
					fruits.add(fr);

					if(fr.getType()==1) {
						kml.addMark("apple",fr.getLocation()); // placemark to kml

					}
					else {
						kml.addMark("banana",fr.getLocation()); // placemark to kml
					}
				}
				fruits.sort(Fruit.comp);
				return;
			}
		}
	}

	//Sets Fruit on specified edge
	public void getEdge(Fruit fruit) { //find location of fruit

		for (node_data n : this.grGame.getV()) {
			for (edge_data e : this.grGame.getE(n.getKey())) {
				node_data dest= this.grGame.getNode(e.getDest());
				double dist1 = n.getLocation().distance2D(fruit.getLocation());
				double dist2 = fruit.getLocation().distance2D(dest.getLocation());
				double dist3 = n.getLocation().distance2D(dest.getLocation());
				double check = dist3 - (dist1 + dist2);

				int t=0;
				if(n.getKey()>dest.getKey()) {t=-1;}
				else {t=1;}

				if((Math.abs(check) <= EPS2) && (fruit.getType() == t)) 
				{
					fruit.setEdge(e);
					return;
				}			
			}
		}

	}

	//Refreshed the fruits and robots from the server and updates the lists.
	public void refresh(){
		fruitsInit();
		robotsInit();		
	}
	//Returns the game's graph
	public graph getGraph() {
		return this.grGame;
	}
	//Returns the game object
	public game_service getGame() {
		return this.game;
	}
	//Returns the list of Robots
	public Hashtable<Integer,Robot> getRobots() {
		return this.robots;
	}
	//Returns the list of Fruits
	public ArrayList<Fruit> getFruits(){
		return this.fruits;
	}
}





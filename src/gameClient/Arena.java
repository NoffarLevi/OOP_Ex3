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

	public static final double EPS = 0.001D;
	private game_service game;
	private graph grGame;
	private ArrayList<Fruit>fruits;
	private Hashtable<Integer,Robot> robots;


	public Arena(int num_level) {

		game = Game_Server.getServer(num_level);
		grGame = new DGraph();
		grGame.init(game.getGraph());
		fruits= new ArrayList<Fruit>();
		robots = new Hashtable<Integer,Robot>(); //Integer is robot id
		fruitsInit();
		setPositionRo();
		robotsInit();

	}

	public void robotsInit() {

		for (String r : game.getRobots()) {			
			Robot ro = new Robot(r);
			robots.put(ro.getID(), ro);
		}
	}


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

	public void fruitsInit() {
		synchronized (fruits) 
		{ 
			fruits.clear();
			if(fruits.isEmpty()) {
				for(String f : game.getFruits()) {
					Fruit fr = new Fruit(f);
					getEdge(fr);
					fruits.add(fr);
				}
				fruits.sort(Fruit.comp);
			}
		}
	}

	public void getEdge(Fruit f) { //find location of fruit
		for (node_data n : grGame.getV()) {
			for (edge_data e : grGame.getE(n.getKey())) {

				Point3D pF = new Point3D(f.getLocation().x(),f.getLocation().y());
				Point3D pEsrc = new Point3D(grGame.getNode(e.getSrc()).getLocation().x(),grGame.getNode(e.getSrc()).getLocation().y());
				Point3D pEdest = new Point3D(grGame.getNode(e.getDest()).getLocation().x(),grGame.getNode(e.getDest()).getLocation().y());

				double dist1 = pEsrc.distance2D(pEdest);
				double dist2 = pF.distance2D(pEsrc)+pF.distance2D(pEdest);
				if(dist1-dist2 < EPS && dist1-dist2 >= ((-1)*EPS)) 
				{
					f.setEdge(e);
				}			
			}
		}

	}

	public void refresh(){
		fruitsInit();
		robotsInit();		
	}

	public graph getGraph() {
		return this.grGame;
	}

	public game_service getGame() {
		return this.game;
	}

	public Hashtable<Integer,Robot> getRobots() {
		return this.robots;
	}

	public ArrayList<Fruit> getFruits(){
		return this.fruits;
	}
}





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
		fruits= new ArrayList<Fruit>();
		robots = new Hashtable<Integer,Robot>(); //Integer is robot source
		fruitsInit();
		robotsInit();

	}

	public void robotsInit() {
		synchronized (robots) {
			robots.clear();
			try {
				JSONObject robot = new JSONObject(game.toString());
				int size= robot.getJSONObject("GameServer").getInt("robots");
				for (int i = 0; i < size; i++) {
					game.addRobot(i); //i is location need to change
					Robot r = new Robot(game.getRobots().get(i));
					robots.put(r.getID(), r);
				}
				setPositionRo();
			} catch (JSONException e) {
				e.printStackTrace();
			}		
		}
	}	

	private void setPositionRo() {
		int i=0;
		for (Entry<Integer, Robot> r : robots.entrySet()) {

			r.getValue().setSrc(fruits.get(i).getEdge().getSrc());
			i++;
		}
	}

	public void fruitsInit() {
		synchronized (fruits) {
			fruits.clear();
			for(String f : game.getFruits()) {
				Fruit fr = new Fruit(f);
				getEdge(fr);
				fruits.add(fr);
			}
			fruits.sort(Fruit.comp);
		}
	}

	public static void main(String[] args) {

	}

	public void getEdge(Fruit f) { //find location of fruit
		for (node_data n : grGame.getV()) {
			for (edge_data e : grGame.getE(n.getKey())) {

				Point3D pF = new Point3D(f.getLocation().x(),f.getLocation().y());
				Point3D pEsrc = new Point3D(grGame.getNode(e.getSrc()).getLocation().x(),grGame.getNode(e.getSrc()).getLocation().y());
				Point3D pEdest = new Point3D(grGame.getNode(e.getDest()).getLocation().y(),grGame.getNode(e.getDest()).getLocation().y());

				double dist1 = pEsrc.distance2D(pEdest);
				double dist2 = pF.distance2D(pEsrc)+pF.distance2D(pEdest);
				if(dist1-dist2<= EPS) {f.setEdge(e);}			
			}
		}

	}



}





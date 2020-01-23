package gameClient;

import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import Server.game_service;
import Server.robot;
import algorithms.Graph_Algo;
import dataStructure.graph;
import dataStructure.node_data;

public class AlgoGame {

	private Arena arena;
	private Graph_Algo graphA;
	private Hashtable <Integer, List<node_data>> roads;
	private Hashtable <Integer, Fruit> ifFruitTagged;
	private int count=-1;
	

	public AlgoGame() {}

	//Initials automatic game from 'board game' built initialized in Arena
	public AlgoGame(Arena a) 
	{
		this.arena = a;
		this.graphA = new Graph_Algo(arena.getGraph());
		this.roads = new Hashtable <Integer, List<node_data>>();
		ifFruitTagged = new Hashtable <Integer, Fruit>();
		
		initRoads();
	}
	
	//find next position of the robot
	public void moveRobots(game_service g) {
		int dest =-1;
		for (int i=0; i<arena.getRobots().size(); i++) {
			Robot robot = arena.getRobots().get(i);
			if(robot.getDest()==-1) {
				dest=nextNode(i);
				arena.getGame().chooseNextEdge(i, dest);						
			}
		}
	}
	
	//finds robot's next node according to closest fruit
	private int nextNode(int id) {
	
		Robot r= arena.getRobots().get(id);
		List<node_data> path=roads.get(id);
		if(path.isEmpty()==true) {
			synchronized (arena.getFruits()) {
				if(arena.getFruits().size()>0) {
					Fruit f=findNearestFruit(r);
					path=graphA.shortestPath(r.getSrc(), f.getEdge().getSrc());
					node_data des=arena.getGraph().getNode(f.getEdge().getDest());
					path.add(des);
					roads.put(r.getID(), path);
				}
			}
		}

		node_data nd = path.get(0);
		path.remove(0);
		return nd.getKey();

	}
	
	//finds the nearest fruit of the given Robot
	private Fruit findNearestFruit(Robot r) {
		double minDist = Double.POSITIVE_INFINITY;
		Fruit wanted = null;
		for (int i=0; i<arena.getFruits().size(); i++) {
			Fruit fr = arena.getFruits().get(i);
			double d = graphA.shortestPathDist(r.getSrc(), fr.getEdge().getSrc());
			if(d<minDist) {
				minDist =d;
				wanted = fr;
			}
		}
		return wanted;
	}

	//the first path of the Robot
	private void initRoads() {
		for (int i = 0; i < arena.getRobots().size(); i++) {
			Robot r=arena.getRobots().get(i);
			Fruit f=arena.getFruits().get(i);
			List<node_data> road=graphA.shortestPath(r.getSrc(), f.getEdge().getSrc());
			roads.put(r.getID(), road);
		}		
	}

}

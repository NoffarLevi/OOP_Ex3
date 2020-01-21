package gameClient;

import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import Server.game_service;
import Server.robot;
import algorithms.Graph_Algo;
import dataStructure.graph;
import dataStructure.node_data;

public class AlgoGame extends Thread {

	private Arena arena;
	private Graph_Algo graphA;
	private Hashtable <Integer, List<node_data>> roads;
	private int count=0;

	public AlgoGame() {}

	public AlgoGame(Arena a) 
	{
		this.arena = a;
		this.graphA = new Graph_Algo(arena.getGraph());
		this.roads = new Hashtable <Integer, List<node_data>>();
		initRoads();
	}

	public Fruit findTheFruit(Robot r) {
		double shortestDist = Double.POSITIVE_INFINITY;
		Fruit get = null;
		for (int i=0; i<arena.getFruits().size(); i++) {
			Fruit f = arena.getFruits().get(i);
			if(f.isTagged() == 1) {
				continue;
			}
			double distance = graphA.shortestPathDist(r.getSrc(), f.getEdge().getSrc());
			if(distance< shortestDist) {
				shortestDist= distance;
				get = f;
			}
		}
		get.setTag(1);
		return get;

	}
	public void moveRobots(game_service g) {
		int next =-1;
		for (int i=0; i<arena.getRobots().size(); i++) {
			Robot robot = arena.getRobots().get(i);
			if(robot!=null && robot.getDest()==-1) {
				next=nextNode(i);
				arena.getGame().chooseNextEdge(i, next);						
			}
		}
	}

	private int nextNode(int id) {
		if(!arena.getGame().isRunning())
			return -1;
		List<node_data> robotRoad=roads.get(id);
		Robot r= arena.getRobots().get(id);
		if(robotRoad.isEmpty()==true) {
			synchronized (arena.getFruits()) {
				if(arena.getFruits().size()>0) {
					Fruit f=arena.getFruits().get(id);
					System.out.println("edge is "+f.getEdge());
					robotRoad=graphA.shortestPath(r.getSrc(), f.getEdge().getSrc());
					node_data des=arena.getGraph().getNode(f.getEdge().getDest());
					robotRoad.add(des);
					roads.put(r.getID(), robotRoad);
				}

			}
		}

		node_data nd = robotRoad.get(0);
		robotRoad.remove(0);
		return nd.getKey();

		//		for (int j = 0; j < robotRoad.size(); j++) {
		//			node_data curr=robotRoad.get(j);
		//			robotRoad.remove(j);
		//			if(curr.getKey()==r.getSrc()) {
		//				continue;
		//			}
		//			return curr.getKey();
		//		}
		//		return -1;
	}

	private void initRoads() {
		for (int i = 0; i < arena.getRobots().size(); i++) {
			Robot r=arena.getRobots().get(i);
			Fruit f=arena.getFruits().get(i);
			List<node_data> road=graphA.shortestPath(r.getSrc(), f.getEdge().getSrc());
			roads.put(r.getID(), road);
		}		
	}

}

package gameClient;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import dataStructure.*;

public class SimpleGameClient {
	
	public static void main(String[] a) 
		{test1();}
	public static void test1() 
	{
	game_service game = Game_Server.getServer(23);
	String g=game.getGraph();
	DGraph gd = new DGraph();
	gd.init(g);

	//MyGameGui gr = new MyGameGui(gd);
	game.addRobot(0);
	game.addRobot(1);
	game.addRobot(2);
	System.out.println(game.toString());
	System.out.println(game.getGraph());
	System.out.println(game.getFruits());
	}

}

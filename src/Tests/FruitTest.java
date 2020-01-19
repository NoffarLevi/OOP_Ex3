package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import dataStructure.Edge;
import dataStructure.edge_data;
import gameClient.Comperator;
import gameClient.Fruit;
import utils.Point3D;

class FruitTest {

	static ArrayList<Fruit> fruits;
	static Comperator comp = new Comperator();

	@BeforeAll
	static void init() {
		fruits = new ArrayList<Fruit>();
		game_service game =  Game_Server.getServer(1);
		System.out.println(game.getFruits());
		for (String f : game.getFruits()) {
			Fruit fr = new Fruit(f);
			fruits.add(fr);			
		}
		fruits.sort(comp);
	}

	@Test
	void getType() {
		for(int i=0; i<2; i++)
		{

			assertEquals(-1,fruits.get(i).getType());
		}
	}
	@Test
	void getLocation() {
		for(int i=0; i<2; i++) {

			Point3D point = new Point3D(fruits.get(i).getLocation());
			assertEquals(point,fruits.get(i).getLocation());
		}		  
	}

	@Test
	void testtoString() {
		for(int i=0; i<2; i++) {

			String string = fruits.get(i).toString();
			assertEquals(string,fruits.get(i).toString());
		}		  
	}

	@Test
	void getValue() {	
		assertEquals(8.0,fruits.get(0).getValue());
		assertEquals(5.0,fruits.get(1).getValue());
	}
	
}

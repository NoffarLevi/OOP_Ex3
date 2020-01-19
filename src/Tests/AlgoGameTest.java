package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import gameClient.AlgoGame;
import gameClient.Arena;
import gameClient.Fruit;

class AlgoGameTest{

	static Arena arena;
    static AlgoGame algo_game;
    
	@BeforeAll
	static void init(){
		game_service game = Game_Server.getServer(5);
		arena = new Arena(5);
	}

	@Test
	void testgetGame() {
		
		String s = arena.getGame().toString();
		assertEquals(s, arena.getGame().toString());
			
	}
	
	@Test
	void testFruitsInit() {
		List<String> fruits = arena.getGame().getFruits();
		for (String f : arena.getGame().getFruits()) {
			assertTrue(fruits.contains(f));
		}
	}
	
	@Test
	void testRobotInit() {
		List<String> fruits = arena.getGame().getRobots();
		for (String r : arena.getGame().getRobots()) {
			assertTrue(fruits.contains(r));
		}
		
	}

}

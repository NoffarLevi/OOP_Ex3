package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Hashtable;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Server.Game_Server;
import Server.game_service;
import gameClient.Robot;
import utils.Point3D;

class RobotTest {

	static Hashtable<Integer,Robot> robots;

	@BeforeEach
	void init() {
		robots = new Hashtable<Integer,Robot>();
		game_service game =  Game_Server.getServer(1);
		for (String r : game.getRobots()) {
			Robot robot = new Robot(r);
			robots.put(robot.getID(), robot);
		}
	}
	@Test
	void testgetID() {

		for (Entry<Integer, Robot> robot : robots.entrySet()) {

			assertEquals(0,robot.getValue().getID());
		}

	}
	@Test
	void testgetLocation() {
		for (Entry<Integer, Robot> robot : robots.entrySet()) {

			Point3D point = new Point3D(robot.getValue().getLocation());
			assertEquals(point,robot.getValue().getLocation());
		}		  
	}

	@Test
	void testtoString() {
		for (Entry<Integer, Robot> robot : robots.entrySet()) {

			String string = robot.getValue().toString();
			assertEquals(string,robot.getValue().toString());
		}		  
	}

	@Test
	void testgetValue() {	
		for (Entry<Integer, Robot> robot : robots.entrySet()) {

			assertEquals(0.0,robot.getValue().getMoney());
		}
	}

}

package gameClient;

import javax.swing.JFrame;

import Server.Game_Server;
import Server.game_service;
import dataStructure.*;

public class TheGame extends JFrame{
	
	game_service game;

	public TheGame(int levelNum) {
		game = Game_Server.getServer(levelNum);
		
	}
	
	
	
}

package gameClient;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.json.JSONException;
import org.json.JSONObject;

import Server.*;
import dataStructure.*;

public class ClientRun extends Thread {

	private MyGameGui win;
	private Arena arena;
	private KML_Logger log;

	private AlgoGame automatic;
	private static int num_level=0;
	private static boolean manGame=false;
	private game_service game;

	public ClientRun() {
		arena = new Arena(num_level);
		win = new MyGameGui(arena, manGame);
	}

	@Override
	public void run() {

		game = arena.getGame();
		game.startGame();

		automatic = null;

		if(!manGame) {
			System.out.println(game.getFruits());
			automatic = new AlgoGame(arena);
			automatic.start();
		}

		try {

			int dati = 65;
			int check = 0;
			while(game.isRunning()) {
				if(check % 2 == 0) {
					arena.getGame().move();
					arena.refresh();
				}
				win.repaint();
				Thread.sleep(dati);
				check++;
			}


			if(automatic != null) {
				automatic.interrupt(); //kills the auto game
			}

		}catch(Exception e) {
			e.printStackTrace();
		}

		log = KML_Logger.getVariable(num_level);
		log.end();
		KMLlog();
		
		double score = getScore();
		int moves = getMoves();
		
		JOptionPane.showMessageDialog(win, "Game Over !\n Points earned: "+score+" in "+moves+" moves.");
		win.setVisible(false);
		System.exit(0);
	}

	private void KMLlog() {
		int result = JOptionPane.showConfirmDialog(win, "Write to KML?");
		if(result == 0) {
			log.export();
		}
				
	}

	private static void init() {
		JFrame frame = new JFrame();
		frame.setBounds(200, 0, 500, 500);
		try {

			String[] mode = { "Manual", "Automatic" };

			int m = JOptionPane.showOptionDialog(frame, "Choose option", "The Maze of Waze",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, mode, mode[1]);
			String lev = JOptionPane.showInputDialog(frame, "Please insert a scenerio [0-23]");

			num_level = Integer.parseInt(lev);

			if (num_level > 23 || num_level < 0)
				throw new RuntimeException();

			manGame = false;
			if (m == 0) {
				manGame = true;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Invalid input.\nPlaying default game", "Error",
					JOptionPane.ERROR_MESSAGE);
			num_level = 0;
			manGame = false;
		}
	}

	private int getMoves() {
		int moves =-1;
		try {
			JSONObject obj =new JSONObject(arena.getGame().toString()).getJSONObject("GameServer");
			moves=obj.getInt("moves");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return moves;
	}

	private double getScore() {

		int score=-1;
		try {
			JSONObject obj = new JSONObject(arena.getGame().toString()).getJSONObject("GameServer");
			score=obj.getInt("grade");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return score;
	}

	public static void main(String[] args) {
		init();
		ClientRun client=new ClientRun();
		client.start();
	}

	public boolean getType() {
		return manGame;
	}

	public AlgoGame getAlgo() {
		return automatic;
	}


}

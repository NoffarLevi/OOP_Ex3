package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class MyGameGui extends JFrame implements MouseListener{

	private Arena arena;
	double xMax, xMin, yMax, yMin;
	private BufferedImage apple, banana, minion;
	int xRobot,yRobot,xDest,yDest;
	node_data dest;
	private boolean manGame;
	private int ROBOT_ID, NODE_DEST;

	public MyGameGui() {}

	public MyGameGui(Arena a, boolean flag)
	{	
		arena = a;
		manGame = flag;

		try {
			apple = ImageIO.read(new File("apple.png"));
			banana = ImageIO.read(new File("banana.png"));
			minion = ImageIO.read(new File("minion.png"));


		}catch(Exception e) {
			System.out.println("Could not read file!");
			e.printStackTrace();
		} 

		initGUI();

	}

	private void initGUI() 
	{
		this.setSize(1100,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		if(manGame) {
			System.out.println("isManual");
			this.addMouseListener(this);
		}

	}

	public void paint(Graphics g)
	{
		BufferedImage BImage = new BufferedImage(1100, 800,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = BImage.createGraphics();

		g2d.setBackground(new Color(240,240,240));
		g2d.clearRect(0, 0, 1100, 800);

		drawGraph(g2d);
		drawTime(g2d);
		drawFruit(g2d);
		drawRobots(g2d);

		Graphics2D g2dComp = (Graphics2D) g;
		g2dComp.drawImage(BImage, null, 0, 0);



	}

	private void drawGraph(Graphics2D g) {

		scalePoints(arena.getGraph());

		for (node_data n : arena.getGraph().getV()) 
		{
			g.setColor(Color.BLUE);
			g.fillOval(n.getLocation2().ix()-5,n.getLocation2().iy()-10, 10, 10);
			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString(n.getKey()+"", n.getLocation2().ix()+20, n.getLocation2().iy());

			if(arena.getGraph().getE(n.getKey())!= null) {
				for (edge_data e : arena.getGraph().getE(n.getKey())) {

					node_data des = arena.getGraph().getNode(e.getDest());
					g.setColor(Color.RED);
					g.drawLine(n.getLocation2().ix(), n.getLocation2().iy(), des.getLocation2().ix(), des.getLocation2().iy());

					g.setColor(Color.YELLOW);

					double middlex=0,middley=0;
					middlex=((des.getLocation2().x()+n.getLocation2().x())/2);
					middley=((des.getLocation2().y()+n.getLocation2().y())/2);

					for (int i = 0; i < 2; i++) {
						middlex=((middlex+des.getLocation2().x())/2);
						middley=((middley+des.getLocation2().y())/2);
					}		
					g.fillOval((int)middlex-5,(int)middley-5, 5, 5);

				}
			}
		}
	}

	private void drawFruit(Graphics2D g) {
		//paint fruits
		for (Fruit f : arena.getFruits()) {
			double x= scale(f.getLocation().x(),xMin ,xMax,30,900);
			double y= scale(f.getLocation().y(),yMax ,yMin,50,700);

			if(f.getType()==-1) {
				g.drawImage(apple, (int)x-7, (int)y-10, this);

			}
			else {

				g.drawImage(banana, (int)x-7, (int)y-10,this);

			}
		}
	}

	private void drawRobots(Graphics2D g) {

		//paint robots
		for (Entry<Integer, Robot> r : arena.getRobots().entrySet()) 	
		{
			double x= scale(r.getValue().getLocation().x(),xMin ,xMax,30,900);
			double y= scale(r.getValue().getLocation().y(),yMax ,yMin,50,700);

			//			g.setColor(Color.BLACK);
			//			g.drawOval((int)x-8,(int)y-12, 15, 15);
			g.drawImage(minion, (int)x-8, (int)y-12,this);

		}
	}

	private void drawTime(Graphics2D g) {

		//paint time
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial",Font.BOLD,15));
		g.drawString("Time: "+arena.getGame().timeToEnd()/1000,50 , 70);
	}

	public void scalePoints(graph g) 
	{
		xMax=getMaxNodeX(g);
		xMin=getMinNodeX(g);
		yMax=getMaxNodeY(g);
		yMin=getMinNodeY(g);

		for (node_data n : g.getV()) 
		{
			double x=scale(n.getLocation().x(),xMin ,xMax,30,900);
			double y=scale(n.getLocation().y(),yMax, yMin,50,700);

			Point3D p = new Point3D(x,y);

			n.setLocation2(p);	
		}
	}
	/**
	 * 
	 * @param data denote some data to be scaled
	 * @param r_min the minimum of the range of your data
	 * @param r_max the maximum of the range of your data
	 * @param t_min the minimum of the range of your desired target scaling
	 * @param t_max the maximum of the range of your desired target scaling
	 * @return
	 */
	private double scale(double data, double r_min, double r_max, 
			double t_min, double t_max)
	{
		double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
		return res;
	}

	public double getMaxNodeX(graph g) {
		double max=Double.NEGATIVE_INFINITY;
		for (node_data n : arena.getGraph().getV()) {
			if(n.getLocation().x()>max) {
				max=n.getLocation().x();
			}
		}		
		return max;
	}

	public double getMinNodeX(graph g) {

		double min=Double.POSITIVE_INFINITY;
		for (node_data n : arena.getGraph().getV()) {
			if(n.getLocation().x()<min) {
				min=n.getLocation().x();
			}
		}		
		return min;
	}
	public double getMaxNodeY(graph g) {

		double max=Double.NEGATIVE_INFINITY;
		for (node_data n : arena.getGraph().getV()) {
			if(n.getLocation().y()>max) {
				max=n.getLocation().y();
			}
		}		
		return max;
	}

	public double getMinNodeY(graph g) {

		double min=Double.POSITIVE_INFINITY;
		for (node_data n : arena.getGraph().getV()) {
			if(n.getLocation().y()<min) {
				min=n.getLocation().y();
			}
		}		
		return min;
	}

	@Override
	public void mouseClicked(MouseEvent e) {


		if(!arena.getGame().isRunning())
			{return;}
		
			xRobot = e.getX();  //x of robot
			yRobot = e.getY();

			Point3D p = new Point3D(xRobot,yRobot);
			if(e.getClickCount() == 1) {
				node_data node = findNodePosOnGraph(p);

				if(node != null) {
					NODE_DEST = node.getKey();

					try {

						arena.getGame().chooseNextEdge(ROBOT_ID,NODE_DEST);
						NODE_DEST = -1;


					}catch(Exception error) {
						error.printStackTrace();
					}
				}

			}	

			if (e.getClickCount() == 2) {
				Robot r = findRbotPosOnGraph(p);
				if (r != null) {
					ROBOT_ID = r.getID();
				}
			}
		}

	private node_data findNodePosOnGraph(Point3D p) {

		for (node_data n :arena.getGraph().getV()) {
			double dist = n.getLocation2().distance2D(p);
			if(dist>= 0 && dist <= 20) {
				return n;
			}
		}
		return null;
	}

	private Robot findRbotPosOnGraph(Point3D p){

		for (Entry<Integer, Robot> ro : arena.getRobots().entrySet()) {
			double originalX = ro.getValue().getLocation().x();
			double originalY = ro.getValue().getLocation().y();

			double scaleX = scale(originalX, xMin ,xMax,30,900);
			double scaleY = scale(originalY, yMax, yMin,50,700);

			Point3D p1 = new Point3D(scaleX, scaleY);
			double dist = p1.distance2D(p);

			if(dist>= 0 && dist <= 20)
			{
				return ro.getValue();
			}
		}

		return null;
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}





//comments about the asignments

/*  
 * class tree diagram
 *
 * delete unnccery code
 * in package gameclient  there is thread-look
 * manual( int closestNode(Point p, graph g)
 * setscale x&y jframe  test take between 1-3 get 2.5  blabla
 * in boaz class-myframe-> paint
 *  
 * 
 * 
 * 
 * 
 */


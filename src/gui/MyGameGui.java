package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class MyGameGui extends JFrame{

	private graph g;
	private Graph_Algo Galgo;
	private int MC;

	public MyGameGui() {	
		g = new DGraph();
		Galgo = new Graph_Algo();
		Galgo.init(g);
		MC=0;
		initGUI();
	}

	public MyGameGui(graph g)
	{	
		System.out.println(g.getV());
		for (node_data n : g.getV()) {
			System.out.println("l "+n.getLocation());}
		this.g=g;
		Galgo = new Graph_Algo();
		Galgo.init(g);
		MC=g.getMC();
		initGUI();
	}

	public MyGameGui(String file) {
		this.g=null;
		Galgo = new Graph_Algo();
		Galgo.init(file);
		this.g=Galgo.copy();
		MC=g.getMC();
		initGUI();
	}

	private void initGUI() 
	{
		this.setSize(1000,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		scalePoints(this.g);
		
		for (node_data n : this.g.getV()) 
		{
			g.setColor(Color.BLUE);
			g.fillOval(n.getLocation2().ix()-5,n.getLocation2().iy()-10, 10, 10);
			g.setFont(new Font("Arial", Font.BOLD, 14));
			g.drawString(n.getKey()+"", n.getLocation2().ix()+20, n.getLocation2().iy());

			if(this.g.getE(n.getKey())!= null) {
				for (edge_data e : this.g.getE(n.getKey())) {

					node_data des = this.g.getNode(e.getDest());
					g.setColor(Color.RED);
					g.drawLine(n.getLocation2().ix(), n.getLocation2().iy(), des.getLocation2().ix(), des.getLocation2().iy());

//					int midX=(n.getLocation2().ix()+des.getLocation2().ix())/2;
//					int midY=(n.getLocation2().iy()+des.getLocation2().iy())/2;
//					g.setFont(new Font("Arial", Font.BOLD, 15));
//					g.drawString(e.getWeight()+"", midX, midY);

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
	

	public void scalePoints(graph g) 
	{
		
		double xMax=getMaxNodeX(g);
		double xMin=getMinNodeX(g);
		double yMax=getMaxNodeY(g);
		double yMin=getMinNodeY(g);
		
		for (node_data n : g.getV()) 
		{
			
		double x=scale(n.getLocation().x(),xMin ,xMax,50,700);
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
		for (node_data n : this.g.getV()) {
			if(n.getLocation().x()>max) {
				max=n.getLocation().x();
			}
		}		
		return max;
	}

public double getMinNodeX(graph g) {
	
	double min=Double.POSITIVE_INFINITY;
		for (node_data n : this.g.getV()) {
			if(n.getLocation().x()<min) {
				min=n.getLocation().x();
			}
		}		
		return min;
	}
public double getMaxNodeY(graph g) {
	
	double max=Double.NEGATIVE_INFINITY;
		for (node_data n : this.g.getV()) {
			if(n.getLocation().y()>max) {
				max=n.getLocation().y();
			}
		}		
		return max;
	}

public double getMinNodeY(graph g) {
	
	double min=Double.POSITIVE_INFINITY;
		for (node_data n : this.g.getV()) {
			if(n.getLocation().y()<min) {
				min=n.getLocation().y();
			}
		}		
		return min;
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


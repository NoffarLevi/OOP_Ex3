package gameClient;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import dataStructure.node_data;

public class garbage {
	
	
	
//	JFrame fr= new JFrame("Begin Game");
//	JButton btn1 =new JButton("Automatic");
//	JButton btn2 =new JButton("Manual");
//	JLabel label =new JLabel("Enter Game Level");
//	
//	JTextArea level = new JTextArea(1,11);
//
//	fr.setLayout(new FlowLayout());
//	fr.setBounds(100, 100, 250, 200);
//	fr.add(btn1);
//	fr.add(btn2);
//	fr.add(label);
//	fr.add(level);
//	fr.setVisible(true);
//	
//	
//	btn1.addActionListener(new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent e) 
//		{
//			System.out.println("Auto");
//			fr.setVisible(false);
//			String m = level.getText();
//			int lev = Integer.parseInt(m);
//			if(lev >23 || lev<0) throw new RuntimeException();
//			manGame=false;
//		}
//	});
//
//	btn2.addActionListener(new ActionListener() {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			fr.setVisible(false);
//			System.out.println("Manu");
//			fr.setVisible(false);
//			String m = level.getText();
//			int lev = Integer.parseInt(m);
//			if(lev >23 || lev<0) throw new RuntimeException();
//			manGame=true;
//		}	
//	});
//
	
//	if(e.getClickCount()%2 == 0) { //needs to click mouse twice to "get" robot
//		System.out.println("1 "+e.getXOnScreen());
//		xRobot = e.getX();  //x of robot
//		yRobot = e.getY();
//	}
//
//	if(e.getClickCount()%2 != 0) { //needs to click mouse once to "get" node destination
//		System.out.println("2 "+e.getXOnScreen());
//		xDest = e.getX();  //x of robot
//		yDest = e.getY();
//
//		dest = getNode(xDest, yDest);
//		System.out.println("de is "+dest);
//	}
//
//	for (Entry<Integer, Robot> ro : arena.getRobots().entrySet()) {
//		double originalX = ro.getValue().getLocation().x();
//		double originalY = ro.getValue().getLocation().y();
//
//		double scaleX = scale(originalX, xMin ,xMax,30,900);
//		double scaleY = scale(originalY, yMax, yMin,50,700);
//
//		if((scaleX+scaleY)-(xRobot+yRobot)<= arena.EPS && (scaleX+scaleY)-(xRobot+yRobot) > arena.EPS)
//		{
//			if(ro.getValue().getDest()==-1) 
//			{
//				arena.getGame().chooseNextEdge(ro.getValue().getID(), 4);
//				//move();
//				repaint();
//			}
//		}
//	}
//}
//
//
//private node_data getNode(int xDest,int yDest) {
//	for (node_data node : arena.getGraph().getV()) {
//		System.out.println("nl "+node.getLocation2().ix()+" xl "+xDest);
//		if(node.getLocation2().ix()-xDest <= 5 && node.getLocation2().ix()-xDest >=-1)
//		{
//
//			//	if(node.getLocation2().iy()-xDest < arena.EPS && node.getLocation2().iy()-xDest >= arena.EPS) {
//			return node;
//			//	}
//
//		}
//	}
//	return null;
//}

}

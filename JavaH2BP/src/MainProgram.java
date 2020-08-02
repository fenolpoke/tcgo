import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class MainProgram extends JFrame{

	/**
	 * @param args
	 */	
	
	private final int DOT_RADIUS = 20;
	private final int DOT_ROW_COUNT = 8;
	private final int DOT_COLUMN_COUNT = 10;
	private final Color[] DOT_COLORS = {
			new Color(254,205,108) //yellow
			,new Color(119,194,152) //green
			,new Color(113,141,191) //blue
			,new Color(164,84,125) //purple
			,new Color(232,77,96)//red
			};
	private static boolean isDragged = false;
	
	
	private LineBorder createBorderDots(Color color, int radius){
		return new LineBorder(color, radius*2, true);
	}
	
	
	
	public MainProgram() {
		
		final JPanel[][] dots = new JPanel[DOT_ROW_COUNT][DOT_COLUMN_COUNT];		
		final ArrayList<Point> connectedDots = new ArrayList<>();
		
		setTitle("");
		setSize(DOT_COLUMN_COUNT * DOT_RADIUS*3+DOT_RADIUS, DOT_ROW_COUNT*DOT_RADIUS*3+DOT_RADIUS);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);
		
		JPanel bgPanel = new JPanel(new GridLayout(DOT_ROW_COUNT, DOT_COLUMN_COUNT, DOT_RADIUS, DOT_RADIUS));
		
		bgPanel.setBounds(((int)(DOT_RADIUS*1.75f)), DOT_RADIUS, getWidth()-DOT_RADIUS*4, getHeight()-DOT_RADIUS*4);

		final JPanel panel = new JPanel();
		panel.setBounds(getWidth()/2-25, getHeight()/2-25, DOT_RADIUS*2,DOT_RADIUS*2);
		panel.setOpaque(false);
		panel.setBorder(createBorderDots(Color.CYAN, DOT_RADIUS));
	
		add(panel);
		
		for (int i = 0; i < DOT_ROW_COUNT; i++) {
			for (int  j= 0; j < DOT_COLUMN_COUNT; j++) {
				
				final int x = i, y = j;
				
				JPanel p = new JPanel();
				dots[i][j] = p;
//				p.setBounds(i*DOT_RADIUS*3+DOT_RADIUS*2, j*DOT_RADIUS*3+DOT_RADIUS*2, DOT_RADIUS*2, DOT_RADIUS*2);
				p.setBorder(createBorderDots(DOT_COLORS[(int)(Math.random()*5)], DOT_RADIUS));
				p.setOpaque(false);
				bgPanel.add(p);
				p.addMouseMotionListener(new MouseMotionAdapter(){

					@Override
					public void mouseMoved(MouseEvent e) {
						// TODO Auto-generated method stub
						if (isDragged) {
							for (Point point : connectedDots) {
								dots[point.x][point.y].setBounds(e.getX(), e.getY(), DOT_RADIUS*2, DOT_RADIUS*2);
							}
							
						}
						System.out.println("move p");
					}
					@Override
					public void mouseDragged(MouseEvent e) {
						// TODO Auto-generated method stub
						for (Point point : connectedDots) {
							dots[point.x][point.y].setBounds(e.getX(), e.getY(), DOT_RADIUS*2, DOT_RADIUS*2);
						}
						System.out.println("drag p");
					}		
				});
				p.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						connectedDots.clear();
						isDragged = false;
						System.out.println("cleared");
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						connectedDots.add(new Point(x,y));
						isDragged = true;
						System.out.println(e.getPoint() + " total dots:"+connectedDots.size());
					}
					
					
					@Override
					public void mouseEntered(MouseEvent e) {
						if (!connectedDots.contains(new Point(x,y)) && isDragged) {
							connectedDots.add(new Point(x, y));							
						}
						System.out.println(e.getPoint() + " enter"+ " total dots:"+connectedDots.size());
					}
					
				});

			}	
		}
		
		
		add(bgPanel);
		
//		bgPanel.addMouseMotionListener(new MouseAdapter() {
//			
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				// TODO Auto-generated method stub
//				if (isDragged) {
//					for (Point point : connectedDots) {
//						dots[point.x][point.y].setBounds(e.getX(), e.getY(), DOT_RADIUS*2, DOT_RADIUS*2);
//					}
//					
//				}
//				System.out.println("move bg");
//			}
//			@Override
//			public void mouseDragged(MouseEvent e) {
//				// TODO Auto-generated method stub
//				for (Point point : connectedDots) {
//					dots[point.x][point.y].setBounds(e.getX(), e.getY(), DOT_RADIUS*2, DOT_RADIUS*2);
//				}
//				System.out.println("drag bg");
//			}					
//			
//		});
	}
	
	public static void main(String[] args) {
		new MainProgram().setVisible(true);
	}

}

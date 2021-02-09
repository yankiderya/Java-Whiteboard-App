import java.awt.Graphics;


import javax.swing.JPanel;

public class PaintPanelServer extends JPanel {

	public void paint(Graphics g) {
		super.paint(g);
		
			for(int i=0; i<Server.shapes.size(); i++) {
				if(Server.shapes.get(i).type == 1) {
					g.setColor(Server.shapes.get(i).color);
					g.fillRect(Server.shapes.get(i).x, Server.shapes.get(i).y, 150, 150);
					
					
				}
				else if (Server.shapes.get(i).type == 3) {
					g.setColor(Server.shapes.get(i).color);
					g.drawLine(Server.shapes.get(i).x, Server.shapes.get(i).y, Server.shapes.get(i).lineEndX, Server.shapes.get(i).lineEndY);
				}
				else if(Server.shapes.get(i).type == 2) {
					g.setColor(Server.shapes.get(i).color);
					g.fillOval(Server.shapes.get(i).x-75, Server.shapes.get(i).y-75, 150, 150);
					
				}
			}
	
			
	}
}

	


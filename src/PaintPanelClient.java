import java.awt.Graphics;



import javax.swing.JPanel;

public class PaintPanelClient extends JPanel {

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for(int i=0; i<Client.shapes.size(); i++) {
			if(Client.shapes.get(i).type == 1) {
				g.setColor(Client.shapes.get(i).color);
				g.fillRect(Client.shapes.get(i).x, Client.shapes.get(i).y, 150, 150);

				
			}
			else if(Client.shapes.get(i).type == 2) {
				g.setColor(Client.shapes.get(i).color);
				g.fillOval(Client.shapes.get(i).x-75, Client.shapes.get(i).y-75, 150, 150);
			}
			else if (Client.shapes.get(i).type == 3) {
				g.setColor(Client.shapes.get(i).color);
				g.drawLine(Client.shapes.get(i).x, Client.shapes.get(i).y, Client.shapes.get(i).lineEndX, Client.shapes.get(i).lineEndY);
			}
				
				
					
				
			}
	
			
	}
}

	


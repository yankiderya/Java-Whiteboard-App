import javax.swing.JFrame;

public class Client_Main {

	public static void main(String[] args) throws ClassNotFoundException{
		Client mclient = new Client("127.168.0.1");
		mclient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mclient.setVisible(true);
		mclient.setSize(900, 800);
		mclient.runClient();

	}	

}

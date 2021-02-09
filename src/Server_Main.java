import javax.swing.JFrame;

public class Server_Main
{

	public static void main(String[] args) {
		Server mserver = new Server();
		mserver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mserver.setVisible(true);
		mserver.setSize(900, 800);
		mserver.runServer();
		

	}

}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client extends JFrame {
	BorderLayout bl;
	GridLayout gl;
	GridLayout glNorth;
	protected static Color c;
	protected static int x;
	protected static int y;
	protected static int eX;
	protected static int eY;


	protected static int counter;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String srv;
	private String name = " ";
	private String teacherName = " ";
	private Socket myClient;
	public static int type;
	public static boolean timeStart = false;
	public static boolean counterStop = false;




	public static ArrayList<PointsClient> shapes = new ArrayList<PointsClient>();

	public Client(String info) {
		super("Paint App");
		srv = info;
		bl = new BorderLayout();
		gl = new GridLayout(2, 1);
		glNorth = new GridLayout(1, 2);
		setLayout(bl);
		center();
		east();
		setResizable(false);

	}

	JPanel jpCenter;
	JPanel jpEast = new JPanel();
	JPanel jpNorth;
	JTextArea jtxtArea = new JTextArea();
	JTextArea jtxtAreaAttend;


	JLabel lblAttendance = new JLabel("Attendance");
	JLabel lblChat = new JLabel("ChatBox");
	Icon sendIcon = new ImageIcon("send.png");
	JButton jbtnChat = new JButton(sendIcon);
	JButton jbtnShow = new JButton("Show List");
	JButton jbtnClose = new JButton("Hide List");
	Icon icon = new ImageIcon("raise.png");
	JButton jbtnRaise = new JButton(icon);
	JPanel jpAttendance = new JPanel();
	JPanel jpChatBox = new JPanel();
	JTextField txtfChat = new JTextField();
	JPanel jpNorth2 = new JPanel();
	JLabel lblShapeCounter = new JLabel();

	public void UserName() {

		name = JOptionPane.showInputDialog(this, "Enter Name");

		while (name == null || (name != null && ("".equals(name)))) {
			if (name == null) {
				System.exit(0);
			}
			JOptionPane.showMessageDialog(this, "Please Enter You Name!");
			name = JOptionPane.showInputDialog(this, "Enter Name");
		}

	}

	public void center() {
		jpCenter = new PaintPanelClient();
		jpCenter.setBackground(Color.white);
		add(jpCenter, BorderLayout.CENTER);
	}

	public void east() {
		jpEast.setLayout(gl);
		jpEast.setBackground(Color.white);
		jpEast.setBorder(BorderFactory.createLineBorder(Color.black));
		jpEast.setPreferredSize(new Dimension(300, 200));
		add(jpEast, BorderLayout.EAST);
		jpAttendance.setLayout(null);
		jpAttendance.setBackground(Color.white);
		jpChatBox.setLayout(null);
		jpChatBox.setBackground(Color.decode("#128C7E"));
		lblAttendance.setBounds(0, 0, 300, 50);
		lblAttendance.setFont(new Font("Serif", Font.BOLD, 20));
		lblAttendance.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
		lblChat.setBounds(0, 0, 300, 50);
		lblChat.setFont(new Font("Serif", Font.BOLD, 20));
		lblChat.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
		lblChat.setForeground(Color.white);
		jbtnChat.setBounds(220, 320, 80, 30);
		jbtnChat.setFont(new Font("Serif", Font.BOLD, 20));
		jbtnChat.setEnabled(false);
		jbtnChat.setBackground(Color.decode("#128C7E"));
		jbtnShow.setBounds(0, 52, 100, 50);
		jbtnShow.setFont(new Font("Serif", Font.BOLD, 15));
		jbtnShow.setBackground(Color.black);
		jbtnShow.setForeground(Color.white);
		jbtnClose.setBounds(100, 52, 110, 50);
		jbtnClose.setFont(new Font("Serif", Font.BOLD, 15));
		jbtnClose.setBackground(Color.black);
		jbtnClose.setForeground(Color.white);
		txtfChat.setBounds(0, 320, 220, 30);
		JScrollPane sp = new JScrollPane(jtxtArea);
		sp.setBounds(1, 50, 300, 250);
		jtxtArea.setEditable(false);
		jtxtArea.setBackground(Color.white);
		jtxtAreaAttend = new JTextArea();
		jtxtAreaAttend.setEditable(false);
		jtxtAreaAttend.setBounds(1, 105, 300, 200);
		jtxtAreaAttend.setFont(new Font("Serif", Font.BOLD, 20));

		jpChatBox.add(txtfChat);
		jpChatBox.add(jbtnChat);
		jpChatBox.add(lblChat);
		jpChatBox.add(sp);
		jpAttendance.add(lblAttendance);
		jpAttendance.add(jbtnShow);
		jpAttendance.add(jbtnClose);
		jpAttendance.add(jtxtAreaAttend);
		jpEast.add(jpAttendance);
		jpEast.add(jpChatBox);
		jbtnChat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtfChat.getText().equals("")) {
					send(txtfChat.getText());
					txtfChat.setText("");
				}

			}
		});
		jbtnShow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jbtnShow.setBackground(Color.decode("#128C7E"));
				jtxtAreaAttend.setText("Username: " + name);

			}
		});
		jbtnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jtxtAreaAttend.setText(" ");
				jbtnShow.setBackground(Color.black);

			}
		});
		jbtnRaise.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				send("Raise");

			}
		});
		txtfChat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!txtfChat.getText().equals("")) {
					send(txtfChat.getText());
					txtfChat.setText("");
				}

			}
		});
	}

	public void north() {
		jpNorth = new CounterClient();
		jpNorth.setLayout(glNorth);
		jpNorth.setPreferredSize(new Dimension(50, 50));
		CounterClient counterr = new CounterClient();
		counterr.label.setBounds(10, 10, 100, 100);
		jpNorth2.setLayout(null);
		lblShapeCounter.setBounds(0, -20, 400, 100);
		lblShapeCounter.setFont(new Font("Serif", Font.BOLD, 20));
		lblShapeCounter.setText("Shapes Drawn:" + Integer.toString(counter));
		jbtnRaise.setBounds(280, 10, 50, 35);

		jpNorth.add(counterr.label);
		jpNorth.add(jpNorth2);
		jpNorth2.add(lblShapeCounter);
		jpNorth2.add(jbtnRaise);
		add(jpNorth, BorderLayout.NORTH);

	}

	public void runClient() throws ClassNotFoundException{
		try {
			UserName();
			connection();
			north();
			streams();
			receive();

		} catch (EOFException e) {
			showMessage("\nClient Terminated Conn\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			endConnection();
		}

	}

	private void connection() throws IOException {
		
		try {
			myClient = new Socket(InetAddress.getByName(srv), 9000);
			JOptionPane.showMessageDialog(this, "Connected");
		}
		catch (ConnectException e){
			JOptionPane.showMessageDialog(this, "The class is not started yet!");
			System.exit(0);
		}

		timeStart = true;

	}

	private void streams() throws IOException {

		oos = new ObjectOutputStream(myClient.getOutputStream());

		oos.flush();

		ois = new ObjectInputStream(myClient.getInputStream());

		
		send("Username:" + name);
	}

	private void receive() throws IOException{
		
		setButtonEnabled(true);
		String message = "";

		while (true) {
			PointsClient p = new PointsClient();
			try {
				message = (String) ois.readObject();

				if (message.length() == 14 && message.equals("S:Open Chatbox")) {
					jpChatBox.setVisible(true);
				}

				else if (message.length() > 11 && message.substring(2, 11).equals("Username:")) {
					teacherName = message.substring(11, message.length());
					send("Hello "+teacherName+" hocam");
					

				}

				else if (message.length() > 11 && !message.substring(2, 11).equals("Username:")
						&& !message.substring(2, 8).equals("Color:")) {
					showMessage(message);
				} else if (message.length() == 10 && message.equals("S:Rejected")) {
					JOptionPane.showMessageDialog(this, "Rejected");
				}
				else if (message.length() == 8 && message.equals("S:!Close")) {

					jpChatBox.setVisible(false);

				} else if (message.length() == 8 && message.equals("S:Expand")) {
					jpEast.setVisible(true);
				} else if (message.length() == 8 && message.equals("S:Narrow")) {
					jpEast.setVisible(false);
				}

				else if (message.length() == 6 && message.substring(2, message.length()).equals("Stop")) {
					counterStop = true;
				} else if (message.length() == 7 && message.substring(2, message.length()).equals("Clear")) {
					shapes.clear();
					counter = 0;
					lblShapeCounter.setText("Shapes Drawn:" + Integer.toString(counter));
					repaint();
				}

				else if (message.length() > 4 && message.substring(2, 4).equals("X:")) {
					x = Integer.parseInt(message.substring(4, message.length()));

				} else if (message.length() > 4 && message.substring(2, 4).equals("Y:")) {
					y = Integer.parseInt(message.substring(4, message.length()));
				} else if (message.length() > 5 && message.substring(2, 5).equals("eX:")) {
					eX = Integer.parseInt(message.substring(5, message.length()));
				} else if (message.length() > 5 && message.substring(2, 5).equals("eY:")) {
					eY = Integer.parseInt(message.substring(5, message.length()));
				}

				else if (message.length() > 8 && message.substring(2, 8).equals("Color:")) {
					c = new Color(Integer.parseInt(message.substring(8, message.length())));
				} else if (message.length() > 7 && message.substring(2, 7).equals("Type:")) {
					type = Integer.parseInt(message.substring(7, message.length()));
					if (type == 1 || type == 2) {
						p.y = y;
						p.x = x;
						p.color = c;
						p.type = type;
						shapes.add(p);
						repaint();
						counter++;
						lblShapeCounter.setText("Shapes Drawn:" + Integer.toString(counter));
					} else if (type ==3) {
						p.y = y;
						p.x = x;
						p.lineEndY = eY;
						p.lineEndX = eX;
						p.color = c;
						p.type = type;
						shapes.add(p);
						repaint();
						counter++;
						lblShapeCounter.setText("Shapes Drawn:" + Integer.toString(counter));
					}

				} else {
					showMessage(message);
				}

			} catch (ClassNotFoundException e) {
				showMessage("Unknown");
			}
			
		}
	}

	private void endConnection() {
		showMessage("\nConnection is Over!\n");
		setButtonEnabled(false);

		try {
			oos.close();
			ois.close();
			myClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void send(String text) {

		try {
			
			oos.writeObject("C:" + text);
			oos.flush();
			if (text.length() >= 9 && !text.substring(0, 9).equals("Username:")) {
				showMessage("C:" + text);
			} else if (text.length() <= 9 && !text.equals("Raise")) {
				showMessage("C:" + text);
			}

		} catch (IOException e) {
			jtxtArea.append("\nError");
		}
	}

	private void showMessage(final String string) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				if (string.substring(0, 2).equals("C:")) {
					String change = string.substring(2, string.length());
					jtxtArea.append("\n" + name + ":" + change);
				}
				else if (string.substring(0, 2).equals("S:")) {
					String change = string.substring(2, string.length());
					jtxtArea.append("\n" + teacherName + ":" + change);
				} else {
					if (name.equals(" ") && teacherName.equals(" ")) {
						jtxtArea.append(string);	
					}
					else {
						jtxtArea.append(string);
					}
			}
			}
		});
	}

	private void setButtonEnabled(final boolean bool) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				jbtnChat.setEnabled(bool);

			}
		});
	}

}

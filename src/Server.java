import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Server extends JFrame implements ActionListener, KeyListener, MouseListener {

	BorderLayout bl;
	GridLayout gl;
	GridLayout glNorth;
	FlowLayout fl;
	JTextArea jtxtArea;
	JTextArea jtxtAreaAttend;
	protected static Color c;
	protected static Color chosenColor;
	protected static int x;
	protected static int y;
	protected static int counter;


	public static boolean s_flag = false;
	public static boolean l_flag = false;
	public static boolean c_flag = false;
	public static boolean flag = false;
	public static boolean timeStart = false;
	public static boolean counterStop = false;



	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ServerSocket serverSocket;
	private Socket conn;
	private String name = " ";
	private String myName = " ";

	public static ArrayList<PointsServer> shapes = new ArrayList<PointsServer>();

	public Server() {
		super("Paint App");
		bl = new BorderLayout();
		gl = new GridLayout(2, 1);
		glNorth = new GridLayout(1, 2);
		setLayout(bl);
		menu();
		center();
		east();
		setResizable(false);

	}

	JMenuBar jmb;
	JMenu color_m, shape_m, timeStop_m, clearBoard_m;
	JMenuItem color_sbm, square_sbm, line_sbm, circle_sbm, timeStop_sbm, clearBoard_sbm;

	public void menu() {
		jmb = new JMenuBar();

		color_m = new JMenu("Color");
		shape_m = new JMenu("Shape");
		timeStop_m = new JMenu("End Lesson");
		clearBoard_m = new JMenu("Clear Board");

		color_sbm = new JMenuItem("Color Chooser");
		color_sbm.addActionListener(this);

		timeStop_sbm = new JMenuItem("End");
		timeStop_sbm.addActionListener(this);

		clearBoard_sbm = new JMenuItem("Clear");
		clearBoard_sbm.addActionListener(this);

		square_sbm = new JMenuItem("Square");
		square_sbm.addActionListener(this);

		line_sbm = new JMenuItem("Line");
		line_sbm.addActionListener(this);

		circle_sbm = new JMenuItem("Circle");
		circle_sbm.addActionListener(this);

		addKeyListener(this);

		color_m.add(color_sbm);
		shape_m.add(square_sbm);
		shape_m.add(line_sbm);
		shape_m.add(circle_sbm);
		timeStop_m.add(timeStop_sbm);
		clearBoard_m.add(clearBoard_sbm);

		jmb.add(color_m);
		jmb.add(shape_m);
		jmb.add(timeStop_m);
		jmb.add(clearBoard_m);

		add(jmb);
		setJMenuBar(jmb);
		shape_m.setEnabled(false);
		timeStop_m.setEnabled(false);
	}

	JPanel jpCenter;
	JPanel jpEast = new JPanel();
	JPanel jpNorth;

	JLabel lblAttendance = new JLabel("Attendance");
	JLabel lblChat = new JLabel("ChatBox");
	Icon icon = new ImageIcon("send.png");
	JButton jbtnChat = new JButton(icon);
	JButton jbtnShow = new JButton("Show List");
	JButton jbtnClose = new JButton("Hide List");
	JCheckBox checkBox = new JCheckBox("Open/Close");
	JPanel jpAttendance = new JPanel();
	JPanel jpChatBox = new JPanel();
	JTextField txtfChat = new JTextField();
	JPanel jpNorth2 = new JPanel();
	JLabel lblShapeCounter = new JLabel();


	public void UserName() {

		myName = JOptionPane.showInputDialog(this, "Enter Name");
		while (myName == null || (myName != null && ("".equals(myName)))) {
			if (myName == null) {
				System.exit(0);
			}
			JOptionPane.showMessageDialog(this, "Please Enter You Name!");
			myName = JOptionPane.showInputDialog(this, "Enter Name");// or you can throw exception
		}

	}

	public void center() {
		jpCenter = new PaintPanelServer();
		jpCenter.addMouseListener(this);
		jpCenter.setBackground(Color.white);
		add(jpCenter, BorderLayout.CENTER);
	}

	public void east() {
		jpEast.setLayout(gl);
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
		lblChat.setForeground(Color.white);
		lblChat.setFont(new Font("Serif", Font.BOLD, 20));
		lblChat.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));

		jbtnChat.setBounds(220, 310, 80, 30);
		jbtnChat.setBackground(Color.decode("#128C7E"));
		jbtnChat.setFont(new Font("Serif", Font.BOLD, 20));
		jbtnChat.setEnabled(false);

		jbtnShow.setBounds(0, 52, 100, 50);
		jbtnShow.setFont(new Font("Serif", Font.BOLD, 15));
		jbtnShow.setBackground(Color.black);
		jbtnShow.setForeground(Color.white);

		jbtnClose.setBounds(100, 52, 110, 50);
		jbtnClose.setFont(new Font("Serif", Font.BOLD, 15));
		jbtnClose.setBackground(Color.black);
		jbtnClose.setForeground(Color.white);

		txtfChat.setBounds(0, 310, 220, 30);

		jtxtArea = new JTextArea();
		jtxtArea.setEditable(false);
		jtxtArea.setBackground(Color.white);

		jtxtAreaAttend = new JTextArea();
		jtxtAreaAttend.setEditable(false);
		jtxtAreaAttend.setBounds(1, 105, 300, 200);
		jtxtAreaAttend.setFont(new Font("Serif", Font.BOLD, 20));

		JScrollPane sp = new JScrollPane(jtxtArea);
		sp.setBounds(1, 50, 300, 250);

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
				String data = "";
				try {
					data = new String(Files.readAllBytes(Paths.get("attendanceList.txt")));
					jtxtAreaAttend.setText("Username: " + data);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		jbtnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				jtxtAreaAttend.setText(" ");
				jbtnShow.setBackground(Color.black);

			}
		});
		txtfChat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (!txtfChat.getText().equals("")) {
					send(txtfChat.getText());
					txtfChat.setText("");
				}

			}
		});
		checkBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					jpEast.setVisible(false);
					send("Narrow");
				} else {
					jpEast.setVisible(true);
					send("Expand");
				}
			}
		});

	}

	public void north() {
		CounterServer c = new CounterServer();
		jpNorth = new CounterServer();
		jpNorth.setLayout(glNorth);
		jpNorth.setPreferredSize(new Dimension(50, 50));

		jpNorth2.setLayout(null);

		lblShapeCounter.setBounds(0, -20, 400, 100);
		lblShapeCounter.setFont(new Font("Serif", Font.BOLD, 20));
		lblShapeCounter.setText("Shapes Drawn:" + Integer.toString(counter));

		checkBox.setBounds(150, -20, 400, 100);
		checkBox.setFont(new Font("Serif", Font.BOLD, 20));

		jpNorth.add(c.label);
		jpNorth.add(jpNorth2);
		jpNorth2.add(lblShapeCounter);
		jpNorth2.add(checkBox);
		add(jpNorth, BorderLayout.NORTH);
		timeStop_m.setEnabled(true);


	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			PointsServer p = new PointsServer();

			if (true == s_flag) {

				p.x = e.getX();
				p.y = e.getY();
				p.type = 1;
				p.color = c;

				shapes.add(p);
				String a = Integer.toString(p.x);
				String b = Integer.toString(p.y);
				String type = Integer.toString(p.type);
				String colorName = Integer.toString(c.getRGB());
				send("X:" + a);
				send("Y:" + b);
				send("Color:" + colorName);
				send("Type:" + type);

				repaint();
				counter++;
				lblShapeCounter.setText("Shapes Drawn:" + Integer.toString(counter));
			} else if (c_flag == true) {

				p.x = e.getX();
				p.y = e.getY();
				p.type = 2;
				p.color = c;
				shapes.add(p);

				String a = Integer.toString(p.x);
				String b = Integer.toString(p.y);
				String type = Integer.toString(p.type);
				String colorName = Integer.toString(c.getRGB());
				send("X:" + a);
				send("Y:" + b);
				send("Color:" + colorName);
				send("Type:" + type);

				repaint();
				counter++;
				lblShapeCounter.setText("Shapes Drawn:" + Integer.toString(counter));
			} else if (l_flag == true) {

				if (flag == false) {

					x = e.getX();
					y = e.getY();
					flag = true;
				} else if (flag == true) {
					p.lineEndX = e.getX();
					p.lineEndY = e.getY();
					p.x = x;
					p.y = y;
					p.type = 3;
					p.color = c;
					shapes.add(p);
					String a = Integer.toString(p.x);
					String b = Integer.toString(p.y);
					String ea = Integer.toString(p.lineEndX);
					String eb = Integer.toString(p.lineEndY);
					String type = Integer.toString(p.type);
					String colorName = Integer.toString(c.getRGB());
					send("X:" + a);
					send("Y:" + b);
					send("eX:" + ea);
					send("eY:" + eb);
					send("Color:" + colorName);
					send("Type:" + type);

					flag = false;
					repaint();
					counter++;
					lblShapeCounter.setText("Shapes Drawn:" + Integer.toString(counter));
				}

			}

		}

	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == color_sbm) {
			c = JColorChooser.showDialog(this, "Choose Color", chosenColor);
			shape_m.setEnabled(true);

		} else if (e.getSource() == square_sbm) {
			l_flag = false;
			c_flag = false;
			s_flag = true;

		} else if (e.getSource() == circle_sbm) {
			l_flag = false;
			c_flag = true;
			s_flag = false;

		} else if (e.getSource() == line_sbm) {
			l_flag = true;
			c_flag = false;
			s_flag = false;

		} else if (e.getSource() == timeStop_sbm) {
			counterStop = true;
			send("Stop");
		} else if (e.getSource() == clearBoard_sbm) {
			shapes.clear();
			counter = 0;
			lblShapeCounter.setText("Shapes Drawn:" + Integer.toString(counter));
			repaint();
			send("Clear");

		}
	}

	public void runServer() {
		try {
			serverSocket = new ServerSocket(9000, 100);

			while (true) {
				try {
					UserName();
					connection();
					north();
					streams();
					receive();

				} catch (EOFException e) {
					showMessage("\nServer Terminated Conn\n");
				} finally {
					endConnection();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void connection() throws IOException {
		showMessage("Please Wait...\n");
		conn = serverSocket.accept();

		timeStart = true;

	}

	private void streams() throws IOException {
		oos = new ObjectOutputStream(conn.getOutputStream());
		oos.flush();

		ois = new ObjectInputStream(conn.getInputStream());
		send("Username:" + myName);


	}

	private void receive() throws IOException {

		setButtonEnabled(true);
		String message = "";


		while (true) {


			try {
				message = (String) ois.readObject();
				if (message.length() > 11 && message.substring(2, 11).equals("Username:")) {
					name = message.substring(11, message.length());
					PrintWriter out = new PrintWriter("attendanceList.txt");
					out.println(name);
					out.close();
					showMessage(name+" has entered the room\n");
					send("Hello "+name);
				}

				if (message.length() > 11 && !message.substring(2, 11).equals("Username:")) {
					showMessage(message);
				} else if (message.length() <= 11 && !message.equals("C:Raise")) {
					showMessage(message);
				} else if (message.equals("C:Raise")) {
						//addFiveSec=true;
					int confirm = JOptionPane.showConfirmDialog(this, name + " Raises Hand!\n Let him/her speak?");
					if (confirm == 0) {
						send("Open Chatbox");
					} else if (confirm == 1 || confirm == 2) {
						send("Rejected");
					}

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
			conn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void send(String text) {

		try {
			oos.writeObject("S:" + text);
			oos.flush();

			if (text.length() == 12 && text.equals("Open Chatbox")) {
				System.out.println();
			}

			else if (text.length() > 9 && !text.substring(0, 9).equals("Username:")
					&& !text.substring(0, 6).equals("Color:")) {
				showMessage("S:" + text);
			} else if (text.length() > 9 && text.substring(0, 9).equals("Username:")) {
				System.out.println();
			} else if (text.length() == 8 && text.equals("Rejected")) {
				System.out.println();
			}

			else if (text.length() > 6) {
				if (!text.substring(0, 2).equals("X:") && !text.substring(0, 2).equals("Y:")
						&& !text.substring(0, 3).equals("eX:") && !text.substring(0, 3).equals("eY:")
						&& !text.substring(0, 6).equals("Color:") && !text.substring(0, 5).equals("Type:")) {
					showMessage("S:" + text);
				}
			} else if (text.length() == 6
					&& (text.equals("!Close") || text.equals("Expand") || text.equals("Narrow"))) {
				System.out.println();
			} else if (text.length() == 4 && text.equals("Stop")) {
				System.out.println();
			} else if (text.length() == 5 && text.equals("Clear")) {
				System.out.println();
			} else if (text.length() > 5) {
				if (!text.substring(0, 2).equals("X:") && !text.substring(0, 2).equals("Y:")
						&& !text.substring(0, 3).equals("eX:") && !text.substring(0, 3).equals("eY:")
						&& !text.substring(0, 5).equals("Type:")) {

					showMessage("S:" + text);
				}
			} else if (text.length() > 3) {
				if (!text.substring(0, 2).equals("X:") && !text.substring(0, 2).equals("Y:")
						&& !text.substring(0, 3).equals("eX:") && !text.substring(0, 3).equals("eY:")) {

					showMessage("S:" + text);
				}
			} else if (text.length() > 2) {
				if (!text.substring(0, 2).equals("X:") && !text.substring(0, 2).equals("Y:")) {

					showMessage("S:" + text);
				}
			} else {
				showMessage("S:" + text);
			}

		} catch (IOException e) {
			jtxtArea.append("\nError");
		}
	}

	private void showMessage(final String string) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (string.substring(0, 2).equals("S:")) {
					String change = string.substring(2, string.length());
					jtxtArea.append("\n" + myName + ":" + change);
				}
				else if (string.substring(0, 2).equals("C:")) {
					String change = string.substring(2, string.length());
					jtxtArea.append("\n" + name + ":" + change);
				} else {
					if (name.equals(" ") && myName.equals(" ")) {
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

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}




	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}

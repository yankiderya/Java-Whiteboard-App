import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

public class CounterServer extends JPanel {

	JLabel label;
	Timer timer;
	Timer timer2;
	int count = 3600;
	int minute = count / 60;
	int seconds = count - (60 * minute);
	int countDown = 5;



	public static boolean flag = false;

	public CounterServer() {

		label = new JLabel("...");
		label.setFont(new Font("Serif", Font.BOLD, 20));

		if (Server.timeStart == true) {

			timer = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if (!Server.counterStop == false) {
						count = 0;
					}
					if (count > 0) {

						if (count % 60 == 0) {
							minute--;
							seconds = 59;
						}

						label.setText("Remaining Time: " + Integer.toString(minute) + ":" + Integer.toString(seconds));
						count--;
						seconds--;





					} else {
						((Timer) (e.getSource())).stop();

					}


				}

			});

			timer.setInitialDelay(0);
			timer.start();
			//s.send("Restart");
			timer2 = new Timer(1000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (count == 0) {
						if (countDown > 0) {
							label.setFont(new Font("Serif", Font.BOLD, 25));
							label.setForeground(Color.red);
							label.setText("Lesson will end in " + Integer.toString(countDown) + " seconds");
							countDown--;
						} else {
							((Timer) e.getSource()).stop();
							label.setFont(new Font("Serif", Font.BOLD, 25));
							label.setForeground(Color.red);
							label.setText("END OF THE LESSON ");
							if (flag == false) {
								Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
								int centerX = screenSize.width / 2;
								int centerY = screenSize.height / 2;
								JFrame jf = new JFrame();
								jf.setSize(500, 500);
								jf.add(new AnimationServer());
								jf.setLocation(centerX - (centerX / 2), centerY - (centerY / 2));
								jf.setVisible(true);

								flag = true;
							}

						}

					}
				}
			});
			timer2.setInitialDelay(0);
			timer2.start();

		}

	}

}

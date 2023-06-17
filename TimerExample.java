import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimerExample extends JFrame {
    private JLabel timerLabel = new JLabel("0");

    private int counter = 1800;
    private Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            counter--;
            timerLabel.setText(Integer.toString(counter));
        }
    });

    public TimerExample() {
        setTitle("Time Left");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);

        timerLabel.setFont(new Font("Arial", Font.BOLD, 50));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().add(timerLabel, BorderLayout.CENTER);
        setVisible(true);
        timer.start();
    }
}

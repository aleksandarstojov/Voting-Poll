import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * This class represents the object client for a distributed
 * object of class Hello, which implements the remote interface
 * HelloInterface.
 * 
 * @author M. L. Liu
 */

public class Client extends JFrame implements ActionListener {

   private JLabel label;
   private JRadioButton yesButton, noButton, dontCareButton;
   private JButton voteButton, registerButton, countButton;
   String voteChoice;
   String voteDisplay;
   private ServerInterface p;
   private ServerImpl s;
   private ClientInterface callbackObj;
   static String callBackMessage;

   public Client() {
      try {
         String hostName = "localhost";
         int portNum = 1234;
         String registryURL = "rmi://" + hostName + ":" + portNum + "/project";
         // find the remote object and cast it to an interface object
         p = (ServerInterface) Naming.lookup(registryURL);
         System.out.println("Lookup completed. ");
         callbackObj = new ClientImpl();
      } // end try
      catch (Exception e) {
         System.out.println("Exception in ProjectClient: " + e);
      }

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JPanel panel = new JPanel(new GridLayout(1, 2));
      label = new JLabel("Please vote:");
      panel.add(label);

      yesButton = new JRadioButton("yes");
      noButton = new JRadioButton("no");
      dontCareButton = new JRadioButton("dont care");
      ButtonGroup buttonGroup = new ButtonGroup();
      buttonGroup.add(yesButton);
      buttonGroup.add(noButton);
      buttonGroup.add(dontCareButton);
      panel.add(yesButton);
      panel.add(noButton);
      panel.add(dontCareButton);

      voteButton = new JButton("Submit vote");
      voteButton.addActionListener(this);
      panel.add(voteButton);

      registerButton = new JButton("Notify me");
      registerButton.addActionListener(this);
      panel.add(registerButton);

      countButton = new JButton("View vote count");
      countButton.addActionListener(this);
      panel.add(countButton);

      add(panel);
      pack();
      setLocationRelativeTo(null);
      setVisible(true);
   } // end Client()

   @Override
   public void actionPerformed(ActionEvent e) {

      // if vote button is pressed, submit the vote and update vote count
      if (e.getSource() == voteButton) {
         if (yesButton.isSelected()) {
            try {
               voteChoice = "yes";
               p.vote(voteChoice);
               JOptionPane.showMessageDialog(this, "You voted Yes!");
               int yes = p.getYesCount();
               if (yes == 2) {
                  p.doCallbacks();
               }
            } catch (Exception re) {
               System.out.println("Exception while submitting a vote: " + re);
            }
         } else if (noButton.isSelected()) {
            try {
               voteChoice = "no";
               p.vote(voteChoice);
               JOptionPane.showMessageDialog(this, "You voted No!");
            } catch (Exception re) {
               System.out.println("Exception while submitting a vote: " + re);
            }
         } else if (dontCareButton.isSelected()) {
            try {
               voteChoice = "dont care";
               p.vote(voteChoice);
               JOptionPane.showMessageDialog(this, "You voted Don't care!");
            } catch (Exception re) {
               System.out.println("Exception while submitting a vote: " + re);
            }
         } else {
            JOptionPane.showMessageDialog(this, "Please select an option!");
         }
      } // end if

      // if countButton is pressed display the current vote count
      if (e.getSource() == countButton) {
         try {
            voteDisplay = p.checkStatus();
            JOptionPane.showMessageDialog(this, voteDisplay);
         } catch (Exception re) {
            System.out.println("Exception while displaying vote count: " + re);
         }
      }

      // if registerButton is pressed, register the client for a callback
      if (e.getSource() == registerButton) {
         try {
            p.registerCallback(callbackObj);
            JOptionPane.showMessageDialog(this, "You will be notified once there are 100 yes votes.");

         } catch (Exception re) {
            System.out.println("Exception while displaying vote count: " + re);
         }
      }
   } // end actionPerformed()

   public static void main(String args[]) {
      new Client();
   } // end main
} // end class

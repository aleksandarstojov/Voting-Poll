import java.rmi.*;
import java.rmi.server.*;
import java.util.Vector;

/**
 * This class implements the remote interface SomeInterface.
 */

public class ServerImpl extends UnicastRemoteObject
      implements ServerInterface {

   static int yesCount = 0;
   static int noCount = 0;
   static int dontCareCount = 0;
   static Vector<ClientInterface> clientList;

   public ServerImpl() throws RemoteException {
      super();
      clientList = new Vector<ClientInterface>();
   }

   public synchronized void vote(String vote) throws RemoteException {
      switch (vote) {
         case "yes":
            yesCount++;
            break;
         case "no":
            noCount++;
            break;
         case "dont care":
            dontCareCount++;
            break;
         default:
            throw new IllegalArgumentException("Invalid choice");
      } // end switch
   } // end vote

   public synchronized String checkStatus() throws RemoteException {
      String message = ("Current vote count:\n" +
            "1) Yes: " + getYesCount() + "\n" +
            "2) No: " + getNoCount() + "\n" +
            "3) I don't care: " + getIDCCount() + "\n");
      return message;
   } // end checkStatus

   public synchronized void registerCallback(ClientInterface callbackClient)
         throws java.rmi.RemoteException {
      // store the callback object into the vector
      if (!(clientList.contains(callbackClient))) {
         clientList.addElement(callbackClient);
         System.out.println("Registered new client.");
      } // end if
   } // end registerCallback

   public synchronized void doCallbacks()
         throws java.rmi.RemoteException {
      // make callback to each registered client
      System.out.println("Callbacks initiated ---");
      for (int i = 0; i < clientList.size(); i++) {
         System.out.println("doing " + i + ". callback\n");
         // convert the vector object to a callback object
         ClientInterface nextClient = (ClientInterface) clientList.elementAt(i);
         // invoke the callback method
         nextClient.notifyMe();
      } // end for
      System.out.println("Server completed callbacks.");
   } // end doCallbacks

   public synchronized int getYesCount() throws RemoteException {
      return yesCount;
   }

   public synchronized int getNoCount() throws RemoteException {
      return noCount;
   }

   public synchronized int getIDCCount() throws RemoteException {
      return dontCareCount;
   }
} // end class

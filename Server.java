import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the object server for a distributed
 * object of class ServerImpl, which implements the remote
 * interface ServerInterface.
 */

public class Server {
   public static void main(String args[]) {
      int portNum = 1234;
      String registryURL;
      try {
         // code for obtaining RMI port number value omitted
         ServerImpl exportedObj = new ServerImpl();
         startRegistry(portNum);
         // register the object under the name "some"
         registryURL = "rmi://localhost:1234/project";
         Naming.rebind(registryURL, exportedObj);
         System.out.println("Server registered.  Registry currently contains:");
         /**/ // list names currently in the registry
         /**/ listRegistry(registryURL);
         System.out.println("Poll Server is ready.");
         //display time left once the server starts
         TimerExample timerExample = new TimerExample();
         // Create a Timer object
         Timer timer = new Timer();
         // Schedule a TimerTask to be executed after 30 minutes
         timer.schedule(new TimerTask() {
            public void run() {
               System.out.println("30 minutes have elapsed.");
               // Perform any actions that need to be taken after 30 minutes here
               System.exit(0);
            }
         }, 30 * 60 * 1000); // 30 minutes * 60 seconds * 1000 milliseconds
      } // end try
      catch (Exception re) {
         System.out.println(
               "Exception in Server.main: " + re);
      } // end catch
   } // end main

   // This method starts a RMI registry on the local host, if it
   // does not already exist at the specified port number.
   private static void startRegistry(int RMIPortNum)
         throws RemoteException {
      try {
         Registry registry = LocateRegistry.getRegistry(RMIPortNum);
         registry.list();
         // The above call will throw an exception
         // if the registry does not already exist
      } catch (RemoteException ex) {
         // No valid registry at that port.
         System.out.println(
               "RMI registry cannot be located at port "
                     + RMIPortNum);
         Registry registry = LocateRegistry.createRegistry(RMIPortNum);
         System.out.println(
               "RMI registry created at port " + RMIPortNum);
      }
   } // end startRegistry

   // This method lists the names registered with a Registry object
   private static void listRegistry(String registryURL)
         throws RemoteException, MalformedURLException {
      System.out.println("Registry " + registryURL + " contains: ");
      String[] names = Naming.list(registryURL);
      for (int i = 0; i < names.length; i++)
         System.out.println(names[i]);
   } // end listRegistry
} // end class

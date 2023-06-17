// file: SomeInterface.java 
// to be implemented by a remote object.

import java.rmi.*;

public interface ServerInterface extends Remote {

   public void vote(String vote)
         throws java.rmi.RemoteException;

   // shows the current voteCount
   public String checkStatus()
         throws java.rmi.RemoteException;

   // This remote method allows an object client to
   // register for callback
   // @param callbackClientObject is a reference to the
   // object of the client; to be used by the server
   // to make its callbacks.
   public void registerCallback(ClientInterface callbackObject)
         throws java.rmi.RemoteException;

   // This remote method notifies the client
   // that there are 100 yes votes
   public void doCallbacks()
         throws java.rmi.RemoteException;

   public int getYesCount()
         throws RemoteException;

   public int getNoCount()
         throws RemoteException;

   public int getIDCCount()
         throws RemoteException;

} // end interface

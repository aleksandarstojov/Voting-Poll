import java.rmi.*;
import java.rmi.server.*;

/**
 * This class implements the remote interface
 * ClientInterface.
 */

public class ClientImpl extends UnicastRemoteObject
        implements ClientInterface {

    public ClientImpl() throws RemoteException {
        super();
    }

    public String notifyMe() {
        String returnMessage = "There are now 100 yes votes. ";
        System.out.println(returnMessage);
        return returnMessage;
    }
}
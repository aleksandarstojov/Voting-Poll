import java.rmi.*;

public interface ClientInterface
        extends Remote {

    // This remote method is invoked by a server
    // to make a callback to an client which
    // implements this interface.

    public String notifyMe()
            throws java.rmi.RemoteException;
}
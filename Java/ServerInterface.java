import java.rmi.*;
public interface ServerInterface extends Remote 
{
	public void sendData(String data[][],String clgname) throws RemoteException;
	public boolean isResultReady() throws RemoteException;
	public String getTop5() throws RemoteException;
}

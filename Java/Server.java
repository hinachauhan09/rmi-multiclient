import java.net.*;
import java.rmi.*;
public class Server 
{
	public static void main(String args[]) 
	{
		Thread t;
        try
        {
            new Thread().start();
			ServerImpl impl = new ServerImpl();
			Naming.rebind("ServerImpl", impl);
		}
		catch(Exception e) 
		{
			System.out.println("Exception: " + e);
		}
	}
}

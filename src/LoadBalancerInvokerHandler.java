import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

// Generated code
import loadbalancerinvoker.*;
import loadbalancer.*;

import java.io.*;

public class LoadBalancerInvokerHandler implements LoadBalancerInvoker.Iface
{

  private Server server;

  public LoadBalancerInvokerHandler(Server server) {
    this.server = server;
  }

  public void offLoad ()
  {
    System.out.println ("offLoad()");
    String str = Utils.removeTail (new File (server.getFileName()), 10);
    //String str = Utils.removeTail (server.getFileName());
    System.out.println (str);

    sendToSecondaryServer (str);
  }

  // client to server connection to invoke load() method
  private void sendToSecondaryServer (String str)
  {
    try
    {
      TTransport transport;
      transport = new TSocket ("localhost", server.getPeerPortNum());
      transport.open ();

      TProtocol protocol = new TBinaryProtocol (transport);
      LoadBalancer.Client client = new LoadBalancer.Client (protocol);

      client.load (str);

      transport.close ();
    } catch (TException x)
    {
      x.printStackTrace ();
    }
  }

}

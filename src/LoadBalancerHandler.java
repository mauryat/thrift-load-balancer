import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

// Generated code
import loadbalancer.*;
import shared.*;

import java.io.*;

public class LoadBalancerHandler implements LoadBalancer.Iface
{

  public void load (String str)
  {
    System.out.println ("load()");
    System.out.println (Server.fileName);
    System.out.println (Server.portNum);

    System.out.println (str);

    // append to b.txt
    try
    {
      String dest = "b.txt";

      FileWriter fstream = new FileWriter (dest, true);
      BufferedWriter out = new BufferedWriter (fstream);

      out.write (str);

      out.close ();
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }

  }

  public SharedStruct getStruct (int key)
  {
    System.out.println ("getStruct(" + key + ")");
    return null;
  }

}

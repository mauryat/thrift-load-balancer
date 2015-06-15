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
    String str = removeTail (new File (server.getFileName()), 10);
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

  public static String removeTail (File file, int lines)
  {
    java.io.RandomAccessFile fileHandler = null;
    try
    {
      fileHandler = new java.io.RandomAccessFile (file, "rw");
      long fileLength = fileHandler.length () - 1;
      StringBuilder sb = new StringBuilder ();
      int line = 0;

      for (long filePointer = fileLength; filePointer != -1; filePointer--)
        {
          fileHandler.seek (filePointer);
          int readByte = fileHandler.readByte ();

          if (readByte == 0xA)
            {
              if (filePointer < fileLength)
                {
                  line = line + 1;
                }
            }
          else if (readByte == 0xD)
            {
              if (filePointer < fileLength - 1)
                {
                  line = line + 1;
                }
            }
          if (line >= lines)
            {
              break;
            }
          sb.append ((char) readByte);
        }

      String str = sb.toString();

      // truncate the file
      fileHandler.setLength (fileLength - str.length());

      return str;
    }
    catch (java.io.FileNotFoundException e)
    {
      e.printStackTrace ();
      return null;
    }
    catch (java.io.IOException e)
    {
      e.printStackTrace ();
      return null;
    }
    finally
    {
      if (fileHandler != null)
        try
        {
          fileHandler.close ();
        }
      catch (IOException e)
      {
      }
    }
  }

}

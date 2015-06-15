import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

// Generated code
import loadbalancerinvoker.*;
import loadbalancer.*;
import shared.*;

import java.io.*;

public class LoadBalancerInvokerHandler implements LoadBalancerInvoker.Iface
{

  public void offLoad ()
  {
    System.out.println ("offLoad()");
    System.out.println (Server.fileName);
    System.out.println (Server.portNum);
    String str = tail2 (new File (Server.fileName), 10);
    System.out.println (str);

    sendToBackupServer (str);
  }

  // client to server connection to invoke load() method
  private void sendToBackupServer (String str)
  {
    try
    {
      TTransport transport;
      transport = new TSocket ("localhost", Server.peerPortNum);
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

  public static String tail2 (File file, int lines)
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

  public SharedStruct getStruct (int key)
  {
    System.out.println ("getStruct(" + key + ")");
    return null;
  }

}

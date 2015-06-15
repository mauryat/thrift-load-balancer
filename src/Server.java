import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

// Generated code
import loadbalancerinvoker.*;
import loadbalancer.*;

public class Server
{

  private String fileName;
  private int portNum;
  private int peerPortNum;
  private boolean isSecondary;

  public static LoadBalancerInvokerHandler invokerHandler;
  public static LoadBalancerHandler handler;

  public static LoadBalancerInvoker.Processor invokerProcessor;
  public static LoadBalancer.Processor processor;

  Server (String fileName, int portNum, int peerPortNum, boolean isSecondary) {
    this.fileName = fileName;
    this.portNum = portNum;
    this.peerPortNum = peerPortNum;
    this.isSecondary = isSecondary;
  }

  public static void main (String[] args)
  {
    try
    {
      final Server server = new Server(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]));
      Runnable simple = null;

      if (!server.isSecondary) {
        invokerHandler = new LoadBalancerInvokerHandler (server);
	invokerProcessor = new LoadBalancerInvoker.Processor (invokerHandler);


	simple = new Runnable () {
	  public void run () {
	    simplePrimaryServer (invokerProcessor, server.portNum);
          }
	};
      } else {
        handler = new LoadBalancerHandler (server);
	processor = new LoadBalancer.Processor (handler);

        simple = new Runnable () {
          public void run () {
            simpleSecondaryServer (processor, server.portNum);
          }
	};
      }

      new Thread (simple).start ();
    } catch (Exception x) {
        x.printStackTrace ();
    }
  }

public static void simplePrimaryServer (LoadBalancerInvoker.Processor processor, int portNum) {
  try {
    TServerTransport serverTransport = new TServerSocket (portNum);
    TServer server = new TSimpleServer (new Args (serverTransport).processor (processor));

    System.out.println ("Starting the simple server...");
    server.serve ();
  } catch (Exception e) {
    e.printStackTrace ();
  }
}

public static void simpleSecondaryServer (LoadBalancer.Processor processor, int portNum) {
  try {
    TServerTransport serverTransport = new TServerSocket (portNum);
    TServer server = new TSimpleServer (new Args (serverTransport).processor (processor));

    System.out.println ("Starting the simple server...");
    server.serve ();
  } catch (Exception e) {
    e.printStackTrace ();
  }
}

  String getFileName() {
    return fileName;
  }

  int getPeerPortNum() {
    return peerPortNum;
  }
}

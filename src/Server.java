import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

// Generated code
import loadbalancer.*;
import shared.*;

public class Server {

  public static String fileName;
  public static int portNum;
  public static int peerPortNum;

  public static LoadBalancerHandler handler;

  public static LoadBalancer.Processor processor;

  public static void main(String [] args) {
    try {
      fileName = args[0]; // write validation code here
      portNum = Integer.parseInt(args[1]);
      peerPortNum = Integer.parseInt(args[2]);

      handler = new LoadBalancerHandler();
      processor = new LoadBalancer.Processor(handler);

      Runnable simple = new Runnable() {
        public void run() {
          simple(processor, portNum);
        }
      };      

      new Thread(simple).start();
    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  public static void simple(LoadBalancer.Processor processor, int portNum) {
    try {
      TServerTransport serverTransport = new TServerSocket(portNum);
      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

      System.out.println("Starting the simple server...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

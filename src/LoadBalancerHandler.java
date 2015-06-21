import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

// Generated code
import loadbalancer.*;

public class LoadBalancerHandler implements LoadBalancer.Iface
{

	private Server server;

	public LoadBalancerHandler (Server server) {
		this.server = server;
	}

	public void load (String str)
	{
		System.out.println ("load()");

		System.out.println (str);

		Utils.prependStringToFile(str, server.getFileName());
	}

}


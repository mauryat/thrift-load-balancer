import org.apache.thrift.TException;

// Generated code
import loadbalancer.*;
import shared.*;

public class LoadBalancerHandler implements LoadBalancer.Iface {

  public void invokeLoadBalancer() {
    System.out.println("invokeLoadBalancer()");
  }

  public SharedStruct getStruct(int key) {
    System.out.println("getStruct(" + key + ")");
    return null;
  }

}


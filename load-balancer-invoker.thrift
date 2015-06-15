include "shared.thrift"

namespace java loadbalancerinvoker 

exception InvalidOperation {
  1: i32 what,
  2: string why
}

service LoadBalancerInvoker extends shared.SharedService {

   void offLoad(), // any exception?

}


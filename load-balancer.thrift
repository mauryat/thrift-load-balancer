include "shared.thrift"

namespace java loadbalancer 

exception InvalidOperation {
  1: i32 what,
  2: string why
}

service LoadBalancer extends shared.SharedService {

   void offLoad(), // any exception?

   void load(1:string str)

}

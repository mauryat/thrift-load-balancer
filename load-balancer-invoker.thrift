namespace java loadbalancerinvoker 

exception InvalidOperation {
  1: i32 what,
  2: string why
}

service LoadBalancerInvoker {

   void offLoad(), // any exception?

}


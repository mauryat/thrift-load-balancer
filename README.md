# thrift-load-balancer

How to build?
1. Edit build.xml. On line 8, edit the path location for the property "thrift.home".
2. Simply run the command "ant" from the top directory.

How to run?
1. Run the following in 3 different terminal windows/tabs
prompt:thrift-load-balancer/build$ java -cp ../thrift-0.9.2/lib/java/build/libthrift-0.9.2.jar:.:../thrift-0.9.2/lib/java/build/lib/* Server a.txt 9090 9091 false

prompt:thrift-load-balancer/build$ java -cp ../thrift-0.9.2/lib/java/build/libthrift-0.9.2.jar:.:../thrift-0.9.2/lib/java/build/lib/* Server b.txt 9091 9090 true

prompt:thrift-load-balancer/build$ java -cp ../thrift-0.9.2/lib/java/build/libthrift-0.9.2.jar:.:../thrift-0.9.2/lib/java/build/lib/* Client


namespace java com.lpp.thrift.hello

service  IHelloWorldService {
  string sayHello(1:string username)
}
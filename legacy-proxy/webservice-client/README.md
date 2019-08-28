# webservice-client
Using Camel to call the legacy webservice as a client. 
* webservice-spring-boot is the java first example of CXF to expose the Hello Service.
* webservice-camel-route is the camel application which call the Hello Service in a process.


# How to run it
1. Start the webservice which is listen to http://localhost:8080/Service/Hello, you can find the wsdl information http://localhost:8080/Service/Hello?wsdl
```
  cd webservice-spring-boot
  mvn spring-boot:run
```

2. Start the camel route which call the services with a timer 
```
  cd webserivce-camel-route
  mvn spring-boot:run
```

3. Check the log message 
* In webservice-camel-route you can see these messages
```
2019-08-28 16:08:03.811  INFO 4383 --- [           main] o.a.c.w.s.f.ReflectionServiceFactoryBean : Creating Service {http://service.ws.sample/}HelloService from class sample.ws.service.Hello
2019-08-28 16:08:03.899  INFO 4383 --- [           main] o.a.camel.spring.SpringCamelContext      : Route: route1 started and consuming from: timer://foo?fixedRate=true&period=60000
2019-08-28 16:08:03.900  INFO 4383 --- [           main] o.a.camel.spring.SpringCamelContext      : Total 1 routes, of which 1 are started
2019-08-28 16:08:03.901  INFO 4383 --- [           main] o.a.camel.spring.SpringCamelContext      : Apache Camel 2.24.1 (CamelContext: camel-1) started in 0.356 seconds
2019-08-28 16:08:03.903  INFO 4383 --- [           main] com.example.camel.demo.DemoApplication   : Started DemoApplication in 2.624 seconds (JVM running for 5.977)
2019-08-28 16:08:05.194  INFO 4383 --- [2 - timer://foo] route1                                   : Get the session id user1:pass
2019-08-28 16:08:05.234  INFO 4383 --- [ult-workqueue-1] route1                                   : Get the response Hello, Welcome to CXF Spring boot Willem!!! with sayHello!
2019-08-28 16:08:05.245  INFO 4383 --- [ult-workqueue-1] route1                                   : Called the logout method!

```

* In webservice-spring-boot you can see these messages
```
INFO: Started SampleWsApplication in 2.603 seconds (JVM running for 8.809)
Aug 28, 2019 4:08:05 PM sample.ws.service.HelloPortImpl login
INFO: Calling the login Service with user1,pass
Aug 28, 2019 4:08:05 PM sample.ws.service.HelloPortImpl sayHello
INFO: Executing operation sayHelloWillem
Aug 28, 2019 4:08:05 PM sample.ws.service.HelloPortImpl logout
INFO: Session user1:pass calling the logout
```

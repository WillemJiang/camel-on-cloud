# camel-servlet-service
This demo shows how to setup customer camel-servlet component for creating a http service in a separated camel context.
* ServletRegistry: It creates an undertow server for publishing the CamelServlet
* ServiceController: It exposes a RESTful service for starting and stop the camel context 


# How to run it
1. There are two port will be use in this demo
* Controller 
``` 
    # create a new camel context to load the route
    curl http://localhost:8080/camel/start 
    # shutdown the camel context
    curl http://localhost:8080/camel/stop
```      
* Services
``` 
   # Access the camel route which is exposed in the data panel.
   curl http://localhost:9000/service/test
```    

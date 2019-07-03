# wsdl2rest-proxy
restroute exposes restful services by wrapping the old legacy webservices(Which is Apache CXF wsdl_first example).

# How to run it
1. start the webservice which is listen to http://localhost:9090/CustomerServicePort
```
  cd webservice
  mvn -Pserver
```

2. start the restroute which provides restful services at http://localhost:8080
```
  cd restroute
  mvn spring-boot:run
```

3. send getCustomersByName request
```
  curl http://localhost:8080/customersbyname/Willem
```     

4. updateCustomer
```
  curl -d '{"customerId": 10,"name": "Willem", "address": ["Pine Street 200"],"numOrders": 1,"revenue": 10000,"test": 1.5,"birthDate": 1233417600000,"type": "BUSINESS"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/customer
```  

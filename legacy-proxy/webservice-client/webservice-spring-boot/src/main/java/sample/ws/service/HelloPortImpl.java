/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package sample.ws.service;

import java.util.logging.Logger;


@javax.jws.WebService(serviceName = "HelloService", portName = "HelloPort",
                    targetNamespace = "http://service.ws.sample/",
                    endpointInterface = "sample.ws.service.Hello")
public class HelloPortImpl implements Hello {

    private static final Logger LOG = Logger.getLogger(HelloPortImpl.class.getName());

    public java.lang.String sayHello(java.lang.String myname) {
        LOG.info("Executing operation sayHello " + myname);
        try {
            return "Hello, Welcome to CXF Spring boot " + myname + "!!!";

        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String login(String username, String password) {
        LOG.info("Calling the login Service with " + username + "," + password);
        return username + ":" + password;
    }

    @Override
    public String sayHello(String sessionId, String myName) {
        if (sessionId != null) {
            LOG.info("Executing operation sayHello" + myName);
            return "Hello, Welcome to CXF Spring boot " + myName + "!!!";
        } else {
            return "Please login first!";
        }
    }

    @Override
    public void logout(String sessionId) {
        LOG.info("Session " + sessionId +  " calling the logout");
    }
}

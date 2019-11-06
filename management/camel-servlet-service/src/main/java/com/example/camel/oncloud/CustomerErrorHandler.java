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

package com.example.camel.oncloud;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.server.handler.ErrorHandler;

public class CustomerErrorHandler extends ErrorHandler {

  // You can setup the customer ErrorHandler by overriding protected method in Jetty
  protected void writeErrorPageBody(HttpServletRequest request, Writer writer, int code, String message, boolean showStacks)
      throws IOException {
    writer.write("<p>My Customer Error </p>");
    super.writeErrorPageBody(request, writer, code, message, showStacks);
  }
}
  

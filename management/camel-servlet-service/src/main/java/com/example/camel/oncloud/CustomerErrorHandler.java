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
  

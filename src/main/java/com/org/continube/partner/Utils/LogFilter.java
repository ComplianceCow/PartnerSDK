package com.org.continube.partner.Utils;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

public class LogFilter implements ClientRequestFilter, ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext reqContext) throws IOException {
        System.out.println("-- Client request info --");
  
        log(reqContext.getUri(), reqContext.getHeaders());
  
    }
  
    @Override
    public void filter(ClientRequestContext reqContext,
                       ClientResponseContext resContext) throws IOException {
        System.out.println("-- Client response info --");
        log(reqContext.getUri(), resContext.getHeaders());
    }
  
    private void log(URI uri, MultivaluedMap<String, ?> headers) {
        System.out.println("Request URI: " + uri.getPath());
        System.out.println("Headers:");
        headers.entrySet().forEach(h -> System.out.println(h.getKey() + ": " + h.getValue()));
    }
  }
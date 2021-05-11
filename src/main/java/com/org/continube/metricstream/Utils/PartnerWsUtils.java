package com.org.continube.metricstream.Utils;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;

import com.org.continube.metricstream.Exception.PartnerException;

import org.glassfish.jersey.media.multipart.MultiPart;

public class PartnerWsUtils {

    private static WebTarget webTarget;

    public static void setResourceName(String url,Client client) {
        webTarget = client.target(url);
    }

    public static Response post(Form credientialFrm) {
        if (webTarget == null) {
            throw new PartnerException("webTarget is not Initializied. call setResourceName() method ");
        }
        Response response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.form(credientialFrm));
        return response;
    }

    public static Response get(MultivaluedMap<String, Object> headers) {
        if (webTarget == null) {
            throw new PartnerException("webTarget is not Initializied. call setResourceName() method ");
        }
        Response response = webTarget.request(MediaType.APPLICATION_JSON).headers(headers).get();
        return response;
    }

    public static Response post(String content,MultivaluedMap<String, Object> headers) {
        if (webTarget == null) {
            throw new PartnerException("webTarget is not Initializied. call setResourceName() method ");
        }
        Response response = webTarget.request(MediaType.APPLICATION_JSON).headers(headers)
                .post(Entity.entity(content, MediaType.APPLICATION_JSON));

        return response;
    }

    public static Response put(String content,MultivaluedMap<String, Object> headers) {
        if (webTarget == null) {
            throw new PartnerException("webTarget is not Initializied. call setResourceName() method ");
        }
        Response response = webTarget.request(MediaType.APPLICATION_JSON).headers(headers)
                .put(Entity.entity(content, MediaType.APPLICATION_JSON));

        return response;
    }

    public static Response PostMultiPart(String token, MultiPart multiPart) {
        if (webTarget == null) {
            throw new PartnerException("webTarget is not Initializied. call setResourceName() method ");
        }
        Response response = webTarget.request().header("Authorization", token)
                .post(Entity.entity(multiPart, multiPart.getMediaType()));
        return response;
    }

}

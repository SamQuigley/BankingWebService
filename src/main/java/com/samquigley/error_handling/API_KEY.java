package com.samquigley.error_handling;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class API_KEY implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {

        MultivaluedMap<String, String> headers = requestContext.getHeaders();

        List<String> apiKeys = headers.get("API_KEY");

        if (apiKeys == null || apiKeys.isEmpty() || !apiKeys.get(0).equals("VALID_KEY")) {
            requestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("You are not authorised to access this resource.")
                    .build());
        }
    }
}

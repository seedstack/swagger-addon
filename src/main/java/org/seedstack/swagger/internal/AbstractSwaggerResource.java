/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import io.swagger.config.FilterFactory;
import io.swagger.core.filter.SpecFilter;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.models.Swagger;

import javax.inject.Inject;
import javax.ws.rs.core.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSwaggerResource {

    @Inject
    private Swagger swagger;

    protected Response.ResponseBuilder getListing(HttpHeaders headers, UriInfo uriInfo) {
        if (swagger != null) {
            swagger = getFilteredSwagger(headers, uriInfo, swagger);
            Object serializedSwagger = serializeSwagger(swagger);
            return Response.ok().entity(serializedSwagger);
        } else {
            return Response.status(404);
        }
    }

    private Swagger getFilteredSwagger(HttpHeaders headers, UriInfo uriInfo, Swagger swagger) {
        SwaggerSpecFilter filterImpl = FilterFactory.getFilter();
        if (filterImpl != null) {
            SpecFilter f = new SpecFilter();
            swagger = f.filter(swagger,
                    filterImpl,
                    getQueryParams(uriInfo.getQueryParameters()),
                    getCookies(headers),
                    getHeaders(headers));
        }
        return swagger;
    }

    protected Object serializeSwagger(Swagger swagger) {
        return swagger;
    }

    private Map<String, List<String>> getQueryParams(MultivaluedMap<String, String> params) {
        Map<String, List<String>> output = new HashMap<String, List<String>>();
        if (params != null) {
            for (String key : params.keySet()) {
                List<String> values = params.get(key);
                output.put(key, values);
            }
        }
        return output;
    }

    private Map<String, String> getCookies(HttpHeaders headers) {
        Map<String, String> output = new HashMap<String, String>();
        if (headers != null) {
            for (String key : headers.getCookies().keySet()) {
                Cookie cookie = headers.getCookies().get(key);
                output.put(key, cookie.getValue());
            }
        }
        return output;
    }


    private Map<String, List<String>> getHeaders(HttpHeaders headers) {
        Map<String, List<String>> output = new HashMap<String, List<String>>();
        if (headers != null) {
            for (String key : headers.getRequestHeaders().keySet()) {
                List<String> values = headers.getRequestHeaders().get(key);
                output.put(key, values);
            }
        }
        return output;
    }
}

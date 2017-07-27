/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import com.google.common.collect.Lists;
import io.swagger.config.FilterFactory;
import io.swagger.core.filter.SpecFilter;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSwaggerResource {

    @Inject
    private Swagger swagger;

    protected Object getListing(HttpHeaders headers, UriInfo uriInfo) {
        addRequestInfoToSwagger(uriInfo);
        if (swagger != null) {
            swagger = getFilteredSwagger(headers, uriInfo, swagger);
            return serializeSwagger(swagger);
        } else {
            throw new NotFoundException();
        }
    }

    protected void addRequestInfoToSwagger(UriInfo uriInfo) {
        if (swagger.getSchemes() == null || swagger.getSchemes().isEmpty()) {
            swagger.setSchemes(Lists.newArrayList(Scheme.forValue(uriInfo.getBaseUri().getScheme())));
        }
        if (swagger.getHost() == null || swagger.getHost().equals("")) {
            swagger.setHost(uriInfo.getBaseUri().getHost() + ":" + uriInfo.getBaseUri().getPort());
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
        Map<String, List<String>> output = new HashMap<>();
        if (params != null) {
            output.putAll(params);
        }
        return output;
    }

    private Map<String, String> getCookies(HttpHeaders headers) {
        Map<String, String> output = new HashMap<>();
        if (headers != null) {
            for (String key : headers.getCookies().keySet()) {
                Cookie cookie = headers.getCookies().get(key);
                output.put(key, cookie.getValue());
            }
        }
        return output;
    }


    private Map<String, List<String>> getHeaders(HttpHeaders headers) {
        Map<String, List<String>> output = new HashMap<>();
        if (headers != null) {
            for (String key : headers.getRequestHeaders().keySet()) {
                List<String> values = headers.getRequestHeaders().get(key);
                output.put(key, values);
            }
        }
        return output;
    }
}

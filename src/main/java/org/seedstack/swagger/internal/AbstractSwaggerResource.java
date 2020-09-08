/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import com.google.common.collect.Lists;
import io.swagger.core.filter.SpecFilter;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

public abstract class AbstractSwaggerResource {
    @Inject
    private Provider<SwaggerSpecFilter> swaggerSpecFilterProvider;
    @Inject
    private Swagger swagger;

    protected Swagger getSwagger(HttpHeaders headers, UriInfo uriInfo) {
        Swagger filteredSwagger = filterSwagger(headers, uriInfo);
        addRequestInfoToSwagger(filteredSwagger, uriInfo);
        return filteredSwagger;
    }

    private Swagger filterSwagger(HttpHeaders headers, UriInfo uriInfo) {
        SwaggerSpecFilter swaggerSpecFilter = swaggerSpecFilterProvider.get();
        if (swaggerSpecFilter != null) {
            SpecFilter f = new SpecFilter();
            swagger = f.filter(swagger,
                    swaggerSpecFilter,
                    getQueryParams(uriInfo),
                    getCookies(headers),
                    getHeaders(headers));
        }
        return swagger;
    }

    private void addRequestInfoToSwagger(Swagger swagger, UriInfo uriInfo) {
        if (swagger.getSchemes() == null || swagger.getSchemes().isEmpty()) {
            swagger.setSchemes(Lists.newArrayList(Scheme.forValue(uriInfo.getBaseUri().getScheme())));
        }
        if (swagger.getHost() == null || swagger.getHost().equals("")) {
            if (uriInfo.getBaseUri().getPort() > 0) {
                swagger.setHost(uriInfo.getBaseUri().getHost() + ":" + uriInfo.getBaseUri().getPort());
            } else {
                swagger.setHost(uriInfo.getBaseUri().getHost());
            }
        }
    }

    private Map<String, List<String>> getQueryParams(UriInfo uriInfo) {
        Map<String, List<String>> output = new HashMap<>();
        MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();
        if (queryParameters != null) {
            output.putAll(queryParameters);
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

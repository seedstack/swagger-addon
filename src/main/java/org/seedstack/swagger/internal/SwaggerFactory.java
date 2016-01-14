/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import io.swagger.config.FilterFactory;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.*;
import org.seedstack.seed.SeedException;

import java.util.Collection;
import java.util.HashSet;

public class SwaggerFactory {

    public Swagger createSwagger(SwaggerConfiguration config, Collection<Class<?>> resources) {
        Swagger swagger = new Swagger();

        swagger.info(BuildInfo(config))
                .host(config.getHost())
                .basePath(config.getBasePath());

        for (String scheme : config.getSchemes()) {
            swagger.scheme(Scheme.forValue(scheme));
        }

        registerSwaggerSpecFilter(config);

        SwaggerSerializers.setPrettyPrint(config.isPrettyPrint());

        Reader reader = new Reader(swagger);
        swagger = reader.read(new HashSet<Class<?>>(resources));

        return swagger;
    }

    private Info BuildInfo(SwaggerConfiguration config) {
        Info info = new Info().title(config.getTitle())
                .version(config.getVersion())
                .description(config.getDescription())
                .termsOfService(config.getTermsOfServiceUrl());

        info.contact(buildContact(config));
        info.license(buildLicense(config));
        return info;
    }

    private Contact buildContact(SwaggerConfiguration config) {
        if (config.getContactName() != null || config.getContactEmail() != null || config.getContactUrl() != null) {
            return new Contact().name(config.getContactName())
                    .email(config.getContactEmail()).url(config.getContactUrl());
        }
        return null;
    }

    private License buildLicense(SwaggerConfiguration config) {
        if (config.getLicenseName() != null || config.getLicenseUrl() != null) {
            return new License().name(config.getLicenseName()).url(config.getLicenseUrl());
        }
        return null;
    }

    private void registerSwaggerSpecFilter(SwaggerConfiguration config) {
        if (config.getFilterClass() != null) {
            try {
                SwaggerSpecFilter filter = (SwaggerSpecFilter) Class.forName(config.getFilterClass()).newInstance();
                if (filter != null) {
                    FilterFactory.setFilter(filter);
                }
            } catch (Exception e) {
                throw SeedException.wrap(e, SwaggerErrorCodes.FAIL_TO_LOAD_FILTER);
            }
        }
    }
}

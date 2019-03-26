/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import static com.google.common.base.Strings.isNullOrEmpty;

import io.swagger.jaxrs.Reader;
import io.swagger.models.Contact;
import io.swagger.models.Info;
import io.swagger.models.License;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import java.util.Collection;
import java.util.HashSet;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletContext;
import org.seedstack.seed.Application;
import org.seedstack.seed.rest.RestConfig;
import org.seedstack.swagger.SwaggerConfig;

class SwaggerProvider implements Provider<Swagger> {
    private final Collection<Class<?>> swaggerClasses;
    private final ServletContext servletContext;
    @Inject
    private Application application;

    SwaggerProvider(Collection<Class<?>> swaggerClasses, ServletContext servletContext) {
        this.swaggerClasses = swaggerClasses;
        this.servletContext = servletContext;
    }

    @Override
    public Swagger get() {
        SwaggerConfig swaggerConfig = getSwaggerConfig();
        Swagger swagger = new Swagger();

        // Set the base info
        swagger.info(buildInfo(swaggerConfig))
                .host(swaggerConfig.getHost())
                .basePath(swaggerConfig.getBasePath());

        for (String scheme : swaggerConfig.getSchemes()) {
            swagger.scheme(Scheme.forValue(scheme));
        }

        // Read swagger-annotated classes
        swagger = new Reader(swagger).read(new HashSet<>(swaggerClasses));

        return swagger;
    }

    private SwaggerConfig getSwaggerConfig() {
        SwaggerConfig swaggerConfig = application.getConfiguration().get(SwaggerConfig.class);
        RestConfig restConfig = application.getConfiguration().get(RestConfig.class);

        if (isNullOrEmpty(swaggerConfig.getBasePath())) {
            String basePath = buildPath(servletContext == null ? "" : servletContext.getContextPath(),
                    restConfig.getPath());
            if (!isNullOrEmpty(basePath)) {
                swaggerConfig.setBasePath(basePath);
            }
        }
        if (isNullOrEmpty(swaggerConfig.getTitle())) {
            swaggerConfig.setTitle(application.getName());
        }
        if (isNullOrEmpty(swaggerConfig.getVersion())) {
            swaggerConfig.setVersion(application.getVersion());
        }

        return swaggerConfig;
    }

    private Info buildInfo(SwaggerConfig config) {
        return new Info()
                .title(config.getTitle())
                .version(config.getVersion())
                .description(config.getDescription())
                .termsOfService(config.getTermsOfServiceUrl())
                .contact(buildContact(config))
                .license(buildLicense(config));
    }

    private Contact buildContact(SwaggerConfig config) {
        if (config.getContactName() != null || config.getContactEmail() != null || config.getContactUrl() != null) {
            return new Contact().name(config.getContactName())
                    .email(config.getContactEmail()).url(config.getContactUrl());
        }
        return null;
    }

    private License buildLicense(SwaggerConfig config) {
        if (config.getLicenseName() != null || config.getLicenseUrl() != null) {
            return new License().name(config.getLicenseName()).url(config.getLicenseUrl());
        }
        return null;
    }

    private static String buildPath(String first, String... parts) {
        StringBuilder result = new StringBuilder(first);

        if (parts != null) {
            for (String part : parts) {
                if (result.length() == 0) {
                    if (part.startsWith("/")) {
                        result.append(part.substring(1));
                    } else {
                        result.append(part);
                    }
                } else {
                    if (result.toString().endsWith("/") && part.startsWith("/")) {
                        result.append(part.substring(1));
                    } else if (!result.toString().endsWith("/") && !part.startsWith("/") && !part.isEmpty()) {
                        result.append("/").append(part);
                    } else {
                        result.append(part);
                    }
                }
            }
        }

        return result.toString();
    }
}

/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import io.swagger.config.FilterFactory;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class SwaggerFactoryTest {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String VERSION = "1.0.0";
    public static final String TERMS_OF_SERVICE_URL = "http://mycompany.com/terms-of-service";
    public static final String CONTACT = "John Doe";
    public static final String CONTACT_EMAIL = "contact@mycompany.com";
    public static final String CONTACT_URL = "mycompany.com/contact";
    public static final String LICENSE = "MPL v2";
    public static final String LICENSE_URL = "https://www.mozilla.org/en-US/MPL/2.0/";
    public static final String HOST = "host";
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String BASE_PATH = "/";
    public static final String FILTER_CLASS = "org.seedstack.swagger.internal.MyFilter";
    private SwaggerFactory underTest = new SwaggerFactory();

    @Test
    public void testCreateSwaggerWithEmptyConfig() {
        Swagger swagger = underTest.createSwagger(new SwaggerConfiguration(), new ArrayList<Class<?>>());
        assertThat(swagger).isNotNull();
    }

    @Test
    public void testCreateSwagger() {
        SwaggerConfiguration config = new SwaggerConfiguration();
        config.setTitle(TITLE);
        config.setDescription(DESCRIPTION);
        config.setVersion(VERSION);
        config.setTermsOfServiceUrl(TERMS_OF_SERVICE_URL);
        config.setContactName(CONTACT);
        config.setContactEmail(CONTACT_EMAIL);
        config.setContactUrl(CONTACT_URL);
        config.setLicenseName(LICENSE);
        config.setLicenseUrl(LICENSE_URL);
        config.setHost(HOST);
        config.setSchemes(HTTP, HTTPS);
        config.setBasePath(BASE_PATH);

        Swagger swagger = underTest.createSwagger(config, new ArrayList<Class<?>>());

        assertThat(swagger).isNotNull();
        assertThat(swagger.getInfo().getTitle()).isEqualTo(TITLE);
        assertThat(swagger.getInfo().getDescription()).isEqualTo(DESCRIPTION);
        assertThat(swagger.getInfo().getVersion()).isEqualTo(VERSION);
        assertThat(swagger.getInfo().getTermsOfService()).isEqualTo(TERMS_OF_SERVICE_URL);

        assertThat(swagger.getInfo().getContact()).isNotNull();
        assertThat(swagger.getInfo().getContact().getName()).isEqualTo(CONTACT);
        assertThat(swagger.getInfo().getContact().getEmail()).isEqualTo(CONTACT_EMAIL);
        assertThat(swagger.getInfo().getContact().getUrl()).isEqualTo(CONTACT_URL);

        assertThat(swagger.getInfo().getLicense()).isNotNull();
        assertThat(swagger.getInfo().getLicense().getName()).isEqualTo(LICENSE);
        assertThat(swagger.getInfo().getLicense().getUrl()).isEqualTo(LICENSE_URL);

        assertThat(swagger.getHost()).isEqualTo(HOST);
        assertThat(swagger.getSchemes()).containsOnly(Scheme.HTTP, Scheme.HTTPS);
        assertThat(swagger.getSchemes()).containsOnly(Scheme.HTTP, Scheme.HTTPS);
    }

    @Test
    public void testFilterFactory() {
        SwaggerConfiguration config = new SwaggerConfiguration();
        config.setFilterClass(FILTER_CLASS);

        underTest.createSwagger(config, new ArrayList<Class<?>>());

        assertThat(FilterFactory.getFilter()).isInstanceOf(MyFilter.class);
    }
}

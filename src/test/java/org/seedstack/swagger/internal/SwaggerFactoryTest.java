/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import static org.assertj.core.api.Assertions.assertThat;

import io.swagger.config.FilterFactory;
import io.swagger.models.Scheme;
import io.swagger.models.Swagger;
import java.util.ArrayList;
import org.junit.Test;
import org.seedstack.swagger.SwaggerConfig;

public class SwaggerFactoryTest {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String VERSION = "1.0.0";
    private static final String TERMS_OF_SERVICE_URL = "http://mycompany.com/terms-of-service";
    private static final String CONTACT = "John Doe";
    private static final String CONTACT_EMAIL = "contact@mycompany.com";
    private static final String CONTACT_URL = "mycompany.com/contact";
    private static final String LICENSE = "MPL v2";
    private static final String LICENSE_URL = "https://www.mozilla.org/en-US/MPL/2.0/";
    private static final String HOST = "host";
    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static final String BASE_PATH = "/";
    private static final Class<MyFilter> FILTER_CLASS = MyFilter.class;
    private SwaggerFactory underTest = new SwaggerFactory();

    @Test
    public void testCreateSwaggerWithEmptyConfig() {
        Swagger swagger = underTest.createSwagger(new SwaggerConfig(), new ArrayList<>());
        assertThat(swagger).isNotNull();
    }

    @Test
    public void testCreateSwagger() {
        SwaggerConfig config = new SwaggerConfig()
                .setTitle(TITLE)
                .setDescription(DESCRIPTION)
                .setVersion(VERSION)
                .setTermsOfServiceUrl(TERMS_OF_SERVICE_URL)
                .setContactName(CONTACT)
                .setContactEmail(CONTACT_EMAIL)
                .setContactUrl(CONTACT_URL)
                .setLicenseName(LICENSE)
                .setLicenseUrl(LICENSE_URL)
                .setHost(HOST)
                .addScheme(HTTP)
                .addScheme(HTTPS)
                .setBasePath(BASE_PATH);

        Swagger swagger = underTest.createSwagger(config, new ArrayList<>());

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
        SwaggerConfig config = new SwaggerConfig();
        config.setFilterClass(FILTER_CLASS);

        underTest.createSwagger(config, new ArrayList<>());

        assertThat(FilterFactory.getFilter()).isInstanceOf(MyFilter.class);
    }
}

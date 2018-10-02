/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.fixtures;

import static org.assertj.core.api.Assertions.assertThat;

import io.swagger.annotations.BasicAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.Reader;
import io.swagger.models.Swagger;
import javax.inject.Inject;
import org.seedstack.seed.Application;
import org.seedstack.swagger.BaseReaderListener;

@SwaggerDefinition(
        securityDefinition = @SecurityDefinition(
                basicAuthDefinitions = @BasicAuthDefinition(key = "basic", description = "Basic authentication")
        )
)
public class MyApiDefinition extends BaseReaderListener {
    @Inject
    private Application application;
    public static boolean beforeCalled = false;
    public static boolean afterCalled = false;

    @Override
    public void beforeScan(Reader reader, Swagger swagger) {
        assertThat(application).isNotNull();
        beforeCalled = true;
    }

    @Override
    public void afterScan(Reader reader, Swagger swagger) {
        assertThat(application).isNotNull();
        afterCalled = true;
    }
}

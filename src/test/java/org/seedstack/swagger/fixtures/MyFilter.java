/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.swagger.fixtures;

import static org.assertj.core.api.Assertions.assertThat;

import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.model.ApiDescription;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.seedstack.seed.Application;

public class MyFilter implements SwaggerSpecFilter {
    @Inject
    private Application application;
    public static boolean opAllowedCalled = false;
    public static boolean paramAllowedCalled = false;

    @Override
    public boolean isOperationAllowed(Operation operation, ApiDescription api, Map<String, List<String>> params,
            Map<String, String> cookies, Map<String, List<String>> headers) {
        assertThat(application).isNotNull();
        opAllowedCalled = true;
        return true;
    }

    @Override
    public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription api,
            Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        assertThat(application).isNotNull();
        paramAllowedCalled = true;
        return true;
    }

    @Override
    public boolean isPropertyAllowed(Model model, Property property, String propertyName,
            Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        assertThat(application).isNotNull();
        return true;
    }
}

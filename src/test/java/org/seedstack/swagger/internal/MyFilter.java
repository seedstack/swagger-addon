/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.swagger.internal;

import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.model.ApiDescription;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.properties.Property;
import java.util.List;
import java.util.Map;
import org.seedstack.seed.Ignore;

@Ignore
public class MyFilter implements SwaggerSpecFilter {
    @Override
    public boolean isOperationAllowed(Operation operation, ApiDescription api, Map<String, List<String>> params,
            Map<String, String> cookies, Map<String, List<String>> headers) {
        return false;
    }

    @Override
    public boolean isParamAllowed(Parameter parameter, Operation operation, ApiDescription api,
            Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        return false;
    }

    @Override
    public boolean isPropertyAllowed(Model model, Property property, String propertyName,
            Map<String, List<String>> params, Map<String, String> cookies, Map<String, List<String>> headers) {
        return false;
    }
}

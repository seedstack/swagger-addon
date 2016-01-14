/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import com.google.common.collect.Lists;
import com.google.inject.Binder;
import com.google.inject.Module;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.core.AbstractPlugin;
import io.swagger.models.Swagger;
import org.apache.commons.configuration.Configuration;
import org.seedstack.seed.Application;
import org.seedstack.seed.core.internal.application.ApplicationPlugin;
import org.seedstack.seed.core.spi.configuration.ConfigurationProvider;
import org.seedstack.seed.rest.internal.RestPlugin;

import java.util.Collection;

public class SwaggerPlugin extends AbstractPlugin {

    private Swagger swagger;

    @Override
    public String name() {
        return "swagger";
    }

    @Override
    public Collection<Class<?>> requiredPlugins() {
        return Lists.<Class<?>>newArrayList(ApplicationPlugin.class, ConfigurationProvider.class, RestPlugin.class);
    }

    @Override
    public InitState init(InitContext initContext) {
        SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();
        Application application = initContext.dependency(ApplicationPlugin.class).getApplication();
        swaggerConfiguration.setTitle(application.getName());
        swaggerConfiguration.setVersion(application.getVersion());
        Configuration configuration = initContext.dependency(ConfigurationProvider.class).getConfiguration();
        swaggerConfiguration.init(configuration);

        Collection<Class<?>> resources = initContext.dependency(RestPlugin.class).getResources();
        swagger = new SwaggerFactory().createSwagger(swaggerConfiguration, resources);
        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(Swagger.class).toInstance(swagger);
            }
        };
    }
}

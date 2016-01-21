/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.core.AbstractPlugin;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Swagger;
import org.apache.commons.configuration.Configuration;
import org.seedstack.seed.Application;
import org.seedstack.seed.core.internal.application.ApplicationPlugin;
import org.seedstack.seed.core.spi.configuration.ConfigurationProvider;
import org.seedstack.seed.rest.internal.RestPlugin;
import org.seedstack.seed.rest.spi.RestProvider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SwaggerPlugin extends AbstractPlugin implements RestProvider {

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
        configureWithDefaultValues(initContext, swaggerConfiguration);
        configureFromSwaggerProps(initContext, swaggerConfiguration);

        Collection<Class<?>> resources = initContext.dependency(RestPlugin.class).resources();
        swagger = new SwaggerFactory().createSwagger(swaggerConfiguration, resources);
        return InitState.INITIALIZED;
    }

    private void configureWithDefaultValues(InitContext initContext, SwaggerConfiguration swaggerConfiguration) {
        String restPath = initContext.dependency(RestPlugin.class).getConfiguration().getRestPath();
        swaggerConfiguration.setBasePath(restPath);

        Application application = initContext.dependency(ApplicationPlugin.class).getApplication();
        swaggerConfiguration.init(application);
    }

    private void configureFromSwaggerProps(InitContext initContext, SwaggerConfiguration swaggerConfiguration) {
        Configuration configuration = initContext.dependency(ConfigurationProvider.class).getConfiguration();
        swaggerConfiguration.init(configuration);
    }

    @Override
    public Object nativeUnitModule() {
        return new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(SwaggerSerializers.class).in(Scopes.SINGLETON);
                binder.bind(Swagger.class).toInstance(swagger);
            }
        };
    }

    @Override
    public Set<Class<?>> resources() {
        return new HashSet<Class<?>>();
    }

    @Override
    public Set<Class<?>> providers() {
        return Sets.<Class<?>>newHashSet(SwaggerSerializers.class);
    }
}

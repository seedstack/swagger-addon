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
import com.google.inject.Module;
import com.google.inject.Scopes;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.swagger.annotations.Api;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Swagger;
import org.seedstack.seed.Application;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.rest.RestConfig;
import org.seedstack.seed.rest.internal.RestPlugin;
import org.seedstack.seed.rest.spi.RestProvider;
import org.seedstack.swagger.SwaggerConfig;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Strings.isNullOrEmpty;

public class SwaggerPlugin extends AbstractSeedPlugin implements RestProvider {
    private Swagger swagger;

    @Override
    public String name() {
        return "swagger";
    }

    @Override
    public Collection<Class<?>> dependencies() {
        return Lists.newArrayList(RestPlugin.class);
    }

    @Override
    public InitState initialize(InitContext initContext) {
        SwaggerConfig swaggerConfig = getConfiguration(SwaggerConfig.class);
        RestPlugin restPlugin = initContext.dependency(RestPlugin.class);

        configureWithDefaultValues(swaggerConfig, restPlugin.getRestConfig(), getApplication());

        Collection<Class<?>> resources = filterResources(restPlugin.resources());
        swagger = new SwaggerFactory().createSwagger(swaggerConfig, resources);
        return InitState.INITIALIZED;
    }

    private Set<Class<?>> filterResources(Set<Class<?>> resources) {
        Set<Class<?>> filteredClasses = new HashSet<>();
        for (Class<?> resource : resources) {
            if (resource.getAnnotation(Api.class) != null) {
                filteredClasses.add(resource);
            }
        }
        return filteredClasses;
    }

    private void configureWithDefaultValues(SwaggerConfig swaggerConfig, RestConfig restConfig, Application application) {
        if (isNullOrEmpty(swaggerConfig.getBasePath()) && !isNullOrEmpty(restConfig.getPath())) {
            swaggerConfig.setBasePath(restConfig.getPath());
        }
        if (isNullOrEmpty(swaggerConfig.getTitle())) {
            swaggerConfig.setTitle(application.getName());
        }
        if (isNullOrEmpty(swaggerConfig.getVersion())) {
            swaggerConfig.setVersion(application.getVersion());
        }
    }

    @Override
    public Object nativeUnitModule() {
        return (Module) binder -> {
            binder.bind(SwaggerSerializers.class).in(Scopes.SINGLETON);
            binder.bind(Swagger.class).toInstance(swagger);
        };
    }

    @Override
    public Set<Class<?>> resources() {
        return new HashSet<>();
    }

    @Override
    public Set<Class<?>> providers() {
        return Sets.newHashSet(SwaggerSerializers.class);
    }
}

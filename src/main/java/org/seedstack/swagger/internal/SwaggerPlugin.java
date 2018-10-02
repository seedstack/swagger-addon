/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.swagger.internal;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.nuun.kernel.api.plugin.InitState;
import io.nuun.kernel.api.plugin.context.InitContext;
import io.nuun.kernel.api.plugin.request.ClasspathScanRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletContext;
import org.seedstack.seed.core.SeedRuntime;
import org.seedstack.seed.core.internal.AbstractSeedPlugin;
import org.seedstack.seed.rest.internal.RestPlugin;
import org.seedstack.seed.rest.spi.RestProvider;
import org.seedstack.swagger.SwaggerConfig;

public class SwaggerPlugin extends AbstractSeedPlugin implements RestProvider {
    private Collection<Class<?>> swaggerClasses;
    private SwaggerConfig swaggerConfig;
    private ServletContext servletContext;

    @Override
    public String name() {
        return "swagger";
    }

    @Override
    public Collection<Class<?>> dependencies() {
        return Lists.newArrayList(RestPlugin.class);
    }

    @Override
    protected void setup(SeedRuntime seedRuntime) {
        servletContext = seedRuntime.contextAs(ServletContext.class);
    }

    @Override
    public Collection<ClasspathScanRequest> classpathScanRequests() {
        return classpathScanRequestBuilder()
                .annotationType(SwaggerDefinition.class)
                .build();
    }

    @Override
    public InitState initialize(InitContext initContext) {
        swaggerConfig = getConfiguration(SwaggerConfig.class);
        RestPlugin restPlugin = initContext.dependency(RestPlugin.class);

        // Globally configure pretty printing
        SwaggerSerializers.setPrettyPrint(swaggerConfig.isPrettyPrint());

        // Merge all swagger-annotated classes in a unique set
        swaggerClasses = Stream.concat(
                restPlugin.resources().stream().filter(r -> r.isAnnotationPresent(Api.class)),
                initContext.scannedClassesByAnnotationClass().get(SwaggerDefinition.class).stream()
        ).collect(Collectors.toSet());

        return InitState.INITIALIZED;
    }

    @Override
    public Object nativeUnitModule() {
        return new SwaggerModule(swaggerClasses, swaggerConfig, servletContext);
    }

    @Override
    public Set<Class<?>> resources() {
        return Sets.newHashSet();
    }

    @Override
    public Set<Class<?>> providers() {
        return Sets.newHashSet(SwaggerSerializers.class);
    }
}

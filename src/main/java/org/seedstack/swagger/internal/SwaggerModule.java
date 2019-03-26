/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import com.google.inject.AbstractModule;
import com.google.inject.util.Providers;
import io.swagger.core.filter.SwaggerSpecFilter;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Swagger;
import java.util.Collection;
import javax.servlet.ServletContext;
import org.seedstack.swagger.BaseReaderListener;
import org.seedstack.swagger.SwaggerConfig;

class SwaggerModule extends AbstractModule {
    private final Collection<Class<?>> swaggerClasses;
    private final SwaggerConfig swaggerConfig;
    private final ServletContext servletContext;

    SwaggerModule(Collection<Class<?>> swaggerClasses, SwaggerConfig swaggerConfig, ServletContext servletContext) {
        this.swaggerClasses = swaggerClasses;
        this.swaggerConfig = swaggerConfig;
        this.servletContext = servletContext;
    }

    @Override
    public void configure() {
        if (swaggerConfig.getFilterClass() != null) {
            bind(SwaggerSpecFilter.class).to(swaggerConfig.getFilterClass());
        } else {
            bind(SwaggerSpecFilter.class).toProvider(Providers.of(null));
        }
        bind(Swagger.class).toProvider(new SwaggerProvider(swaggerClasses, servletContext));
        bind(SwaggerSerializers.class);
        requestStaticInjection(BaseReaderListener.class);
    }
}

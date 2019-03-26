/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger;

import com.google.inject.Injector;
import io.swagger.jaxrs.config.ReaderListener;
import javax.inject.Inject;

/**
 * Extend this base class if you need dependency injection in your Swagger {@link ReaderListener}s.
 */
public abstract class BaseReaderListener implements ReaderListener {
    @Inject
    private static Injector injector;

    public BaseReaderListener() {
        injector.injectMembers(this);
    }
}

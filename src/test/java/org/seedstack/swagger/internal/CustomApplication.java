/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import org.apache.commons.configuration.Configuration;
import org.seedstack.seed.Application;

import java.io.File;

public class CustomApplication implements Application {
    @Override
    public String getName() {
        return "title";
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public File getStorageLocation(String context) {
        return null;
    }

    @Override
    public String getInjectionGraph(String filter) {
        return null;
    }

    @Override
    public String getInjectionGraph() {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public Configuration getConfiguration(Class<?> clazz) {
        return null;
    }

    @Override
    public String substituteWithConfiguration(String value) {
        return null;
    }
}

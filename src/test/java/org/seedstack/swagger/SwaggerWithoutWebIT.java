/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.swagger;

import static org.assertj.core.api.Assertions.assertThat;

import io.swagger.models.Swagger;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.testing.junit4.SeedITRunner;

@RunWith(SeedITRunner.class)
public class SwaggerWithoutWebIT {
    @Inject
    private Swagger swagger;

    @Test
    public void standaloneAppCanRun() {
        assertThat(swagger).isNotNull();
    }
}

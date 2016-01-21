/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;

@Api
@Path("/hello")
public class MyResource {

    @GET
    @Path("/{name}")
    @Produces("text/plain")
    @ApiOperation(value = "Say hello the user", produces = "text/plain")
    public String hello(@ApiParam(value = "The user name", required = true) @PathParam("name") String name) {
        return "hello " + name;
    }
}

/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.Swagger;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("/swagger.json")
public class SwaggerJsonResource extends AbstractSwaggerResource {

    @Context
    private ServletContext context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "The swagger definition in JSON", hidden = true)
    public Swagger getListingJson(@Context HttpHeaders headers, @Context UriInfo uriInfo) {
        return (Swagger) getListing(headers, uriInfo);
    }

}

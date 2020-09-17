/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import io.swagger.annotations.ApiOperation;
import io.swagger.models.Swagger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/swagger.json")
public class SwaggerJsonResource extends AbstractSwaggerResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "The swagger definition in JSON", hidden = true)
    public Swagger getListingJson(@Context HttpHeaders headers, @Context UriInfo uriInfo) {
        return getSwagger(headers, uriInfo);
    }
}

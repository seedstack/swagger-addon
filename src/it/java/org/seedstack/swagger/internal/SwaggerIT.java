/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import com.jayway.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.seed.core.SeedMain;
import org.seedstack.seed.spi.SeedLauncher;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.core.MediaType;

import static com.jayway.restassured.RestAssured.expect;

public class SwaggerIT {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String SWAGGER = "{\"swagger\":\"2.0\",\"info\":{\"description\":null,\"version\":\"1.0.2\",\"title\":null,\"termsOfService\":null,\"contact\":null,\"license\":null},\"host\":\"localhost:8080\",\"basePath\":\"/\",\"tags\":null,\"schemes\":[\"http\"],\"consumes\":null,\"produces\":null,\"paths\":{\"/hello/{name}\":{\"get\":{\"tags\":null,\"summary\":null,\"description\":null,\"operationId\":\"hello\",\"schemes\":null,\"consumes\":null,\"produces\":null,\"parameters\":[{\"name\":\"name\",\"in\":\"path\",\"description\":null,\"required\":true,\"type\":\"string\",\"items\":null,\"collectionFormat\":null,\"default\":null,\"maximum\":null,\"exclusiveMaximum\":null,\"minimum\":null,\"exclusiveMinimum\":null,\"pattern\":null,\"format\":null,\"enum\":null}],\"responses\":{\"200\":{\"description\":\"successful operation\",\"schema\":{\"type\":\"string\",\"format\":null,\"example\":null,\"xml\":null,\"position\":null,\"description\":null,\"title\":null,\"readOnly\":null,\"minLength\":null,\"maxLength\":null,\"pattern\":null,\"default\":null,\"enum\":null},\"examples\":null,\"headers\":{}}},\"security\":null,\"externalDocs\":null,\"deprecated\":null},\"post\":null,\"put\":null,\"delete\":null,\"options\":null,\"patch\":null,\"parameters\":null}},\"securityDefinitions\":null,\"definitions\":null,\"parameters\":null,\"externalDocs\":null,\"securityRequirement\":null}";
    private SeedLauncher launcher;

    @Before
    public void setUp() throws Exception {
        launcher = SeedMain.getLauncher();
        launcher.launch(new String[]{});
    }

    @Test
    public void exposeSwagger() throws Exception {
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON)
                .get(BASE_URL + "/swagger.yaml");

        Response response = expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON)
                .get(BASE_URL + "/swagger.json");

        response.print();
        JSONAssert.assertEquals(response.asString(), SWAGGER, true);
    }

    @After
    public void tearDown() throws Exception {
        launcher.shutdown();
    }

}

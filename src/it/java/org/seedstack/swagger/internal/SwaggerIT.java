/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger.internal;

import com.jayway.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seedstack.seed.core.SeedMain;
import org.seedstack.seed.spi.SeedLauncher;
import org.skyscreamer.jsonassert.JSONAssert;

import javax.ws.rs.core.MediaType;

import static com.jayway.restassured.RestAssured.expect;

public class SwaggerIT {

    private static final String BASE_URL = "http://localhost:9001";
    private static final String SWAGGER_JSON = "{\"swagger\":\"2.0\",\"info\":{\"version\":\"1.0.2\",\"title\":\"Test API\"},\"host\":\"localhost:9001\",\"schemes\":[\"http\"],\"paths\":{\"/hello/{name}\":{\"get\":{\"summary\":\"Say hello the user\",\"description\":\"\",\"operationId\":\"hello\",\"produces\":[\"text/plain\"],\"parameters\":[{\"name\":\"name\",\"in\":\"path\",\"description\":\"The user name\",\"required\":true,\"type\":\"string\"}],\"responses\":{\"200\":{\"description\":\"successful operation\",\"schema\":{\"type\":\"string\"}}}}}}}";
    private static final String SWAGGER_YAML = "---\n" +
            "swagger: \"2.0\"\n" +
            "info:\n" +
            "  version: \"1.0.2\"\n" +
            "  title: \"Test API\"\n" +
            "host: \"localhost:9001\"\n" +
            "schemes:\n" +
            "- \"http\"\n" +
            "paths:\n" +
            "  /hello/{name}:\n" +
            "    get:\n" +
            "      summary: \"Say hello the user\"\n" +
            "      description: \"\"\n" +
            "      operationId: \"hello\"\n" +
            "      produces:\n" +
            "      - \"text/plain\"\n" +
            "      parameters:\n" +
            "      - name: \"name\"\n" +
            "        in: \"path\"\n" +
            "        description: \"The user name\"\n" +
            "        required: true\n" +
            "        type: \"string\"\n" +
            "      responses:\n" +
            "        200:\n" +
            "          description: \"successful operation\"\n" +
            "          schema:\n" +
            "            type: \"string\"\n";

    private SeedLauncher launcher;

    @Before
    public void setUp() throws Exception {
        launcher = SeedMain.getLauncher();
        launcher.launch(new String[]{});
    }

    @Test
    public void exposeSwaggerJson() throws Exception {
        Response response = expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON)
                .get(BASE_URL + "/swagger.json");

        JSONAssert.assertEquals(SWAGGER_JSON, response.asString(), true);
    }

    @Test
    public void exposeSwaggerYaml() throws Exception {
        String response = expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON)
                .get(BASE_URL + "/swagger.yaml").asString();

        Assertions.assertThat(response).isEqualTo(SWAGGER_YAML);
    }

    @After
    public void tearDown() throws Exception {
        launcher.shutdown();
    }

}

/*
 * Copyright © 2013-2019, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.swagger;

import static io.restassured.RestAssured.expect;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.Response;
import javax.ws.rs.core.MediaType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.junit4.SeedITRunner;
import org.seedstack.seed.undertow.LaunchWithUndertow;
import org.seedstack.swagger.fixtures.MyApiDefinition;
import org.seedstack.swagger.fixtures.MyFilter;
import org.skyscreamer.jsonassert.JSONAssert;

@RunWith(SeedITRunner.class)
@LaunchWithUndertow
public class SwaggerIT {
    @Configuration("runtime.web.baseUrlSlash")
    private String baseUrl;
    @Configuration("runtime.web.server.port")
    private int port;
    private static final String SWAGGER_JSON = "{\"swagger\":\"2.0\",\"info\":{\"version\":\"1.0.2\"," +
            "\"title\":\"Test API\"},\"host\":\"localhost:%d\",\"basePath\":\"/context/api\"," +
            "\"schemes\":[\"http\"]," +
            "\"paths\":{\"/hello/{name}\":{\"get\":{\"summary\":\"Say hello the user\"," +
            "\"description\":\"\",\"operationId\":\"hello\",\"produces\":[\"text/plain\"]," +
            "\"parameters\":[{\"name\":\"name\",\"in\":\"path\",\"description\":\"The user name\"," +
            "\"required\":true,\"type\":\"string\"},{\"name\":\"surname\",\"in\":\"query\"," +
            "\"description\":\"The user surnames\",\"required\":true,\"type\":\"array\"," +
            "\"items\":{\"type\":\"string\"},\"collectionFormat\":\"multi\"}],\"responses\":" +
            "{\"200\":{\"description\":\"successful operation\",\"schema\":{\"type\":\"string\"}}}}}}," +
            "\"securityDefinitions\":{\"basic\":{\"description\":\"Basic authentication\",\"type\":\"basic\"}}}";
    private static final String SWAGGER_YAML = "---\n" +
            "swagger: \"2.0\"\n" +
            "info:\n" +
            "  version: \"1.0.2\"\n" +
            "  title: \"Test API\"\n" +
            "host: \"localhost:%d\"\n" +
            "basePath: \"/context/api\"\n" +
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
            "      - name: \"surname\"\n" +
            "        in: \"query\"\n" +
            "        description: \"The user surnames\"\n" +
            "        required: true\n" +
            "        type: \"array\"\n" +
            "        items:\n" +
            "          type: \"string\"\n" +
            "        collectionFormat: \"multi\"\n" +
            "      responses:\n" +
            "        200:\n" +
            "          description: \"successful operation\"\n" +
            "          schema:\n" +
            "            type: \"string\"\n" +
            "securityDefinitions:\n" +
            "  basic:\n" +
            "    description: \"Basic authentication\"\n" +
            "    type: \"basic\"\n";

    @Test
    public void exposeSwaggerJson() throws Exception {
        Response response = expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON)
                .get(baseUrl + "api/swagger.json");

        JSONAssert.assertEquals(String.format(SWAGGER_JSON, port), response.asString(), true);
        checkReaderListenerCalled();
        checkFilterCalled();
    }

    @Test
    public void exposeSwaggerYaml() throws Exception {
        String response = expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON)
                .get(baseUrl + "api/swagger.yaml").asString();

        assertThat(response).isEqualTo(String.format(SWAGGER_YAML, port));
        checkReaderListenerCalled();
        checkFilterCalled();
    }

    private void checkReaderListenerCalled() {
        assertThat(MyApiDefinition.beforeCalled).isTrue();
        assertThat(MyApiDefinition.afterCalled).isTrue();
    }

    private void checkFilterCalled() {
        assertThat(MyFilter.opAllowedCalled).isTrue();
        assertThat(MyFilter.paramAllowedCalled).isTrue();
    }
}

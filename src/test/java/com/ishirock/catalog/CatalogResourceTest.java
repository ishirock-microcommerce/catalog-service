package com.ishirock.catalog;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CatalogResourceTest {

    @Test
    public void testCatalogGetEndpoint() {
        given()
          .when().get("/catalog")
          .then()
             .statusCode(200);
    }

}
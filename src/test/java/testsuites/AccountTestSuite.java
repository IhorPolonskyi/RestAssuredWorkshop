package testsuites;

import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.User;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Created by IT school on 18.08.2017.
 */
public class AccountTestSuite {
    private String HOST = "http://192.168.1.41:8080/";
    String token;

    @BeforeMethod
    void before(){

        User user = new User("admin", "admin");

        token = given()
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().body()
                .body(user)
                .when().post(HOST + "login")
                .then().statusCode(200)
                .extract().body().jsonPath().getString("token");
    }

    @Test
    void addAccountTestCase(){
        Map body = new HashMap();

        body.put("name", "test account");
        body.put("currency", "UKR");

        given()
                .log().method().log().uri().log().body()
                .contentType(ContentType.JSON)
                .body(body)
                .header("Authorization", token)
        .when().post(HOST+"account")
                 .then().statusCode(200)
                 .log().body()
                 .body("name", equalTo(body.get("name")))
                 .body("currency", equalTo(body.get("currency")));
    }

    @Test
    void getAccountPaymentsTestCase(){

        given()
                .log().method().log().uri()
                .header("Authorization", token)
                .param("accountId", 1)
        .when().get(HOST+"payments")
                .then().statusCode(200)
                .log().body()
                .assertThat()
        .body("size()", greaterThanOrEqualTo(6))
        .body("find{ it.number == 1 }.appointment", equalTo("test"))
        .body("number", hasItems(1, 7, 8))
        .body("[0].exchange.name", equalTo("UAH-USD"));


    }
}

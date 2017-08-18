package testsuites;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import service.User;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

/**
 * Created by IT school on 18.08.2017.
 */
public class LoginTestSuite {

    private String HOST = "http://192.168.1.41:8080/";

    @Test
    public void  positiveLoginTestCase(){
      /*  Map body = new HashMap();
        body.put("name", "admin");
        body.put("pass", "admin");*/

        User user = new User("admin", "admin");

        given()
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().body()
                .body(user)
         .when().post(HOST + "login")
                .then().statusCode(200)
         .log().body()
         .body("token", notNullValue());

    }

    @Test
    public void  negativeLoginTestCase(){
     /*   Map body = new HashMap();
        body.put("name", "wrong");
        body.put("pass", "wrong");*/

        User user = new User("false", "user");

        given()
                .contentType(ContentType.JSON)
                .log().method().log().uri().log().body()
                .body(user)
                .when().post(HOST + "login")
                .then().statusCode(200)
                .log().body()
                .body("token", nullValue());

    }
}

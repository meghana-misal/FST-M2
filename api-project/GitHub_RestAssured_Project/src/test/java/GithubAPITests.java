import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class GithubAPITests {

    RequestSpecification requestSpec;
    private int id;

    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization","token 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")
                .setBaseUri("https://api.github.com")
                .build();
    }

    @Test(priority = 1)
    public void postRequest(){
        Reporter.log("IN post request");
        String reqBody;
        Response response = null;
        FileInputStream inputJSON;
        try {
            inputJSON = new FileInputStream("src/test/java/key.json");
            reqBody = new String(inputJSON.readAllBytes());
            response = given().spec(requestSpec)
                    .body(reqBody)
                    .when().post("/user/keys");
            inputJSON.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert response != null;
        System.out.println(response.getBody().prettyPrint());
        Reporter.log(response.getBody().prettyPrint());
        //Storing id into variable
        id = response.then().extract().path("id");
        System.out.println("Generated Id is="+ id);
        Reporter.log("Generated Id is="+ id);
        //assertion to check status code
        response.then().statusCode(201);
        Reporter.log("End post request");

    }

    @Test(priority = 2)
    public void getRequest(){
        Reporter.log("IN get request");
        Response response = given().spec(requestSpec)
                .when().get("/user/keys");

        response.then().log().all();
        String resBody = response.getBody().asPrettyString();
        Reporter.log(resBody);
        //assertion to check status code
        response.then().statusCode(200);
        Reporter.log("End post request");
    }

    @Test(priority = 3)
    public void deleteRequest(){
        Reporter.log("IN delete request");
        Response response =
                given().spec(requestSpec)
                        .pathParam("keyId", id)
                        .when().delete( " /user/keys/{keyId}");
        response.then().log().all();
        String resBody = response.getBody().asPrettyString();
        Reporter.log(resBody);
        //assertion to check status code
        response.then().statusCode(204);
        Reporter.log("End delete request");
    }

}

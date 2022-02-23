import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Activity3 {
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setUp(){
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://petstore.swagger.io/v2/pet")
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody("status", equalTo("alive"))
                .build();
    }

    @DataProvider
    public Object[][] petInfoProvider() {
        // Setting parameters to pass to test case
        return new Object[][] {
                { 77232, "Riley", "alive" },
                { 77233, "Hansel", "alive" }
        };
    }
    @Test(priority=1)
    public void postRequest(){
        String reqBody;
        Response response;

        //First get
        reqBody = "{\"id\": 77232, \"name\": \"Riley\", \"status\": \"alive\"}";
        response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();
        response.then().spec(responseSpec);

        //Second get
        reqBody = "{\"id\": 77233, \"name\": \"Hansel\", \"status\": \"alive\"}";
        response = given().spec(requestSpec)
                .body(reqBody)
                .when().post();
        response.then().spec(responseSpec);
    }
    @Test(dataProvider = "petInfoProvider", priority=2)
    public void getRequest(int id, String name, String status){
        Response response = given().spec(requestSpec)
                .pathParam("petId", id)
                .when().get("/{petId}");
        System.out.println(response.asPrettyString());
        response.then()
                .spec(responseSpec)
                .body("name", equalTo(name))
                .body("status",equalTo(status));
    }

    @Test(dataProvider = "petInfoProvider",priority=3)
    public void deleteRequest(int id, String name, String status){
        Response response = given().spec(requestSpec)
                .pathParam("petId", id)
                .when().delete("/{petId}");
        response.then().body("code", equalTo(200));
    }
}

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity2 {

    final static String ROOT_URI = "https://petstore.swagger.io/v2/user";

    @Test(priority=1)
    public void addNewUserFromFile() throws IOException {
        FileInputStream inputJSON = new FileInputStream("src/test/java/test.json");
        String reqBody = new String(inputJSON.readAllBytes());
        System.out.println(reqBody);
        Response response =
                given().contentType(ContentType.JSON)
                        .body(reqBody)
                        .when().post(ROOT_URI);
        inputJSON.close();
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("9907"));
    }

    @Test(priority=2)
    public void getUserInfo() {
        File outputJSON = new File("src/test/java/response.json");

        Response response =
                given().contentType(ContentType.JSON)
                        .pathParam("username", "meghana")
                        .when().get(ROOT_URI + "/{username}");


        String resBody = response.getBody().asPrettyString();

        try {
            outputJSON.createNewFile();
            FileWriter writer = new FileWriter(outputJSON.getPath());
            writer.write(resBody);
            writer.close();
        } catch (IOException excp) {
            excp.printStackTrace();
        }

        // Assertion
        response.then().body("id", equalTo(9907));
        response.then().body("username", equalTo("meghana"));
        response.then().body("firstName", equalTo("Meghana"));
        response.then().body("lastName", equalTo("Misal"));
        response.then().body("email", equalTo("meghanavmisal@gmail.com"));
        response.then().body("password", equalTo("password123"));
        response.then().body("phone", equalTo("4353536576"));
    }

    @Test(priority=3)
    public void deleteUser() {
        Response response =
                given().contentType(ContentType.JSON)
                        .pathParam("username", "meghana")
                        .when().delete(ROOT_URI + "/{username}");

        // Assertion
        //response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("meghana"));
    }
}
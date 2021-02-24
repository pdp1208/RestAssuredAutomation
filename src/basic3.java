import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.payLoad;
import files.resources;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class basic3 {
	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {

		FileInputStream fis = new FileInputStream(
				"F:\\eclipseWorkSpace\\DemoJavaRestAssuredProject\\src\\files\\env.properties");
		prop.load(fis);

	}

	@Test
	public void AddandDeletePlace() {

		RestAssured.baseURI = prop.getProperty("HOST");
		Response res = given()
				.queryParam("key", prop.getProperty("KEY")).body(payLoad.getPostData())
				.when()
				.post(resources.placePostData())
				.then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.and().body("status", equalTo("OK"))
				.extract().response();

		String responseString = res.asString();

		System.out.println(responseString);

		JsonPath jsonResponse = new JsonPath(responseString);
		String placeid = jsonResponse.get("place_id");

		// Grab the place-Id and use the same in delete place API

		given()
		.queryParam("key", prop.getProperty("KEY")).body("{" + "\"place_id\":\"" + placeid + "\"" + "}")
		.when()
		.post("/maps/api/place/delete/json")
		.then()
		.assertThat().statusCode(200).and()
		.contentType(ContentType.JSON).and()
		.body("status", equalTo("OK"));

	}
}

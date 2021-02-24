import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class basic6 {

	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {

		FileInputStream fis = new FileInputStream(
				"F:\\eclipseWorkSpace\\DemoJavaRestAssuredProject\\src\\files\\env.properties");
		prop.load(fis);

	}

	@Test
	public void test() throws IOException {

		String body=GenerateStringFromResource("F:\\SeleniumRelatedFiles\\JsonFileBody.json");
		RestAssured.baseURI = "http://216.10.245.166";
		Response rawResponse=given()
		.header("Content-Type","application/json")
		.body(body)
		.when()
		.post("Library/Addbook.php")
		.then()
		.assertThat().statusCode(200).and()
		.contentType(ContentType.JSON)
		.extract().response();

		JsonPath x=reusableMethods.rawToJson(rawResponse);
		String s = x.get("ID");
		
		System.out.println(s);
		// Grab the place-Id and use the same in delete place API
	}

	
	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
		}
	
	
}

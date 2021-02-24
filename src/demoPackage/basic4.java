package demoPackage;
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
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class basic4 {

	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {

		FileInputStream fis = new FileInputStream(
				"F:\\eclipseWorkSpace\\DemoJavaRestAssuredProject\\src\\files\\env.properties");
		prop.load(fis);

	}

	@Test
	public void test() throws IOException {

		String body=GenerateStringFromResource("F:\\SeleniumRelatedFiles\\XmlFileBody.xml");
		RestAssured.baseURI = prop.getProperty("HOST");
		Response rawResponse=given()
		.queryParam("key", prop.getProperty("KEY"))
		.body(body)
		.when()
		.post("/maps/api/place/add/xml")
		.then()
		.assertThat().statusCode(200).and()
		.contentType(ContentType.XML)
		.extract().response();

		XmlPath x=reusableMethods.rawToXml(rawResponse);
		String s = x.get("response.place_id");
		
		System.out.println(s);
		// Grab the place-Id and use the same in delete place API
	}

	
	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
		}
}

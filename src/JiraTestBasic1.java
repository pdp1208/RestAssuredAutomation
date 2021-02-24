import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JiraTestBasic1 {

	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {

		FileInputStream fis = new FileInputStream(
				"F:\\eclipseWorkSpace\\DemoJavaRestAssuredProject\\src\\files\\env.properties");
		prop.load(fis);
	}

	@Test
	public void createAnIssueInJira() {

		RestAssured.baseURI = prop.getProperty("JIRAHOME");

		Response res = given()
				.header("Content-Type", "Application/json")
				.header("Cookie", "JSESSIONID=" + reusableMethods.getJiraSessionKey())
				.body("{" + "\"fields\": {" + "\"project\":" + "{" + "\"key\": \"BLACKPEARL\"" + "},"
						+ "\"summary\": \" All -in - One - 1 \"," + "\"description\": \"All -in - One - 1 \","
						+ "\"issuetype\": {" + "\"name\": \"Bug\"" + "}" + "}" + "}")
				.when()
				.post("/rest/api/2/issue/")
				.then()
				.assertThat()
				.statusCode(201)
				.and()
				.contentType(ContentType.JSON)
				.extract()
				.response();

		JsonPath js = reusableMethods.rawToJson(res);
		String id = js.get("id");
		System.out.println(id);
	}

	@Test
	public void addCommentForIssueInJira() {

		RestAssured.baseURI = prop.getProperty("JIRAHOME");

		Response res = given()
				.header("Content-Type", "Application/json")
				.header("Cookie", "JSESSIONID=" + reusableMethods.getJiraSessionKey())
				.body("{" + "\"body\": \"Added Comment -  All -in - One - 1 \"," + "\"visibility\": {"
						+ "\"type\": \"role\"," + "\"value\": \"Administrators\"" + "}" + "}")
				.when()
				.post("rest/api/2/issue/" + reusableMethods.GetBugId() + "/comment")
				.then()
				.assertThat()
				.statusCode(201)
				.and()
				.contentType(ContentType.JSON)
				.extract()
				.response();

		JsonPath js = reusableMethods.rawToJson(res);
		String id = js.get("id");
		System.out.println(id);
	}

	@Test
	public void updateCommentForIssueInJira() {

		String budId = reusableMethods.GetBugId();
		RestAssured.baseURI = prop.getProperty("JIRAHOME");

		Response res = given()
				.header("Content-Type", "Application/json")
				.header("Cookie", "JSESSIONID=" + reusableMethods.getJiraSessionKey())
				.body("{" + "\"body\": \"Updated Comment -  All -in - One - 1 " + "\"," + "\"visibility\": {"
						+ "\"type\": \"role\"," + "\"value\": \"Administrators\"" + "}" + "}")
				.when()
				.put("rest/api/2/issue/" + budId + "/comment/" + reusableMethods.getCommentId(budId))
				.then()
				.log()
				.body()
				.assertThat()
				.statusCode(200)
				.and()
				.contentType(ContentType.JSON)
				.extract()
				.response();

		JsonPath js = reusableMethods.rawToJson(res);
		String id = js.get("id");
		System.out.println(id);
	}

}

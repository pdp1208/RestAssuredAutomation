package files;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class reusableMethods {

	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

	public static XmlPath rawToXml(Response res) {
		String respo = res.asString();
		XmlPath x = new XmlPath(respo);
		return x;
	}

	public static JsonPath rawToJson(Response res) {
		String respo = res.asString();
		JsonPath x = new JsonPath(respo);
		return x;
	}

	public static String getJiraSessionKey() {
		// Creating Session
		RestAssured.baseURI = "http://localhost:8080";

		Response res = given().header("Content-Type", "Application/json")
				.body("{" + "\"username\": \"pdp1208\"," + "\"password\": \"LovingFamily@123\"" + "}").when()
				.post("/rest/auth/1/session").then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();

		JsonPath js = reusableMethods.rawToJson(res);
		String sessionID = js.get("session.value");

		return sessionID;

	}

	public static String GetBugId() {
		// Creating bug and returning bug ID
		RestAssured.baseURI = "http://localhost:8080";

		Response res = given().header("Content-Type", "Application/json")
				.header("Cookie", "JSESSIONID=" + reusableMethods.getJiraSessionKey())
				.body("{" + "\"fields\": {" + "\"project\":" + "{" + "\"key\": \"BLACKPEARL\"" + "},"
						+ "\"summary\": \"Automation Testing - All In One\","
						+ "\"description\": \"Automation Testing - All In One\"," + "\"issuetype\": {"
						+ "\"name\": \"Bug\"" + "}" + "}" + "}")
				.when().post("/rest/api/2/issue/").then().assertThat().statusCode(201).and()
				.contentType(ContentType.JSON).extract().response();

		JsonPath js = reusableMethods.rawToJson(res);
		String bugId = js.get("id");
		System.out.println(bugId);
		return bugId;
	}

	public static String getCommentId(String bugId) {
		// Adding Comment and returning Comment ID
		RestAssured.baseURI = "http://localhost:8080";

		Response res = given().header("Content-Type", "Application/json")
				.header("Cookie", "JSESSIONID=" + reusableMethods.getJiraSessionKey())
				.body("{" + "\"body\": \"HI i have commented from REST API\"," + "\"visibility\": {"
						+ "\"type\": \"role\"," + "\"value\": \"Administrators\"" + "}" + "}")
				.when().post("rest/api/2/issue/" + bugId + "/comment").then().assertThat().statusCode(201).and()
				.contentType(ContentType.JSON).extract().response();

		JsonPath js = reusableMethods.rawToJson(res);
		String id = js.get("id");
		System.out.println(id);
		return id;
	}
}

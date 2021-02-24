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

public class Twitter {

	String ConsumerKey="";
	String ConsmerSecret="";
	String Token="";
	String SecretToken="";
	
	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(
				"F:\\eclipseWorkSpace\\DemoJavaRestAssuredProject\\src\\files\\env.properties");
		prop.load(fis);
	}

	@Test
	public void getLatestTweet() {
		RestAssured.baseURI=prop.getProperty("TwitterBaseAPI");
		Response res= given().
				auth().oauth(ConsumerKey, ConsmerSecret, Token, SecretToken)
				.queryParam("Count","5").
				when().
				get("/home_timeline.json").
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
		
		JsonPath jr=reusableMethods.rawToJson(res);
		String TweetText= jr.get("text");
		String id=jr.get("id");
		System.out.println("TweetText="+TweetText+"  id="+id);								
	}
	
	@Test
	public String postNewTweet() {
		//Create A New tweet
		RestAssured.baseURI=prop.getProperty("TwitterBaseAPI");
		Response res= given().
				auth().oauth(ConsumerKey, ConsmerSecret, Token, SecretToken)
				.queryParam("status","I am Creating this tweet with Automation")
				.when()
				.post("/update.json").
				then().assertThat().statusCode(201).and().contentType(ContentType.JSON).extract().response();
		
		JsonPath jr=reusableMethods.rawToJson(res);
		String id=jr.get("id");
		System.out.println("id="+id);
		return id;
	}
	
	@Test
	public void deleteTweet() {
		//Delete tweet
		
		String id = postNewTweet();
		RestAssured.baseURI=prop.getProperty("TwitterBaseAPI");
		Response res= given().
				auth().oauth(ConsumerKey, ConsmerSecret, Token, SecretToken)
				.when()
				.post("/destroy/"+id+"/.json").
				then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
		
		JsonPath jr=reusableMethods.rawToJson(res);
		String TweetText= jr.get("text");
		String truncated=jr.get("truncated");
		System.out.println("TweetText="+TweetText+"  truncated="+id);
		
	}
}

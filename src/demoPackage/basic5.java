package demoPackage;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class basic5 {

	@Test
public void Test1() {
		// TODO Auto-generated method stub

		//Base URL or Host
		RestAssured.baseURI="https://maps.googleapis.com";
		
		
		Response res=given().
				param("location","-33.8670522,151.1957362").
				param("radius","1500").
				param("key","AIzaSyA4GmsmCbwnrJN7HWqIvfTEgij6rgBiC1Q").
				log().ifValidationFails().
				when().
				get("/maps/api/place/nearbysearch/json").
				then().assertThat().
				statusCode(200).and().contentType(ContentType.JSON).and().
				body("results[0].name",equalTo("Sydney")).and().
				body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).
				and().
				header("Server","scaffolding on HTTPServer2").log().body()
				.extract().response();

		
		JsonPath js = reusableMethods.rawToJson(res);
		int count=js.get("results.size()");
		System.out.println(count);
		
		for(int i=0;i<count;i++){
			String s=js.get("results["+i+"].name");
			System.out.println(s);
		}
		
		

		
	}


}

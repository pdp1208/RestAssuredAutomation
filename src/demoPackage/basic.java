package demoPackage;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class basic {

	@Test
public void Test1() {
		// TODO Auto-generated method stub

		//Base URL or Host
		RestAssured.baseURI="https://maps.googleapis.com";
		
		given().
				param("location","-33.8670522,151.1957362").
				param("radius","1500").
				param("key","AIzaSyA4GmsmCbwnrJN7HWqIvfTEgij6rgBiC1Q").
				when().
				get("/maps/api/place/nearbysearch/json").
				then().assertThat().
				statusCode(200).and().contentType(ContentType.JSON).and().
				body("results[0].name",equalTo("Sydney")).and().
				body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).
				and().
				header("Server","scaffolding on HTTPServer2");
		/*header("","").
				cookies("").
				body("")*/
	}


}

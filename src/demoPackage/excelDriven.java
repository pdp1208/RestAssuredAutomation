package demoPackage;
import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.payLoad;
import files.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class excelDriven {

@Test(dataProvider="BooksData", priority =1)
public void addBookData(String isbn, String aisle) {
		// TODO Auto-generated method stub

		//Base URL or Host
		RestAssured.baseURI="http://216.10.245.166";
		
		Response res=given().
		header("Content-Type","application/json").
				body(payLoad.addBook(isbn , aisle)).
				when().
				post("/Library/Addbook.php").
				then().log().body().
				assertThat().
				statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();

		
		JsonPath js=reusableMethods.rawToJson(res);
		String s= js.get("ID");
		System.out.println(s);
	}

@Test(dataProvider="BookID", priority =2)
public void deleteBookData(String id) {
		// TODO Auto-generated method stub

		//Base URL or Host
		RestAssured.baseURI="http://216.10.245.166";
		
		Response res=given().
		header("Content-Type","application/json").
				body(payLoad.deleteBook(id)).
				when().
				post("/Library/DeleteBook.php").
				then().log().body()
				.assertThat()
				.statusCode(200).and().contentType(ContentType.JSON)
				.extract().response();

		
		JsonPath js=reusableMethods.rawToJson(res);
		String s= js.get("msg");
		System.out.println(s);
	}

@DataProvider(name="BooksData")
public Object[][] getData()
{
	return new Object[][]{{"wd21","dd3"},{"1212","edef"},{"32e2","d3d3"}};
	
}

@DataProvider(name="BookID")
public Object[][] getBookID()
{
	return new Object[][]{{"wd21dd3"},{"1212edef"},{"32e2d3d3"}};
	
}

}

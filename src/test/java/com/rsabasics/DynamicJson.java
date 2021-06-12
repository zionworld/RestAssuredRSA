package com.rsabasics;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@BeforeMethod
	public void setup() {
	RestAssured.baseURI ="https://rahulshettyacademy.com/";	
	}
	
	//@Test(dataProvider="BookData")
	public void AddBook(String isbn, String aisle) {				
		
		String response = given().log().all()
		.body(Payload.AddBook(isbn,aisle))
		.header("Content-Type", "application/json")		
		.when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath responseJson = Utilities.RawToJson(response);
		String Id = responseJson.get("ID");
		System.out.println(Id);
		
		//delete Book
		given().log().all()
		.body("{\r\n"
				+ "    \"ID\": \""+Id+"\"\r\n"
				+ "}")
		.when().delete("Library/DeleteBook.php")
		.then()
		.statusCode(HttpStatus.SC_OK);
		
	}
	
	@DataProvider(name="BookData")
	public Object[][] getData() {
		return new Object[][] {{"iwtei","8726"},{"wykj","8782"},{"qoiu","2187"}};
	}
	
	@Test
	public void CreateFromFile() throws IOException {
				given().log().all()
				.body(new String(Files.readAllBytes(Paths.get(".\\books.json"))))
				.header("Content-Type", "application/json")		
				.when().post("Library/Addbook.php")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
	}
}

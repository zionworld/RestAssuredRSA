package com.rsabasics;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import org.apache.http.HttpStatus;

import files.Payload;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

public class Basics {

	public static void main(String[] args) {

		// given, when, then

		
		//Add Place
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat()
				.body("scope", equalTo("APP")).statusCode(HttpStatus.SC_OK).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		
		System.out.println(response);
		
		JsonPath js = new JsonPath(response);
		String place_id = js.getString("place_id");
		System.out.println(place_id);
		
		//Update Place
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place_id+"\",\r\n"
				+ "\"address\":\"70 winter walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().body("msg", equalTo("Address successfully updated")).statusCode(HttpStatus.SC_OK);
		
	
		//Get Place
		String getPlaceResponse = given().log().all().queryParams("key", "qaclick123","place_id",place_id)
		.when().get("maps/api/place/get/json")
		.then().log().all().extract().response().asString();
		
		JsonPath jsGetPlace = new JsonPath(getPlaceResponse);
		System.out.println(jsGetPlace.get("address").equals("70 winter walk, USA"));
		
	}
}

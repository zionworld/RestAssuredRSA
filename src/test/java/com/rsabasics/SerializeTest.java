package com.rsabasics;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import RequestPOJO.AddAddress;
import RequestPOJO.Location;

public class SerializeTest {

	@BeforeClass
	public void Setup() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

	}

	@Test
	public void AddAddress() {

		AddAddress addAddress = new AddAddress();
		addAddress.setAccuracy(50);
		addAddress.setAddress("14 Sugarberry Dr");
		addAddress.setLanguage("Malayalam");
		addAddress.setName("Lakshmi Vilas");
		addAddress.setPhoneNumber("914842228126");
		addAddress.setWebsite("Zion World");
		List<String> types = new ArrayList<String>();
		types.add("Maps");
		types.add("Semi-detached");
		addAddress.setTypes(types);
		Location location = new Location();
		location.setLat(77.1652876);
		location.setLng(87.268726);
		addAddress.setLocation(location);

		String response = given().log().all().queryParam("key", "qaclick123").body(addAddress).when()
				.post("/maps/api/place/add/json").then().statusCode(HttpStatus.SC_OK).extract().response().asString();
		
		JsonPath json = new JsonPath(response);
		Assert.assertNotNull("placeId");
		System.out.println(response);

	}
}

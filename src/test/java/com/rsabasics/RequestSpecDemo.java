package com.rsabasics;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import RequestPOJO.AddAddress;
import RequestPOJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestSpecDemo {

	RequestSpecification request;
	ResponseSpecification response;

	@BeforeClass
	public void Setup() {
		request = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		response = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

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

		RequestSpecification req = given().log().all().spec(request).body(addAddress);

		Response res = req.when().post("/maps/api/place/add/json").then().spec(response).extract().response();

		Assert.assertNotNull("placeId");
		System.out.println(response);
	}

}

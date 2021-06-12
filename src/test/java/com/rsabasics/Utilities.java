package com.rsabasics;

import io.restassured.path.json.JsonPath;

public class Utilities {

	public static JsonPath RawToJson(String response) {
		JsonPath js = new JsonPath(response);
		return js;
	}
	
}

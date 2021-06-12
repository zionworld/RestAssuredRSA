package com.rsabasics;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonResponse {

	public static void main(String[] args) {

		JsonPath js = new JsonPath(Payload.complexJsonResponse());

		// Count of courses
		int courseSize = js.getInt("courses.size()");
		System.out.println(courseSize);

		// print purchase amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);

		// print title of the first course
		String title = js.getString("courses[0].title");
		System.out.println(title);

		// number of copies sold by RPA course
		int numOfRPA = js.getInt("courses[2].copies");
		System.out.println(numOfRPA);

		// Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		for (int i = 0; i <courseSize; i++) {
			int price = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses[" + i + "].copies");
			int calc = price * copies;
			sum = sum + calc;
		}
		System.out.println("The sum of all the courses is : " + sum);
		Assert.assertEquals(purchaseAmount, sum);

		// print all titles and the price
		for (int i = 0; i < courseSize; i++) {
			String titleList = js.getString("courses[" + i + "].title");
			int price = js.getInt("courses[" + i + "].price");

			System.out.println(titleList + " : " + price);

		}

	}
}

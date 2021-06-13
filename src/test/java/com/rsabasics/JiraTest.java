package com.rsabasics;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
public class JiraTest {

	SessionFilter session = new SessionFilter();
	
	@BeforeClass
	public void CreateSession() {
		RestAssured.baseURI = "http://localhost:8080";
		given().header("Content-type", "application/json").filter(session).body("{\r\n"
				+ "    \"username\": \"sree.satish131\",\r\n"
				+ "    \"password\": \"Shrteiej1857\"\r\n"
				+ "}").log().all()
		.when().post("rest/auth/1/session")
		.then().assertThat().statusCode(200);
	}
	
	
	@Test
	public void AddComment() {
		given().log().all().pathParam("issueID", "10004").filter(session)
		.header("Content-Type","application/json")
		.body("{\r\n"
				+ "	\"body\":\"My third comment\",\r\n"
				+ "	\"visibility\":{\r\n"
				+ "		\"type\":\"role\",\r\n"
				+ "		\"value\":\"Administrators\"\r\n"
				+ "	}\r\n"
				+ "}")
		.when().post("rest/api/2/issue/{issueID}/comment")
		.then().log().all().assertThat().statusCode(HttpStatus.SC_CREATED);
		
		
	}
	
	//@Test
	public void AddAttachment() {
		given().header("X-Atlassian-Token", "no-check").relaxedHTTPSValidation().filter(session).header("Content-Type", "multipart/form-data")
		.multiPart("file", new File("C:\\Users\\user\\eclipse-workspace\\RestAssuredRSAMain\\RestAssuredRSA\\src\\test\\java\\files\\jiraattachment.jpg"))
		.pathParam("issueID", "10004").when().post("rest/api/2/issue/{issueID}/comment")
		.then().statusCode(200);
	}
	
	@Test
	public void GetIssue() {
		String response = given().log().all().filter(session).pathParam("issueID", "10004")
				.queryParam("fields", "comment")
				.when().get("/rest/api/2/issue/{issueID}")
				.then().log().all().extract().response().toString();
		System.out.println(response);
	}
}

package com.Harinder.ApiAutomationFramework;

import static io.restassured.RestAssured.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class JiraTest {
	String storyId = "";
	String issueId = "";
	String authorization = "Basic aGFyaW5kZXJrYXVyODgxQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBXelM3MFo0c1NVZkRVVnl1SzBiejJZVXk1TWpFQW0wWGd3cm9zV2wxREFiSWVfV2JQZ2pJUF9Rc3YzVlAxXzNyaGEtSENTbm96UEtVMWRWdm1Jc21mdzVuZDhNb3RwdDItb1lWbE5wUXA1VmpFQkpua1dWbEpZQl9CelIxQ21PbjVkLUQzRmVUc2RwWnFFS25FVXRjeVZRQ3Nram1UX2JveVMzVmpnOUFiNVU9NDFBOEFCQjg=";

	@BeforeMethod
	public void setUp() {
		baseURI = "https://harinderkaur.atlassian.net/";
	}

	
	@Test(priority = 0)
	public void getCreateIssueMetadata() {
		Response response = given().header("Authorization", authorization)
				.when().get("/rest/api/3/issue/createmeta");
		storyId = response.getBody().jsonPath().get("projects[0].issuetypes[0].id");
		System.out.println(storyId);
	}
	
	@Test(priority = 1)
	public void createIssue() {
		String requestBodyForCreateissue = "{\r\n"
				+ "  \"fields\": {\r\n"
				+ "    \"assignee\": {\r\n"
				+ "      \"id\": \"63ea0188d08a73e538f35bca\"\r\n"
				+ "    },\r\n"
				+ "    \"project\": {\r\n"
				+ "      \"id\": \"10000\"\r\n"
				+ "    },  \r\n"
				+ "    \"issuetype\": {\r\n"
				+ "      \"id\": \""+storyId+"\"\r\n"
				+ "    },  \r\n"
				+ "    \"labels\": [\r\n"
				+ "      \"triaged\"\r\n"
				+ "    ],\r\n"
				+ "    \"reporter\": {\r\n"
				+ "      \"id\": \"63ea0188d08a73e538f35bca\"\r\n"
				+ "    },\r\n"
				+ "    \"summary\": \"New issue created on feb 19\"\r\n"
				+ "   }\r\n"
				+ "}";
//		given().header("Authorization", authorization).header("Content-Type","application/json").body(requestBodyForCreateissue)
//		.when().log().all().post("/rest/api/3/issue")
//		.then().log().all();
		
		Response response = given().header("Authorization", authorization).header("Content-Type","application/json").body(requestBodyForCreateissue)
				.when().log().all().post("/rest/api/3/issue");
					
		issueId = response.getBody().jsonPath().get("id");
		System.out.println("issue id = "+issueId);
		
	}
	
	@Test(priority = 2)
	public void editIssue() {
		String requestBodyForEditIssue = "{\r\n"
				+ " \"fields\": {\r\n"
				+ "    \"summary\": \"Updated summary\"\r\n"
				+ "  },\r\n"
				+ "\"historyMetadata\": {\r\n"
				+ "    \"description\": \"Updated issue in Jira\"    \r\n"
				+ "  },\r\n"
				+ "\"update\": {\r\n"
				+ "      \"labels\": [\r\n"
				+ "      {\r\n"
				+ "        \"add\": \"triaged\"\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"remove\": \"\"\r\n"
				+ "      }\r\n"
				+ "    ]    \r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "";
		given().header("Authorization", authorization).pathParam("issueIdOrKey", issueId).header("Content-Type","application/json").body(requestBodyForEditIssue)
				.when().put("/rest/api/3/issue/{issueIdOrKey}")
				.then().log().all();
		}
	
	@Test(priority = 3)
	public void deleteIssue() {
		given().pathParam("issueIdOrKey", issueId).header("Authorization", authorization)
		.when().delete("/rest/api/3/issue/{issueIdOrKey}")
		.then().log().all();
		System.out.println("Issue deleted");		
	}
	@Test(priority = 4)
	public void getEvents() {
		// method chaining
		// gherkin language
		given().header("Authorization", authorization)
		.when().get("/rest/api/3/events")
		.then().log().all();
	}

}

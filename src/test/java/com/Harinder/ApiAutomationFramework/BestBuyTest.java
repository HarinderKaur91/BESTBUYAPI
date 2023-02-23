package com.Harinder.ApiAutomationFramework;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;

public class BestBuyTest {

	int productId ;
	String productName;
	String productType;
	String productModel;
	@BeforeMethod
	public void setUp() {
		baseURI = "http://localhost:3030";
	}
	
	@Test(priority = 0)
	public void getProducts() {
		Response response= given()
		.when().get("/products");
		
		productId = response.getBody().jsonPath().get("data[0].id");
		productName = response.getBody().jsonPath().get("data[0].name");
		productType = response.getBody().jsonPath().get("data[0].type");
		productModel = response.getBody().jsonPath().get("data[0].model");
		
		System.out.println("Product id = " + productId);
	}
	
	@Test(priority = 1)
	public void getSingleProductById() {
		given().pathParam("id", productId)
		.when().get("/products/{id}")
		.then().log().all()
		.and().assertThat().body("id", equalTo(productId))
		.and().assertThat().body("name", equalTo(productName))
		.and().assertThat().body ("type",equalTo(productType))
		.and().assertThat().body ("model", equalTo(productModel))
		.and().assertThat().statusCode(200);

	}
	
	@Test(priority = 2)
	public void removeProductById() {
		given().pathParam("id", productId)
		.when().delete("/products/{id}")
		.then().log().all()
		.and().assertThat().statusCode(200);

	}
	
	
	
}

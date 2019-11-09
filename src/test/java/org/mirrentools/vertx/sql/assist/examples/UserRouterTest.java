package org.mirrentools.vertx.sql.assist.examples;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.codec.BodyCodec;

@RunWith(VertxUnitRunner.class)
public class UserRouterTest {
	public WebClient webClient;
	/** 状态码的key */
	public final static String CODE_KEY = "code";

	@Before
	public void startUp() {
		Vertx vertx = Vertx.vertx();
		WebClientOptions options = new WebClientOptions();
		options.setDefaultHost("localhost").setDefaultPort(8080);
		webClient = WebClient.create(vertx, options);
	}

	@Test
	public void testFind(TestContext context) {
		Async async = context.async();
		webClient.get("/user/find").as(BodyCodec.jsonObject()).send(res -> {
			if (res.succeeded()) {
				context.assertEquals(200, res.result().statusCode());
				JsonObject body = res.result().body();
				System.out.println("testFind: " + body);
				context.assertEquals(200, body.getInteger(CODE_KEY));
				async.complete();
			} else {
				context.fail(res.cause());
			}
		});
	}

	@Test
	public void testGet(TestContext context) {
		Async async = context.async();
		webClient.get("/user/get")
		.addQueryParam("id", "1")
		.as(BodyCodec.jsonObject()).send(res -> {
			if (res.succeeded()) {
				context.assertEquals(200, res.result().statusCode());
				JsonObject body = res.result().body();
				System.out.println("testGet: " + body);
				context.assertEquals(200, body.getInteger(CODE_KEY));
				async.complete();
			} else {
				context.fail(res.cause());
			}
		});
	}
	
	@Test
	public void testLimit(TestContext context) {
		Async async = context.async();
		webClient.get("/user/limit")
		.as(BodyCodec.jsonObject()).send(res -> {
			if (res.succeeded()) {
				context.assertEquals(200, res.result().statusCode());
				JsonObject body = res.result().body();
				System.out.println("testLimit: " + body);
				context.assertEquals(200, body.getInteger(CODE_KEY));
				async.complete();
			} else {
				context.fail(res.cause());
			}
		});
	}
	@Test
	public void testSave(TestContext context) {
		Async async = context.async();
		webClient.post("/user/save")
		.addQueryParam("name", "name")
		.addQueryParam("tid", "1")
		.as(BodyCodec.jsonObject()).send(res -> {
			if (res.succeeded()) {
				context.assertEquals(200, res.result().statusCode());
				JsonObject body = res.result().body();
				System.out.println("testSave: " + body);
				context.assertEquals(200, body.getInteger(CODE_KEY));
				async.complete();
			} else {
				context.fail(res.cause());
			}
		});
	}
	@Test
	public void testUpdate(TestContext context) {
		Async async = context.async();
		webClient.post("/user/update")
		.addQueryParam("id", "13")
		.addQueryParam("name", "13")
		.addQueryParam("tid", "13")
		.as(BodyCodec.jsonObject()).send(res -> {
			if (res.succeeded()) {
				context.assertEquals(200, res.result().statusCode());
				JsonObject body = res.result().body();
				System.out.println("testUpdate: " + body);
				context.assertEquals(200, body.getInteger(CODE_KEY));
				async.complete();
			} else {
				context.fail(res.cause());
			}
		});
	}

}

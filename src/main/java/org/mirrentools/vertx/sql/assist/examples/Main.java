package org.mirrentools.vertx.sql.assist.examples;

import org.mirrentools.vertx.sql.assist.examples.router.UserRouter;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.assist.SQLExecute;
import io.vertx.ext.web.Router;

public class Main {
	public static void main(String[] args) {
		InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
		System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4j2LogDelegateFactory");
		Vertx vertx = Vertx.vertx();
		JsonObject config = new JsonObject()
				// 数据库类型
				.put("driver_class", "com.mysql.cj.jdbc.Driver")
				// 你的数据库连接
				.put("url", "jdbc:mysql://localhost:3306/root?useUnicode=true&useSSL=false&serverTimezone=UTC")
				// 账户与密码
				.put("user", "root").put("password", "root");
		/*
		 * 创建表的SQL语句
		 CREATE TABLE uuser (
		   id BIGINT(20) NOT NULL PRIMARY KEY , 
		   name VARCHAR(50) NOT NULL, 
		   type INT(11) 
		 )
		 */
		JDBCClient jdbcClient = JDBCClient.createShared(vertx, config);
		SQLExecute<JDBCClient> execute = SQLExecute.create(jdbcClient);
		Router router = Router.router(vertx);
		UserRouter.startService(router, execute);
		vertx.createHttpServer().requestHandler(router).listen(8080);
		System.out.println("server running port 8080");
	}
}

package org.mirrentools.vertx.sql.assist.examples.router;

import org.mirrentools.vertx.sql.assist.examples.service.UserService;

import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.assist.SQLExecute;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * User的接口服务
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class UserRouter {
	/** 日志工具 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	/** 数据服务接口 */
	private UserService service;

	private UserRouter(UserService service) {
		super();
		this.service = service;
	}

	/**
	 * 启动服务
	 * 
	 * @param router
	 *          Router
	 * @param execute
	 *          数据执行器
	 */
	public static void startService(Router router, SQLExecute<JDBCClient> execute) {
		UserRouter instance = new UserRouter(UserService.create(execute));
		// 获取所有数据
		router.get("/user/find").handler(instance::find);
		// 获取指定数据
		router.get("/user/get").handler(instance::get);
		// 获取分页数据
		router.get("/user/limit").handler(instance::limit);
		// 新增数据
		router.post("/user/save").handler(instance::save);
		// 更新数据
		router.post("/user/update").handler(instance::update);
	}

	private void find(RoutingContext rct) {
		MultiMap params = rct.request().params();
		if (LOG.isDebugEnabled()) {
			LOG.debug("执行获取数据[UserService.find]-->请求参数:\n" + params);
		}
		service.find(params, res -> {
			rct.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").end(res.result().toBuffer());
		});
	}

	private void get(RoutingContext rct) {
		MultiMap params = rct.request().params();
		if (LOG.isDebugEnabled()) {
			LOG.debug("执行获取数据[UserService.get]-->请求参数:\n" + params);
		}
		service.get(params, res -> {
			rct.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").end(res.result().toBuffer());
		});
	}

	private void limit(RoutingContext rct) {
		MultiMap params = rct.request().params();
		if (LOG.isDebugEnabled()) {
			LOG.debug("执行获取数据[UserService.limit]-->请求参数:\n" + params);
		}
		service.limit(params, res -> {
			rct.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").end(res.result().toBuffer());
		});
	}

	private void save(RoutingContext rct) {
		MultiMap params = rct.request().params();
		if (LOG.isDebugEnabled()) {
			LOG.debug("执行新增数据[UserService.save]-->请求参数:\n" + params);
		}
		service.save(params, res -> {
			rct.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").end(res.result().toBuffer());
		});
	}

	private void update(RoutingContext rct) {
		MultiMap params = rct.request().params();
		if (LOG.isDebugEnabled()) {
			LOG.debug("执行更新数据[UserService.update]-->请求参数:\n" + params);
		}
		service.update(params, res -> {
			rct.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8").end(res.result().toBuffer());
		});
	}
}

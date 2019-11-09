package org.mirrentools.vertx.sql.assist.examples.service;

import org.mirrentools.vertx.sql.assist.examples.service.impl.UserServiceImpl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.assist.SQLExecute;

/**
 * 数据服务接口
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public interface UserService {
	/**
	 * 创建一个实例
	 * 
	 * @param execute
	 * @return
	 */
	static UserService create(SQLExecute<JDBCClient> execute) {
		return new UserServiceImpl(execute);
	}

	/**
	 * 获取所有数据
	 * 
	 * @param params
	 * @param handler
	 */
	void find(MultiMap params, Handler<AsyncResult<JsonObject>> handler);

	/**
	 * 获取指定的属性
	 * 
	 * @param params
	 * @param handler
	 */
	void get(MultiMap params, Handler<AsyncResult<JsonObject>> handler);

	/**
	 * 获取分页
	 * 
	 * @param params
	 * @param handler
	 */
	void limit(MultiMap params, Handler<AsyncResult<JsonObject>> handler);

	/**
	 * 保存
	 * 
	 * @param params
	 * @param handler
	 */
	void save(MultiMap params, Handler<AsyncResult<JsonObject>> handler);

	/**
	 * 更新
	 * 
	 * @param params
	 * @param handler
	 */
	void update(MultiMap params, Handler<AsyncResult<JsonObject>> handler);

}

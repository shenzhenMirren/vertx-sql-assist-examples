package org.mirrentools.vertx.sql.assist.examples.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mirrentools.vertx.sql.assist.examples.entity.User;
import org.mirrentools.vertx.sql.assist.examples.service.UserService;
import org.mirrentools.vertx.sql.assist.examples.sql.UserSQL;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.assist.SQLExecute;
import io.vertx.ext.sql.assist.SqlAssist;

/**
 * 数据服务接口的默认实现
 * 
 * @author <a href="http://mirrentools.org">Mirren</a>
 *
 */
public class UserServiceImpl implements UserService {
	/** 日志工具 */
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	/** SQL操作语句 */
	private UserSQL userSQL;

	/**
	 * 初始化
	 * 
	 * @param execute
	 */
	public UserServiceImpl(SQLExecute<JDBCClient> execute) {
		super();
		this.userSQL = new UserSQL(execute);
	}

	@Override
	public void find(MultiMap params, Handler<AsyncResult<JsonObject>> handler) {
		userSQL.selectAll(res -> {
			if (res.succeeded()) {
				List<JsonObject> result = res.result() == null ? new ArrayList<>() : res.result();
				if (LOG.isDebugEnabled()) {
					LOG.debug("执行获取数据[UserServiceImpl.find]-->成功:" + result);
				}
				handler.handle(formatSuccess(result));
			} else {
				LOG.error("执行获取数据[UserServiceImpl.find]-->失败:", res.cause());
				handler.handle(formatFailure(500, "获取数据失败,请稍后重试!", "获取数据失败,请联系管理员!", null));
			}
		});
	}

	@Override
	public void get(MultiMap params, Handler<AsyncResult<JsonObject>> handler) {
		String id = params.get("id");
		if (id == null || id.trim().isEmpty()) {
			handler.handle(formatFailure(412, "获取数据失败,请求中缺少必填的参数!", "id不能为空", null));
			return;
		}
		userSQL.selectById(id, res -> {
			if (res.succeeded()) {
				JsonObject result = res.result();
				if (LOG.isDebugEnabled()) {
					LOG.debug("执行获取数据[UserServiceImpl.get]-->成功:" + result);
				}
				handler.handle(formatSuccess(result));
			} else {
				LOG.error("执行获取数据[UserServiceImpl.get]-->失败:", res.cause());
				handler.handle(formatFailure(500, "获取数据失败,请稍后重试!", "获取数据失败,请联系管理员!", null));
			}
		});
	}

	@Override
	public void limit(MultiMap params, Handler<AsyncResult<JsonObject>> handler) {
		Integer page = getInteger(params.get("page"), 1);
		Integer rowSize = getInteger(params.get("rowSize"), 15);
		SqlAssist assist = new SqlAssist();
		assist.setPage(page).setRowSize(rowSize);
		userSQL.limitAll(assist, res -> {
			if (res.succeeded()) {
				JsonObject result = res.result();
				if (LOG.isDebugEnabled()) {
					LOG.debug("执行获取数据[UserServiceImpl.limit]-->成功:" + result);
				}
				handler.handle(formatSuccess(result));
			} else {
				LOG.error("执行获取数据[UserServiceImpl.limit]-->失败:", res.cause());
				handler.handle(formatFailure(500, "获取数据失败,请稍后重试!", "获取数据失败,请联系管理员!", null));
			}
		});
	}

	@Override
	public void save(MultiMap params, Handler<AsyncResult<JsonObject>> handler) {
		try {
			User user = new User(params);
			if (idNullOrEmpty(user.getName())) {
				handler.handle(formatFailure(412, "修改数据失败,缺少必填的数据!", "缺少必填项参数", 0));
				return;
			}
			userSQL.insertNonEmpty(user, res -> {
				if (res.succeeded()) {
					int result = res.result();
					if (LOG.isDebugEnabled()) {
						LOG.debug("执行新增数据[UserServiceImpl.save]-->成功:" + result);
					}
					handler.handle(formatSuccess(result));
				} else {
					LOG.error("执行新增数据[UserServiceImpl.save]-->失败:", res.cause());
					handler.handle(formatFailure(500, "新增失败,请稍后重试!", "获取数据失败,请联系管理员!", 0));
				}
			});
		} catch (Exception e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("执行新增数据[UserServiceImpl.save]-->失败:", e);
			}
			handler.handle(formatFailure(413, "新增失败,存在无效的数据!", e.getMessage(), 0));
		}
	}

	@Override
	public void update(MultiMap params, Handler<AsyncResult<JsonObject>> handler) {
		try {
			User user = new User(params);
			if (user.getId() == null) {
				handler.handle(formatFailure(412, "修改数据失败,缺少必填的数据!", "id不能为空", 0));
				return;
			}
			if (user.getName() == null && user.getType() == null) {
				handler.handle(formatFailure(412, "修改数据失败,没有设置修改项!", "没有要更新的属性", 0));
				return;
			}
			userSQL.updateNonEmptyById(user, res -> {
				if (res.succeeded()) {
					int result = res.result();
					if (LOG.isDebugEnabled()) {
						LOG.debug("执行更新数据[UserServiceImpl.update]-->成功:" + result);
					}
					handler.handle(formatSuccess(result));
				} else {
					LOG.error("执行更新数据[UserServiceImpl.update]-->失败:", res.cause());
					handler.handle(formatFailure(500, "获取数据失败,请稍后重试!", "获取数据失败,请联系管理员!", null));
				}
			});
		} catch (Exception e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("执行更新数据[UserServiceImpl.save]-->失败:", e);
			}
			handler.handle(formatFailure(413, "修改数据失败,存在无效的数据!", e.getMessage(), null));
		}
	}

	/**
	 * TODO 如果你看到这些方法时,你应该将其抽成一个工具类,并修改模板使用工具类替换它们
	 */
	public <T> Future<JsonObject> formatSuccess(T data) {
		JsonObject result = new JsonObject();
		// 状态码
		result.put("code", 200);
		// 显示给用户的提示信息
		result.put("msg", "成功!");
		// 显示给前端开发人员的信息
		result.put("explain", "成功!");
		// 操作结果的数据
		if (data == null) {
			result.putNull("data");
		} else {
			result.put("data", data);
		}
		return Future.succeededFuture(result);
	}

	/**
	 * TODO 这里可以在工具类中提供不同的构造方法
	 */
	public <T> Future<JsonObject> formatFailure(int code, String msg, String explain, T data) {
		JsonObject result = new JsonObject();
		result.put("code", code);
		result.put("msg", msg);
		result.put("explain", explain);
		if (data == null) {
			result.putNull("data");
		} else {
			result.put("data", data);
		}
		return Future.succeededFuture(result);
	}

	public boolean idNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public Integer getInteger(String num, Integer def) {
		try {
			Integer result = new Integer(num);
			return result;
		} catch (NumberFormatException e) {
			return def;
		}
	}

}

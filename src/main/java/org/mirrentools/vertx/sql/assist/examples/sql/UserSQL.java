package org.mirrentools.vertx.sql.assist.examples.sql;

import org.mirrentools.vertx.sql.assist.examples.entity.User;

import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.assist.CommonSQL;
import io.vertx.ext.sql.assist.SQLExecute;

public class UserSQL extends CommonSQL<JDBCClient> {
	public UserSQL(SQLExecute<JDBCClient> execute) {
		super(User.class, execute);
	}

}

package org.mirrentools.vertx.sql.assist.examples.sql;

import org.mirrentools.vertx.sql.assist.examples.entity.User;

import io.vertx.ext.sql.assist.CommonSQL;
import io.vertx.ext.sql.assist.SQLExecute;
import io.vertx.jdbcclient.JDBCPool;

public class UserSQL extends CommonSQL<User, JDBCPool> {
	public UserSQL(SQLExecute<JDBCPool> execute) {
		super(execute);
	}
}

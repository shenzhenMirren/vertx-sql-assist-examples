package org.mirrentools.vertx.sql.assist.examples.entity;

import io.vertx.core.MultiMap;
import io.vertx.ext.sql.assist.Table;
import io.vertx.ext.sql.assist.TableColumn;
import io.vertx.ext.sql.assist.TableId;

@Table("uuser")
public class User {
	/** id */
	@TableId("id")
	private Long id;
	/** name */
	@TableColumn("name")
	private String name;
	/** type */
	@TableColumn(value = "type", alias = "tid")
	private Integer type;

	public User() {
		super();
	}

	public User(MultiMap params) {
		super();
		try {
			if (params.get("id") != null) {
				try {
					this.id = new Long(params.get("id"));
				} catch (Exception e) {
					throw new IllegalArgumentException("无法识别参数:id,请检查是否符合要求!");
				}
			}
			this.name = params.get("name");
			if (params.get("tid") != null) {
				try {
					this.type = new Integer(params.get("tid"));
				} catch (Exception e) {
					throw new IllegalArgumentException("无法识别参数:tid,请检查是否符合要求!");
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

}

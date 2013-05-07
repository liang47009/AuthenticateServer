package com.curlymaple.server.module.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int yyid;// yy的id
	private String username;
	private String password;
	private long freeze;// 冻结(-1:永久冻结, 0:未冻结, 大于0:冻结时长)

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYyid() {
		return yyid;
	}

	public void setYyid(int yyid) {
		this.yyid = yyid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getFreeze() {
		return freeze;
	}

	public void setFreeze(long freeze) {
		this.freeze = freeze;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", yyid=" + yyid + ", username=" + username
				+ ", password=" + password + ", freeze=" + freeze + "]";
	}

}

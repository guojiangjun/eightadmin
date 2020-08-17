package com.bootdo.eight.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 16:52:01
 */
public class AppUserDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer userid;
	//
	private String username;
	//
	private String password;
	//
	private String token;
	//
	private Date registertime;
	//
	private String email;
	private String remark;

	private String lastOperateTime;

	private String oftenOperateTime;

	/**
	 * 设置：
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	/**
	 * 获取：
	 */
	public Integer getUserid() {
		return userid;
	}
	/**
	 * 设置：
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置：
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 获取：
	 */
	public String getToken() {
		return token;
	}
	/**
	 * 设置：
	 */
	public void setRegistertime(Date registertime) {
		this.registertime = registertime;
	}
	/**
	 * 获取：
	 */
	public Date getRegistertime() {
		return registertime;
	}
	/**
	 * 设置：
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：
	 */
	public String getEmail() {
		return email;
	}

	public String getLastOperateTime() {
		return lastOperateTime;
	}

	public void setLastOperateTime(String lastOperateTime) {
		this.lastOperateTime = lastOperateTime;
	}

	public String getOftenOperateTime() {
		return oftenOperateTime;
	}

	public void setOftenOperateTime(String oftenOperateTime) {
		this.oftenOperateTime = oftenOperateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

package com.bootdo.eight.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户操作时间记录
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-14 16:20:45
 */
public class UserOperateTimeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//记录id
	private Integer logid;
	//用户id
	private Integer userid;
	//操作时间
	private Date addtime;

	/**
	 * 设置：记录id
	 */
	public void setLogid(Integer logid) {
		this.logid = logid;
	}
	/**
	 * 获取：记录id
	 */
	public Integer getLogid() {
		return logid;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserid() {
		return userid;
	}
	/**
	 * 设置：操作时间
	 */
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	/**
	 * 获取：操作时间
	 */
	public Date getAddtime() {
		return addtime;
	}
}

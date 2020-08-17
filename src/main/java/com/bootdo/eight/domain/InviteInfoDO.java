package com.bootdo.eight.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 17:50:27
 */
public class InviteInfoDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//邀请码
	private String code;
	//是否有效 0否 1是
	private Integer valid;

	/**
	 * 设置：邀请码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：邀请码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：是否有效 0否 1是
	 */
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	/**
	 * 获取：是否有效 0否 1是
	 */
	public Integer getValid() {
		return valid;
	}
}

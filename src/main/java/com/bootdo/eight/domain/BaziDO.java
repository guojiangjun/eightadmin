package com.bootdo.eight.domain;

import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 16:52:01
 */
public class BaziDO extends BaseRowModel implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	//
	private String baziid;
	//
	private Integer userid;
	//
	private String baziname;
	//
	private Integer male;
	//
	private Date solardatetime;
	//
	private String lunardatetime;
	//
	private String bazidata;
	//
	private String remark;
	//
	private String baziconfig;
	//
	private Date createtime;
	//
	private Date updatetime;

	/**
	 * 设置：
	 */
	public void setBaziid(String baziid) {
		this.baziid = baziid;
	}
	/**
	 * 获取：
	 */
	public String getBaziid() {
		return baziid;
	}
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
	public void setBaziname(String baziname) {
		this.baziname = baziname;
	}
	/**
	 * 获取：
	 */
	public String getBaziname() {
		return baziname;
	}
	/**
	 * 设置：
	 */
	public void setMale(Integer male) {
		this.male = male;
	}
	/**
	 * 获取：
	 */
	public Integer getMale() {
		return male;
	}
	/**
	 * 设置：
	 */
	public void setSolardatetime(Date solardatetime) {
		this.solardatetime = solardatetime;
	}
	/**
	 * 获取：
	 */
	public Date getSolardatetime() {
		return solardatetime;
	}
	/**
	 * 设置：
	 */
	public void setLunardatetime(String lunardatetime) {
		this.lunardatetime = lunardatetime;
	}
	/**
	 * 获取：
	 */
	public String getLunardatetime() {
		return lunardatetime;
	}
	/**
	 * 设置：
	 */
	public void setBazidata(String bazidata) {
		this.bazidata = bazidata;
	}
	/**
	 * 获取：
	 */
	public String getBazidata() {
		return bazidata;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：
	 */
	public void setBaziconfig(String baziconfig) {
		this.baziconfig = baziconfig;
	}
	/**
	 * 获取：
	 */
	public String getBaziconfig() {
		return baziconfig;
	}
	/**
	 * 设置：
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * 设置：
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdatetime() {
		return updatetime;
	}


}

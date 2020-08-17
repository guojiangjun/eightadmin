package com.bootdo.eight.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.eight.domain.UserOperateTimeDO;
import com.bootdo.eight.service.UserOperateTimeService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 用户操作时间记录
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-14 16:20:45
 */
 
@Controller
@RequestMapping("/eight/userOperateTime")
public class UserOperateTimeController {
	@Autowired
	private UserOperateTimeService userOperateTimeService;
	
	@GetMapping()
	@RequiresPermissions("eight:userOperateTime:userOperateTime")
	String UserOperateTime(){
	    return "eight/userOperateTime/userOperateTime";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("eight:userOperateTime:userOperateTime")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<UserOperateTimeDO> userOperateTimeList = userOperateTimeService.list(query);
		int total = userOperateTimeService.count(query);
		PageUtils pageUtils = new PageUtils(userOperateTimeList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("eight:userOperateTime:add")
	String add(){
	    return "eight/userOperateTime/add";
	}

	@GetMapping("/edit/{logid}")
	@RequiresPermissions("eight:userOperateTime:edit")
	String edit(@PathVariable("logid") Integer logid,Model model){
		UserOperateTimeDO userOperateTime = userOperateTimeService.get(logid);
		model.addAttribute("userOperateTime", userOperateTime);
	    return "eight/userOperateTime/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("eight:userOperateTime:add")
	public R save( UserOperateTimeDO userOperateTime){
		if(userOperateTimeService.save(userOperateTime)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("eight:userOperateTime:edit")
	public R update( UserOperateTimeDO userOperateTime){
		userOperateTimeService.update(userOperateTime);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("eight:userOperateTime:remove")
	public R remove( Integer logid){
		if(userOperateTimeService.remove(logid)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("eight:userOperateTime:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] logids){
		userOperateTimeService.batchRemove(logids);
		return R.ok();
	}
	
}

package com.bootdo.eight.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bootdo.common.utils.MD5Util;
import com.bootdo.eight.service.UserOperateTimeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.eight.domain.AppUserDO;
import com.bootdo.eight.service.AppUserService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 16:52:01
 */
 
@Controller
@RequestMapping("/eight/user")
public class AppUserController {
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserOperateTimeService userOperateTimeService;
	@GetMapping()
	@RequiresPermissions("eight:user:user")
	String User(){
	    return "eight/user/user";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("eight:user:user")
	public PageUtils list(@RequestParam Map<String, Object> params) throws ParseException {
		//查询列表数据
        Query query = new Query(params);
		params.put("sort","registerTime");
		params.put("order","desc");
		List<AppUserDO> userList = appUserService.list(query);
		for (AppUserDO appUserDO : userList){
			Map<String,Object> map = userOperateTimeService.userOftenOperate(appUserDO.getUserid());
			if(map != null){
				appUserDO.setLastOperateTime(map.get("lastOperateTime").toString());
				appUserDO.setOftenOperateTime(map.get("hour").toString()+"时/操作次数("+map.get("count").toString()+")");
			}

		}
		int total = appUserService.count(query);
		PageUtils pageUtils = new PageUtils(userList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("eight:user:add")
	String add(){
	    return "eight/user/add";
	}

	@GetMapping("/edit/{userid}")
	@RequiresPermissions("eight:user:edit")
	String edit(@PathVariable("userid") Integer userid,Model model){
		AppUserDO user = appUserService.get(userid);
		model.addAttribute("user", user);
	    return "eight/user/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("eight:user:add")
	public R save( AppUserDO user){
		user.setToken(buildToken(user.getUsername(),user.getPassword()));
		user.setRegistertime(new Date());
		if(appUserService.save(user)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("eight:user:edit")
	public R update( AppUserDO user){
		appUserService.update(user);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("eight:user:remove")
	public R remove( Integer userid){
		if(appUserService.remove(userid)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("eight:user:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] userids){
		appUserService.batchRemove(userids);
		return R.ok();
	}
	private String buildToken(String userName, String password) {
		return MD5Util.computeMd5(userName + password);
	}
}

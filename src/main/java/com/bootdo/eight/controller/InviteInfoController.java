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

import com.bootdo.eight.domain.InviteInfoDO;
import com.bootdo.eight.service.InviteInfoService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 17:50:27
 */
 
@Controller
@RequestMapping("/eight/inviteInfo")
public class InviteInfoController {
	@Autowired
	private InviteInfoService inviteInfoService;
	
	@GetMapping()
	@RequiresPermissions("eight:inviteInfo:inviteInfo")
	String InviteInfo(){
	    return "eight/inviteInfo/inviteInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("eight:inviteInfo:inviteInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<InviteInfoDO> inviteInfoList = inviteInfoService.list(query);
		int total = inviteInfoService.count(query);
		PageUtils pageUtils = new PageUtils(inviteInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("eight:inviteInfo:add")
	String add(){
	    return "eight/inviteInfo/add";
	}

	@GetMapping("/edit/{code}")
	@RequiresPermissions("eight:inviteInfo:edit")
	String edit(@PathVariable("code") String code,Model model){
		InviteInfoDO inviteInfo = inviteInfoService.get(code);
		model.addAttribute("inviteInfo", inviteInfo);
	    return "eight/inviteInfo/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("eight:inviteInfo:add")
	public R save( InviteInfoDO inviteInfo){
		if(inviteInfoService.save(inviteInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("eight:inviteInfo:edit")
	public R update( InviteInfoDO inviteInfo){
		inviteInfoService.update(inviteInfo);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("eight:inviteInfo:remove")
	public R remove( String code){
		if(inviteInfoService.remove(code)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("eight:inviteInfo:batchRemove")
	public R remove(@RequestParam("ids[]") String[] codes){
		inviteInfoService.batchRemove(codes);
		return R.ok();
	}
	
}

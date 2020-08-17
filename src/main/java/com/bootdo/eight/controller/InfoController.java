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

import com.bootdo.eight.domain.InfoDO;
import com.bootdo.eight.service.InfoService;
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
@RequestMapping("/eight/info")
public class InfoController {
	@Autowired
	private InfoService infoService;
	
	@GetMapping()
	@RequiresPermissions("eight:info:info")
	String Info(){
	    return "eight/info/info";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("eight:info:info")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<InfoDO> infoList = infoService.list(query);
		int total = infoService.count(query);
		PageUtils pageUtils = new PageUtils(infoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("eight:info:add")
	String add(){
	    return "eight/info/add";
	}

	@GetMapping("/edit/{code}")
	@RequiresPermissions("eight:info:edit")
	String edit(@PathVariable("code") String code,Model model){
		InfoDO info = infoService.get(code);
		model.addAttribute("info", info);
	    return "eight/info/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("eight:info:add")
	public R save( InfoDO info){
		if(infoService.save(info)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("eight:info:edit")
	public R update( InfoDO info){
		infoService.update(info);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("eight:info:remove")
	public R remove( String code){
		if(infoService.remove(code)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("eight:info:batchRemove")
	public R remove(@RequestParam("ids[]") String[] codes){
		infoService.batchRemove(codes);
		return R.ok();
	}
	
}

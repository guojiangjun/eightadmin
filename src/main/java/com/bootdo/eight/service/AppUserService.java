package com.bootdo.eight.service;

import com.bootdo.eight.domain.AppUserDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 16:52:01
 */
public interface AppUserService {
	
	AppUserDO get(Integer userid);
	
	List<AppUserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AppUserDO user);
	
	int update(AppUserDO user);
	
	int remove(Integer userid);
	
	int batchRemove(Integer[] userids);
}

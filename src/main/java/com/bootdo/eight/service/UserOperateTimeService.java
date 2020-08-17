package com.bootdo.eight.service;

import com.bootdo.eight.domain.UserOperateTimeDO;

import java.util.List;
import java.util.Map;

/**
 * 用户操作时间记录
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-14 16:20:45
 */
public interface UserOperateTimeService {
	
	UserOperateTimeDO get(Integer logid);
	
	List<UserOperateTimeDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserOperateTimeDO userOperateTime);
	
	int update(UserOperateTimeDO userOperateTime);
	
	int remove(Integer logid);
	
	int batchRemove(Integer[] logids);

	/**
	 * 查询用户最常登录时间和最后登录时间
	 * @param userId
	 * @return
	 */
	Map<String,Object> userOftenOperate(Integer userId);
}

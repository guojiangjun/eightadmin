package com.bootdo.eight.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.eight.dao.UserOperateTimeDao;
import com.bootdo.eight.domain.UserOperateTimeDO;
import com.bootdo.eight.service.UserOperateTimeService;



@Service
public class UserOperateTimeServiceImpl implements UserOperateTimeService {
	@Autowired
	private UserOperateTimeDao userOperateTimeDao;
	
	@Override
	public UserOperateTimeDO get(Integer logid){
		return userOperateTimeDao.get(logid);
	}
	
	@Override
	public List<UserOperateTimeDO> list(Map<String, Object> map){
		return userOperateTimeDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userOperateTimeDao.count(map);
	}
	
	@Override
	public int save(UserOperateTimeDO userOperateTime){
		return userOperateTimeDao.save(userOperateTime);
	}
	
	@Override
	public int update(UserOperateTimeDO userOperateTime){
		return userOperateTimeDao.update(userOperateTime);
	}
	
	@Override
	public int remove(Integer logid){
		return userOperateTimeDao.remove(logid);
	}
	
	@Override
	public int batchRemove(Integer[] logids){
		return userOperateTimeDao.batchRemove(logids);
	}

	@Override
	public Map<String, Object> userOftenOperate(Integer userId) {
		return userOperateTimeDao.userOftenOperate(userId);
	}

}

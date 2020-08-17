package com.bootdo.eight.service.impl;

import com.bootdo.eight.dao.AppUserDao;
import com.bootdo.system.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.eight.domain.AppUserDO;
import com.bootdo.eight.service.AppUserService;



@Service
public class AppUserServiceImpl implements AppUserService {
	@Autowired
	private AppUserDao userDao;
	
	@Override
	public AppUserDO get(Integer userid){
		return userDao.get(userid);
	}
	
	@Override
	public List<AppUserDO> list(Map<String, Object> map){
		return userDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return userDao.count(map);
	}
	
	@Override
	public int save(AppUserDO user){
		return userDao.save(user);
	}
	
	@Override
	public int update(AppUserDO user){
		return userDao.update(user);
	}
	
	@Override
	public int remove(Integer userid){
		return userDao.remove(userid);
	}
	
	@Override
	public int batchRemove(Integer[] userids){
		return userDao.batchRemove(userids);
	}
	
}

package com.bootdo.eight.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.eight.dao.InviteInfoDao;
import com.bootdo.eight.domain.InviteInfoDO;
import com.bootdo.eight.service.InviteInfoService;



@Service
public class InviteInfoServiceImpl implements InviteInfoService {
	@Autowired
	private InviteInfoDao inviteInfoDao;
	
	@Override
	public InviteInfoDO get(String code){
		return inviteInfoDao.get(code);
	}
	
	@Override
	public List<InviteInfoDO> list(Map<String, Object> map){
		return inviteInfoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return inviteInfoDao.count(map);
	}
	
	@Override
	public int save(InviteInfoDO inviteInfo){
		return inviteInfoDao.save(inviteInfo);
	}
	
	@Override
	public int update(InviteInfoDO inviteInfo){
		return inviteInfoDao.update(inviteInfo);
	}
	
	@Override
	public int remove(String code){
		return inviteInfoDao.remove(code);
	}
	
	@Override
	public int batchRemove(String[] codes){
		return inviteInfoDao.batchRemove(codes);
	}
	
}

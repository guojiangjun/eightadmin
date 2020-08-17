package com.bootdo.eight.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.eight.dao.InfoDao;
import com.bootdo.eight.domain.InfoDO;
import com.bootdo.eight.service.InfoService;



@Service
public class InfoServiceImpl implements InfoService {
	@Autowired
	private InfoDao infoDao;
	
	@Override
	public InfoDO get(String code){
		return infoDao.get(code);
	}
	
	@Override
	public List<InfoDO> list(Map<String, Object> map){
		return infoDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return infoDao.count(map);
	}
	
	@Override
	public int save(InfoDO info){
		return infoDao.save(info);
	}
	
	@Override
	public int update(InfoDO info){
		return infoDao.update(info);
	}
	
	@Override
	public int remove(String code){
		return infoDao.remove(code);
	}
	
	@Override
	public int batchRemove(String[] codes){
		return infoDao.batchRemove(codes);
	}
	
}

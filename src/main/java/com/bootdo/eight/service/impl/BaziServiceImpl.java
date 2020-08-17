package com.bootdo.eight.service.impl;

import com.bootdo.common.utils.excel.BaziExcelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.bootdo.eight.dao.BaziDao;
import com.bootdo.eight.domain.BaziDO;
import com.bootdo.eight.service.BaziService;



@Service
public class BaziServiceImpl implements BaziService {
	@Autowired
	private BaziDao baziDao;
	
	@Override
	public BaziDO get(String baziid){
		return baziDao.get(baziid);
	}
	
	@Override
	public List<BaziDO> list(Map<String, Object> map){
		return baziDao.list(map);
	}@Override
	public List<BaziExcelVo> BaziExcelVoList(Map<String, Object> map){
		return baziDao.BaziExcelVoList(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return baziDao.count(map);
	}
	
	@Override
	public int save(BaziDO bazi){
		return baziDao.save(bazi);
	}
	
	@Override
	public int update(BaziDO bazi){
		return baziDao.update(bazi);
	}
	
	@Override
	public int remove(String baziid){
		return baziDao.remove(baziid);
	}
	
	@Override
	public int batchRemove(String[] baziids){
		return baziDao.batchRemove(baziids);
	}
	
}

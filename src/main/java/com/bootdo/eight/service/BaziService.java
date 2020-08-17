package com.bootdo.eight.service;

import com.bootdo.common.utils.excel.BaziExcelVo;
import com.bootdo.eight.domain.BaziDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 16:52:01
 */
public interface BaziService {
	
	BaziDO get(String baziid);
	
	List<BaziDO> list(Map<String, Object> map);
	List<BaziExcelVo> BaziExcelVoList(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(BaziDO bazi);
	
	int update(BaziDO bazi);
	
	int remove(String baziid);
	
	int batchRemove(String[] baziids);
}

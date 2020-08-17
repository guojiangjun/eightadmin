package com.bootdo.eight.dao;

import com.bootdo.common.utils.excel.BaziExcelVo;
import com.bootdo.eight.domain.BaziDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 16:52:01
 */
@Mapper
public interface BaziDao {

	BaziDO get(String baziid);
	
	List<BaziDO> list(Map<String, Object> map);
	List<BaziExcelVo> BaziExcelVoList(Map<String, Object> map);

	int count(Map<String, Object> map);
	
	int save(BaziDO bazi);
	
	int update(BaziDO bazi);
	
	int remove(String baziId);
	
	int batchRemove(String[] baziids);
}

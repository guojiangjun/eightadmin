package com.bootdo.eight.dao;

import com.bootdo.eight.domain.InfoDO;

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
public interface InfoDao {

	InfoDO get(String code);
	
	List<InfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(InfoDO info);
	
	int update(InfoDO info);
	
	int remove(String code);
	
	int batchRemove(String[] codes);
}

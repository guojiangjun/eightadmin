package com.bootdo.eight.dao;

import com.bootdo.eight.domain.InviteInfoDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 17:50:27
 */
@Mapper
public interface InviteInfoDao {

	InviteInfoDO get(String code);
	
	List<InviteInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(InviteInfoDO inviteInfo);
	
	int update(InviteInfoDO inviteInfo);
	
	int remove(String code);
	
	int batchRemove(String[] codes);
}

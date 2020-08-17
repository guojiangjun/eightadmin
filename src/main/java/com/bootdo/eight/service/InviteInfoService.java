package com.bootdo.eight.service;

import com.bootdo.eight.domain.InviteInfoDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author johnson
 * @email 892702641@qq.com
 * @date 2020-04-10 17:50:27
 */
public interface InviteInfoService {
	
	InviteInfoDO get(String code);
	
	List<InviteInfoDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(InviteInfoDO inviteInfo);
	
	int update(InviteInfoDO inviteInfo);
	
	int remove(String code);
	
	int batchRemove(String[] codes);
}

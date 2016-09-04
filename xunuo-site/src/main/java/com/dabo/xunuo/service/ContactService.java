package com.dabo.xunuo.service;

import java.util.List;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.ContactType;
public interface ContactService {
   
	List<ContactType> getContactByuserId(Long userId) throws SysException;
	
}

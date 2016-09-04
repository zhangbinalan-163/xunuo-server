package com.dabo.xunuo.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.dao.ContactMapper;
import com.dabo.xunuo.dao.ContactTypeMapper;
import com.dabo.xunuo.entity.Contact;
import com.dabo.xunuo.entity.ContactType;
import com.dabo.xunuo.service.ContactService;
@Service
public class ContactServiceImpl implements ContactService {
	  @Autowired
	    private ContactMapper contactMapper;
	  @Autowired
	    private ContactTypeMapper contactTypeMapper;
	@Override
	public List<ContactType> getContactByuserId(Long userId) throws SysException {
	       
		
		List<ContactType> contactTypeList=contactTypeMapper.getContactTypeListbyuser(userId,null);
		  if(contactTypeList!=null &&  contactTypeList.size()>0)
		  {
			  for(int i=0;i<contactTypeList.size();i++)
			  {
				  List<Contact> ContactList=contactMapper.getContactList(userId, contactTypeList.get(i).getId(), null, null);
				  contactTypeList.get(i).setContactList(ContactList);;
			  }
			  
		  }
		
		return contactTypeList;
	}
	  
	  
	  
}

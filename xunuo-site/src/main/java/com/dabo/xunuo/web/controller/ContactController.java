package com.dabo.xunuo.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dabo.xunuo.service.ContactService;import com.dabo.xunuo.service.IDeviceService;
import com.dabo.xunuo.web.vo.RequestContext;
@RequestMapping("/contact")
@Controller
public class ContactController  extends BaseController{

	@Autowired
    private ContactService contactService;

	  @RequestMapping(value = "/getcontactbyuser")
	    @ResponseBody
	    public String regDeviceId() throws Exception {
		    long userId=RequestContext.getUserId();
		    Object obj=contactService.getContactByuserId(userId);
	        return createSuccessResponse(obj);
	    }  
	
}

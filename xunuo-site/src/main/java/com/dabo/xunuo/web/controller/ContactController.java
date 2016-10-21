package com.dabo.xunuo.web.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.entity.ContactFigure;
import com.dabo.xunuo.entity.ContactFigurePro;
import com.dabo.xunuo.entity.ContactType;
import com.dabo.xunuo.service.IContactService;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 联系人相关的接口
 */
@RequestMapping("/contact")
@Controller
public class ContactController  extends BaseController{

	@Autowired
	private IContactService contactService;

	/**
	 * 获取全部联系人形象
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/figure/list")
	@ResponseBody
	public String getFigureList() throws Exception {
		List<ContactFigure> figureList = contactService.getAllContactFigure();
		JSONArray jsonArray=new JSONArray();
		if(!CollectionUtils.isEmpty(figureList)){
			figureList.forEach(contactFigure -> {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("figureId",contactFigure.getId());
				jsonObject.put("name",contactFigure.getName());
				jsonObject.put("headUrl",contactFigure.getHeadUrl());
				jsonArray.add(jsonObject);
			});
		}
		return createSuccessResponse(jsonArray);
	}

	/**
	 * 获取联系人形象属性列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/figure/pro/list")
	@ResponseBody
	public String getFigureInfo() throws Exception {
		int figureId= RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "figureId");
		List<ContactFigurePro> proList = contactService.getFigurePro(figureId);
		JSONArray jsonArray=new JSONArray();
		if(!CollectionUtils.isEmpty(proList)){
			proList.forEach(contactFigurePro -> {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("proId",contactFigurePro.getId());
				jsonObject.put("name",contactFigurePro.getName());
				jsonObject.put("unit",contactFigurePro.getPropUnit());
				jsonArray.add(jsonObject);
			});
		}
		return createSuccessResponse(jsonArray);
	}

	/**
	 * 获取联系人类别列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/type/list/byuser")
	@ResponseBody
	public String getTypeList() throws Exception {
		//当前登录userId
		long userId=RequestContext.getUserId();

		List<ContactType> contactTypeList = contactService.getUserContactType(userId);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total",contactTypeList.size());

		JSONArray jsonArray=new JSONArray();
		if(!CollectionUtils.isEmpty(contactTypeList)){
			contactTypeList.forEach(contactType -> {
				JSONObject contactTypeObj=new JSONObject();
				contactTypeObj.put("id",contactType.getId());
				contactTypeObj.put("sourceType",contactType.getSourceType());
				contactTypeObj.put("name",contactType.getName());
				jsonArray.add(contactTypeObj);
			});
		}
		jsonObject.put("data",jsonArray);

		return createSuccessResponse(jsonObject);
	}
}

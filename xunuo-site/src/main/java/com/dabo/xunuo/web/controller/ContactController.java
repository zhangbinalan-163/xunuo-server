package com.dabo.xunuo.web.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.common.exception.SysException;
import com.dabo.xunuo.entity.*;
import com.dabo.xunuo.service.IContactService;
import com.dabo.xunuo.service.IUserEventService;
import com.dabo.xunuo.util.JsonUtils;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.util.TimeUtils;
import com.dabo.xunuo.web.vo.ContactBatchCreateParam;
import com.dabo.xunuo.web.vo.FigureProSetParam;
import com.dabo.xunuo.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人相关的接口
 */
@RequestMapping("/contact")
@Controller
public class ContactController  extends BaseController{

	@Autowired
	private IContactService contactService;

	@Autowired
	private IUserEventService userEventService;
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
				jsonObject.put("figure_id",contactFigure.getId());
				jsonObject.put("figure_name",contactFigure.getName());
				jsonObject.put("figure_head_url",contactFigure.getHeadUrl());
				jsonArray.add(jsonObject);
			});
		}
		return createSuccessResponse(jsonArray);
	}

	/**
	 * 获取联系人形象信息
	 * 如果该联系人设置了这个形象,那么返回用户设置的值
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/figure/contact/info")
	@ResponseBody
	public String getFigureProInfo() throws Exception {
		int figureId= RequestUtils.getInt(RequestContext.getNotEmptyParamMap(), "figure_id");
		long contactId= RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "contact_id");

		ContactFigure contactFigure = contactService.getContactFigure(figureId);
		if(contactFigure==null){
			throw new SysException("数据不存在",Constants.ERROR_CODE_DATA_NOT_EXSIST);
		}
		//
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("figure_id",contactFigure.getId());
		jsonObject.put("figure_name",contactFigure.getName());
		jsonObject.put("figure_head_url",contactFigure.getHeadUrl());
		//属性信息
		List<ContactFigureProValue> proList = contactService.getFigureProWithValue(contactId,figureId);
		JSONArray jsonArray=new JSONArray();
		if(!CollectionUtils.isEmpty(proList)){
			proList.forEach(contactFigurePro -> {
				JSONObject proValueObject=new JSONObject();
				proValueObject.put("prop_id",contactFigurePro.getFigurePropId());
				proValueObject.put("name",contactFigurePro.getContactFigurePro().getName());
				proValueObject.put("unit",contactFigurePro.getContactFigurePro().getPropUnit());
				proValueObject.put("value",contactFigurePro.getValue());
				jsonArray.add(proValueObject);
			});
		}
		jsonObject.put("props",jsonArray);
		return createSuccessResponse(jsonObject);
	}

	/**
	 * 设置联系人的形象
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/figure/contact/update")
	@ResponseBody
	public String setFigureProInfo() throws Exception {
		String jsonParam = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "data");
		FigureProSetParam proSetParam;
		try {
			proSetParam=JsonUtils.toObject(jsonParam, FigureProSetParam.class);
		}catch (Exception e){
			throw new SysException("参数格式错误",Constants.ERROR_CODE_INVALID_PARAM);
		}

		long contactId=proSetParam.getContact_id();
		int figureId=proSetParam.getFigure_id();

		List<FigureProSetParam.Prop> props = proSetParam.getProps();
		List<ContactFigureProValue> propValueList=new ArrayList<>();
		if(!CollectionUtils.isEmpty(props)){
			props.forEach(prop -> {
				ContactFigureProValue proValue=new ContactFigureProValue();
				proValue.setContactId(contactId);
				proValue.setFigurePropId(prop.getProp_id());
				proValue.setValue(prop.getValue());
				propValueList.add(proValue);
			});
		}
		//设置形象以及形象的属性值
		contactService.setContactFigureProValue(contactId,figureId,propValueList);

		return createDefaultSuccessResponse();
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
				contactTypeObj.put("type_id",contactType.getId());
				contactTypeObj.put("source_type",contactType.getSourceType());
				contactTypeObj.put("name",contactType.getName());
				jsonArray.add(contactTypeObj);
			});
		}
		jsonObject.put("data",jsonArray);

		return createSuccessResponse(jsonObject);
	}

	/**
	 * 创建用户自定义的联系人类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/type/create")
	@ResponseBody
	public String createContactType() throws Exception {
		//当前登录userId
		long userId=RequestContext.getUserId();
		String typeName=RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "name");

		ContactType contactType=new ContactType();
		contactType.setUserId(userId);
		contactType.setSourceType(ContactType.SOURCE_USER);
		contactType.setName(typeName);

		contactService.createContactType(contactType);

		JSONObject jsonObject=new JSONObject();
		jsonObject.put("type_id",contactType.getId());
		return createSuccessResponse(jsonObject);
	}

	/**
	 * 删除用户自定义的联系人类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/type/delete")
	@ResponseBody
	public String deleteContactType() throws Exception {
		//当前登录userId
		long userId=RequestContext.getUserId();
		long typeId=RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "type_id");

		ContactType contactType = contactService.getUserContactTypeById(typeId);
		if(contactType==null){
			throw new SysException("请求数据不存在", Constants.ERROR_CODE_DATA_NOT_EXSIST);
		}
		if(contactType.getSourceType()!=UserEventType.SOURCE_USER
				||contactType.getUserId()!=userId){
			throw new SysException("无权限",Constants.ERROR_CODE_DATA_NO_RIGHT);
		}
		//统计数量
		long contactCount=contactService.countByContactType(typeId);
		if(contactCount>0){
			throw new SysException("该类型下有联系人,不允许删除",Constants.ERROR_CODE_HAS_DATA);
		}
		//
		contactService.deleteContactType(contactType);

		return createDefaultSuccessResponse();
	}

	/**
	 * 批量新增联系人
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create/batch")
	@ResponseBody
	public String batchCreateContact() throws Exception {
		String jsonParam = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "data");
		ContactBatchCreateParam contactBatchCreateParam;
		try {
			contactBatchCreateParam=JsonUtils.toObject(jsonParam, ContactBatchCreateParam.class);
		}catch (Exception e){
			throw new SysException("参数格式错误",Constants.ERROR_CODE_INVALID_PARAM);
		}

		List<Contact> contactList=parseContactList(contactBatchCreateParam);
		contactService.batchCreateContact(contactList);

		return createDefaultSuccessResponse();
	}

	private List<Contact> parseContactList(ContactBatchCreateParam contactBatchCreateParam) {
		List<Contact> contactList=new ArrayList<>();

		List<ContactBatchCreateParam.ContactEntity> contactParamList = contactBatchCreateParam.getContacts();
		if(!CollectionUtils.isEmpty(contactParamList)){
			contactParamList.forEach(contactAddEntity -> {
				Contact contact=parseContact(contactAddEntity);
				contactList.add(contact);
			});
		}
		return contactList;
	}

	private Contact parseContact(ContactBatchCreateParam.ContactEntity contactAddEntity) {
		Contact contact=new Contact();
		contact.setBirthday(contactAddEntity.getBirth()==null?0:contactAddEntity.getBirth().getTime());
		contact.setName(contactAddEntity.getName());
		contact.setHeadImg(contactAddEntity.getHead_url());
		contact.setRemark(contactAddEntity.getRemark());
		contact.setPhone(contactAddEntity.getPhone());
		contact.setFigureId(contactAddEntity.getFigure_id());
		contact.setContactTypeId(contactAddEntity.getType_id());
		//自定义字段
		List<ContactBatchCreateParam.SelfPropEntity> selfProps = contactAddEntity.getSelf_prop();
		if(!CollectionUtils.isEmpty(selfProps)){
			List<ContactPro> proList=new ArrayList<>();
			selfProps.forEach(selfPropEntity -> {
				ContactPro contactPro=new ContactPro();
				contactPro.setName(selfPropEntity.getName());
				contactPro.setValue(selfPropEntity.getValue());
				proList.add(contactPro);
			});
			contact.setContactProList(proList);
		}
		return contact;
	}


	/**
	 * 修改联系人
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public String updateContact() throws Exception {
		String jsonParam = RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "data");
		ContactBatchCreateParam.ContactEntity contactEntity;
		try {
			contactEntity=JsonUtils.toObject(jsonParam, ContactBatchCreateParam.ContactEntity.class);
		}catch (Exception e){
			throw new SysException("参数格式错误",Constants.ERROR_CODE_INVALID_PARAM);
		}

		Contact contact=parseContact(contactEntity);
		contactService.updateContact(contact);

		return createDefaultSuccessResponse();
	}

	/**
	 * 获取联系人详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/info")
	@ResponseBody
	public String getContactInfo() throws Exception {
		long contactId = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "contact_id");

		Contact contactInfo = contactService.getContactById(contactId);
		JSONObject jsonObject=new JSONObject();

		if(contactInfo!=null){
			jsonObject.put("id",contactInfo.getId());
			jsonObject.put("head_url",contactInfo.getHeadImg());
			jsonObject.put("name",contactInfo.getName());
			jsonObject.put("birth",TimeUtils.getDateStrWithoutTime(contactInfo.getBirthday()));
			jsonObject.put("phone",contactInfo.getPhone());
			jsonObject.put("remark",contactInfo.getRemark());

			JSONObject figureObject=new JSONObject();
			ContactFigure contactFigure = contactService.getContactFigure(contactInfo.getFigureId());
			if(contactFigure!=null){
				figureObject.put("id",contactFigure.getId());
				figureObject.put("head_img",contactFigure.getId());
				figureObject.put("name",contactFigure.getName());
			}
			jsonObject.put("figure",figureObject);

			JSONObject typeObject=new JSONObject();
			ContactType contactType = contactService.getUserContactTypeById(contactInfo.getContactTypeId());
			if(contactType!=null){
				typeObject.put("id",contactFigure.getId());
				typeObject.put("name",contactFigure.getName());
			}
			jsonObject.put("type",typeObject);

			List<ContactPro> selfProList = contactInfo.getContactProList();
			JSONArray jsonArray=new JSONArray();
			if(!CollectionUtils.isEmpty(selfProList)){
				selfProList.forEach(selfPro -> {
					JSONObject proObject=new JSONObject();
					proObject.put("id",selfPro.getId());
					proObject.put("name",selfPro.getName());
					proObject.put("value",selfPro.getValue());
					jsonArray.add(proObject);
				});
			}
			jsonObject.put("self_prop",jsonArray);
		}
		return createSuccessResponse(jsonObject);
	}

	/**
	 * 删除联系人
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String deleteContactInfo() throws Exception {
		long contactId = RequestUtils.getLong(RequestContext.getNotEmptyParamMap(), "contact_id");
		Contact contactInfo = contactService.getContactById(contactId);
		if(contactInfo==null){
			throw new SysException("数据不存在",Constants.ERROR_CODE_DATA_NO_RIGHT);
		}
		//删除
		contactService.deleteContactById(contactId);
		//删除用户关联的全部事件
		userEventService.deleteUserEventByContactId(contactId);

		return createDefaultSuccessResponse();
	}
}

package com.dabo.xunuo.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.service.IImageService;
import com.dabo.xunuo.util.RequestUtils;
import com.dabo.xunuo.web.vo.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用接口的controller
 */
@RequestMapping("/common")
@Controller
public class CommonController extends BaseController{

    @Autowired
    private IImageService imageService;

    /**
     * 图片上传
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/img/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImg(@RequestParam("image") MultipartFile file) throws Exception {

        System.out.println(file.getSize());

        String url=imageService.uploadImage(file);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("url",url);
        return createSuccessResponse(jsonObject);
    }

    /**
     * 图片上传
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/app/suggest")
    @ResponseBody
    public String appSuggest() throws Exception {
        String suggest= RequestUtils.getString(RequestContext.getNotEmptyParamMap(), "suggest");
        //TODO 暂时不处理
        return createDefaultSuccessResponse();
    }
}
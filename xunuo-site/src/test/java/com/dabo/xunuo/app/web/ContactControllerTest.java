package com.dabo.xunuo.base.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dabo.xunuo.base.common.Constants;
import com.dabo.xunuo.base.entity.DataResponse;
import com.dabo.xunuo.base.util.JsonUtils;
import com.dabo.xunuo.base.util.SignUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;


/**
 * 联系人接口测试
 * Created by zhangbin on 16/8/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent",locations = {"classpath*:springContext-core.xml","classpath*:springContext-dao.xml"}),
        @ContextConfiguration(name = "child", locations = "classpath*:springContext-mvc.xml")
})
public class ContactControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String deviceId="my_device_id";
    private String clientType="IOS";
    private String version="1.1.1";
    private String sid="KM4VgBDmOx1lRtwL8nG5+9wcvAny8agueu+63eU/Muc=";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getFigureListTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/figure/list")
                        .param("client_type", clientType+"_"+version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("app_key", Constants.APP_KEY)
                .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }


    @Test
    public void getFigureProListTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("figure_id", "2");
        paramMap.put("contact_id", "1");
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/figure/contact/info")
                        .param("client_type", clientType+"_"+version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("figure_id", "1")
                        .param("contact_id", "1")
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }


    @Test
    public void setFigureProListTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        JSONObject jsonObject=new JSONObject();

        JSONArray jsonArray=new JSONArray();

        JSONObject proValue=new JSONObject();
        proValue.put("prop_id",3);
        proValue.put("value","新的值2");
        jsonArray.add(proValue);
        JSONObject proValue2=new JSONObject();
        proValue2.put("prop_id",4);
        proValue2.put("value","new2");
        jsonArray.add(proValue2);

        jsonObject.put("props",jsonArray);
        jsonObject.put("contact_id",1);
        jsonObject.put("figure_id",2);
        String dataJsonStr=JsonUtils.fromObject(jsonObject);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("data", dataJsonStr);
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/figure/contact/update")
                        .param("client_type", clientType+"_"+version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("data", dataJsonStr)
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }
    @Test
    public void contactTypeListTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/type/list/byuser")
                        .param("client_type", clientType+"_"+version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }

    @Test
    public void createEventTypeTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("name", "自定义类型");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/type/create")
                        .param("client_type", clientType+"_"+version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("name", "自定义类型1")
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }

    @Test
    public void deleteEventTypeTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("type_id", "5");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/type/delete")
                        .param("client_type", clientType+"_"+version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("type_id","5")
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }

    @Test
    public void getContactInfo() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("contact_id", "1");
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/info")
                        .param("client_type", clientType+"_"+version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("contact_id","1")
                        .param("timestamp", String.valueOf(timestamp))
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }
}
package com.dabo.xunuo.base.web;

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
 * 事件接口测试
 * Created by zhangbin on 16/8/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent", locations = {"classpath*:springContext-core.xml", "classpath*:springContext-dao.xml"}),
        @ContextConfiguration(name = "child", locations = "classpath*:springContext-mvc.xml")
})
public class UserEventControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String deviceId = "my_device_id";
    private String clientType = "IOS";
    private String version = "1.1.1";
    private String sid = "KM4VgBDmOx1lRtwL8nG5+9wcvAny8agueu+63eU/Muc=";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void eventClassListTest() throws Exception {
        long timestamp = System.currentTimeMillis();
        String nonce = "123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType + "_" + version);
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/event/class/list/byuser")
                        .param("client_type", clientType + "_" + version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }

    @Test
    public void createEventTest() throws Exception {
        long timestamp = System.currentTimeMillis();
        String nonce = "123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("name", "第3个事件");
        paramMap.put("event_time", String.valueOf(timestamp + 10 * 24 * 60 * 60 * 1000L));
        paramMap.put("contact_id", "1");
        paramMap.put("event_class_id", "0");
        paramMap.put("event_class_name", "");
        paramMap.put("remind_interval", "1");
        paramMap.put("remind_interval_type", "0");
        paramMap.put("chinese_calendar_flag", "0");
        paramMap.put("remind_interval_type", "0");
        paramMap.put("remark", "这是备注");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/event/update")
                        .param("client_type", clientType + "_" + version)
                        .param("device_id", deviceId)
                        .header("X-XN-SID", sid)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("name", "第3个事件")
                        .param("event_time", String.valueOf(timestamp + 10 * 24 * 60 * 60 * 1000L))
                        .param("contact_id", "1")
                        .param("event_class_id", "")
                        .param("event_class_name", "")
                        .param("remind_interval", "1")
                        .param("remind_interval_type", "0")
                        .param("chinese_calendar_flag", "0")
                        .param("remark", "这是备注")
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }

    @Test
    public void eventListByUserTest() throws Exception {
        long timestamp = System.currentTimeMillis();
        String nonce = "123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType + "_" + version);
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("page", "1");
        paramMap.put("limit", "10");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/event/list/byuser")
                        .param("client_type", clientType + "_" + version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("page", "1")
                        .param("limit", "10")
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }

    @Test
    public void eventInfoTest() throws Exception {
        long timestamp = System.currentTimeMillis();
        String nonce = "123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType + "_" + version);
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("event_id", "6");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/event/info")
                        .param("client_type", clientType + "_" + version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("event_id", "6")
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }

    @Test
    public void contactNeariestEvent() throws Exception {
        long timestamp = System.currentTimeMillis();
        String nonce = "123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType + "_" + version);
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("contact_id", "1");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/event/nearly")
                        .param("client_type", clientType + "_" + version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("contact_id", "1")
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }
}
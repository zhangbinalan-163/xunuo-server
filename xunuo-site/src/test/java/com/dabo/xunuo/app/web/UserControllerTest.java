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
 * SSO接口测试
 * Created by zhangbin on 16/8/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent",locations = {"classpath*:springContext-core.xml","classpath*:springContext-dao.xml"}),
        @ContextConfiguration(name = "child", locations = "classpath*:springContext-mvc.xml")
})
public class UserControllerTest {

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
    public void userInfoTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/user/info")
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
}
package com.dabo.xunuo.web;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.entity.DataResponse;
import com.dabo.xunuo.util.JsonUtils;
import com.dabo.xunuo.util.SignUtils;
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
 * WEB接口测试
 * Created by zhangbin on 16/8/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent",locations = {"classpath*:springContext-core.xml","classpath*:springContext-dao.xml"}),
        @ContextConfiguration(name = "child", locations = "classpath*:springContext-mvc.xml")
})
public class DemoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void signFilterTest() throws Exception {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("format", "json");
        paramMap.put("city", "上海");
        paramMap.put("latitude", "31.21524");
        paramMap.put("longitude", "121.420033");
        paramMap.put("radius", "2000");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/demo/sign")
                        .param("format", "json")
                        .param("city", "上海")
                        .param("latitude", "31.21524")
                        .param("longitude", "121.420033")
                        .param("radius", "2000")
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap,Constants.APP_KEY,Constants.APP_SECRET));
        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }
}
package com.dabo.xunuo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 图片业务测试
 * Created by zhangbin on 16/9/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:springContext-core.xml","classpath*:springContext-dao.xml"})
public class ImageServiceTest extends AbstractJUnit4SpringContextTests {
    private static Logger logger= LoggerFactory.getLogger(ImageServiceTest.class);

    @Autowired
    private IImageService imageService;

    @Test
    public void saveImage() {

    }
}

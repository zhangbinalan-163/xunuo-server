/**
 * Copyright (C) 2006-2016 Tuniu All rights reserved
 */
package com.dabo.xunuo.base.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dabo.xunuo.base.common.exception.SysException;

/**
 * Date: 2016-12-17
 *
 * @author zhangbinalan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springContext-dao.xml", "classpath:springContext-core.xml"})
public class SsoServiceTest {

    @Autowired
    private ISsoService ssoService;

    @Test
    public void testSmsCode() {
        try {
            ssoService.sendRegCode("18069812065");
        } catch (SysException e) {
            e.printStackTrace();
        }
    }
}

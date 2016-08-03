package com.dabo.xunuo.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dabo.xunuo.cache.Datacache;

public class XunoListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		confinit();
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
    
	private void confinit()
	{ 
		//初始化配置文件
		Datacache.getDatacache().init();
	}
}

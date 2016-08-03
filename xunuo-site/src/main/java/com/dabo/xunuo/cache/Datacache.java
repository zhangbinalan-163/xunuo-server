package com.dabo.xunuo.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Datacache {

	private static Datacache datacache = new Datacache();
	private Map<String, String> configMap = new HashMap<String, String>();
	private String CONFIGPATH = "conf/config.properties";

	public static Datacache getDatacache() {
		return datacache;
	}

	private Datacache() {

	}

	public String getValue(String key) {
		return configMap.get(key);
	}

	public void init() {
		Properties prop = new Properties();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(CONFIGPATH);
		try {
			prop.load(in);
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				configMap.put(key, prop.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {

	}

}

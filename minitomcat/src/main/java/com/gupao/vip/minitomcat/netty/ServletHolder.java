package com.gupao.vip.minitomcat.netty;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServletHolder {

    private static final String DEFAULT_WEB="web.properties";
    static Map<String, String> servletMappingMap = new HashMap<>();
    static Map<String, String> servletClassMap = new HashMap<>();

    static{
        InputStream resource = ServletHolder.class.getClassLoader().getResourceAsStream(DEFAULT_WEB);
        Properties properties = new Properties();
        try {
            properties.load(resource);
            String servletName = properties.getProperty("servlet-name");
            String servletClass = properties.getProperty("servlet-class");
            String servletMapping = properties.getProperty("servlet-mapping");
            servletMappingMap.put(servletMapping, servletName);
            servletClassMap.put(servletName, servletClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

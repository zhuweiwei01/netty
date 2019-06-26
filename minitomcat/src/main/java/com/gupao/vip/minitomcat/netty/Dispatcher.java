package com.gupao.vip.minitomcat.netty;

public class Dispatcher {

    /**
     * 分发
     */
    public void dispatch(Request request, Response response) {
        String url = request.getUrl();
        System.out.println("请求的url" + url);
        if(ServletHolder.servletMappingMap.containsKey(url)) {
            String servletName = ServletHolder.servletMappingMap.get(request.getUrl());
            String clsName = ServletHolder.servletClassMap.get(servletName);
            Servlet servlet = getServletByClassName(clsName);
            servlet.service(request, response);
        } else {
            response.write("404 - Not Found");
        }
    }

    public Servlet getServletByClassName(String clsName){
        try {
            Class<?> clz = Class.forName(clsName);
            Servlet servlet = (Servlet)clz.newInstance();
            return servlet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

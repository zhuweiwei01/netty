package com.gupao.vip.minitomcat.netty.servlet;

import com.gupao.vip.minitomcat.netty.Request;
import com.gupao.vip.minitomcat.netty.Response;
import com.gupao.vip.minitomcat.netty.Servlet;

public class HelloServlet extends Servlet {


    @Override
    protected void doPost(Request request, Response response) {
        doGet(request,response);
    }

    @Override
    protected void doGet(Request request, Response response) {
        String name = request.getParameter("name");
        response.write("Hello " + name);
    }
}

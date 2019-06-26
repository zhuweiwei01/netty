package com.gupao.vip.minitomcat.bio.serverlet;

import com.gupao.vip.minitomcat.bio.Request;
import com.gupao.vip.minitomcat.bio.Response;
import com.gupao.vip.minitomcat.bio.Servlet;

public class HelloServlet extends Servlet {
    @Override
    protected void doPost(Request request, Response response) {

    }

    @Override
    protected void doGet(Request request, Response response) {
        System.out.println("Hello World!");
        response.write("Hello World");
    }
}

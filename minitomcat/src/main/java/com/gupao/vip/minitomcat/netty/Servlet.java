package com.gupao.vip.minitomcat.netty;

public abstract class Servlet {

    public void service(Request request, Response response) {
        if(request.getMethod().equalsIgnoreCase("GET")) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    protected abstract void doPost(Request request, Response response);

    protected abstract void doGet(Request request, Response response);
}

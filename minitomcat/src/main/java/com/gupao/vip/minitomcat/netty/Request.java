package com.gupao.vip.minitomcat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Request {

    private ChannelHandlerContext ctx;
    private HttpRequest request;

    public Request(ChannelHandlerContext ctx, HttpRequest request) {
        this.ctx = ctx;
        this.request = request;
    }

    public String getUrl() {
        return request.uri().split("\\?")[0];
    }

    public String getMethod() {
        return request.method().name();
    }

    public Map<String,List<String>> getParameters() {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        return decoder.parameters();
    }

    public String getParameter(String name) {
        Map<String, List<String>> parameters = this.getParameters();
        List<String> param = parameters.get(name);
        if(null == param) {
            return null;
        } else {
            return param.get(0);
        }
    }

    public static void main(String[] args) {
        String a = "/hello?name=aa";
        String[] split = a.split("\\?");
        System.out.println(Arrays.toString(split));
    }

}

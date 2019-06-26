package com.gupao.vip.minitomcat.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

public class RequestHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest){
            HttpRequest req = (HttpRequest) msg;

            // 转交给我们自己的request实现
            Request request = new Request(ctx,req);
            // 转交给我们自己的response实现
            Response response = new Response(ctx,req);
            // 实际业务处理
            new Dispatcher().dispatch(request, response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

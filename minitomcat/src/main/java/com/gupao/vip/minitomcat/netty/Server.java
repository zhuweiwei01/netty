package com.gupao.vip.minitomcat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class Server {

    private int port;

    public Server() {
        this(8080);
    }

    public Server(int port) {
        this.port = port;
    }


    public void start() {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boosGroup, workGroup)
                    //主线程处理类
                    .channel(NioServerSocketChannel.class)
                    //子线程处理类，Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //客户端初始化处理
                        @Override
                        protected void initChannel(SocketChannel client) throws Exception {
                            client.pipeline().addLast(new HttpResponseEncoder());
                            client.pipeline().addLast(new HttpRequestDecoder());
                            client.pipeline().addLast(new RequestHandler());
                        }
                    })
                    //针对主线程的配置，分配线程最大数量 128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //针对子线程的配置，保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //启动服务
            ChannelFuture f = server.bind(port).sync();
            System.out.println("Tomcat 已启动，监听端口：" + port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

}

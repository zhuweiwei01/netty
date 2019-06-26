package com.gupao.vip.minitomcat.netty;

public class TomcatApp {

    public static void main(String[] args) {
        new Server(8081).start();
    }
}

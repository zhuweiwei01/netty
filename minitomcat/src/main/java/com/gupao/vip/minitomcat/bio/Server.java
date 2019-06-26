package com.gupao.vip.minitomcat.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private int port;
    private final int DEFAULT_PORT = 8080;


    public Server() {
        this.port = DEFAULT_PORT;
    }

    public Server(int port) {
        this.port = port;
    }

    public void listen() {
        try {
            ServerSocket server = new ServerSocket(port);
            while(true) {
                Socket client = server.accept();
                new RequestHandler(client).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

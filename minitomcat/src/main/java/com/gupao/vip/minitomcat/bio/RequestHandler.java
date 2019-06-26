package com.gupao.vip.minitomcat.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {

    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        OutputStream os = null;
        try {
            in = socket.getInputStream();
            os = socket.getOutputStream();
            Request request = new Request(in);
            Response response = new Response(os);
            new Dispatcher().dispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

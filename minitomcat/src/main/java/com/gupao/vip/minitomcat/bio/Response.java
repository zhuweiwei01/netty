package com.gupao.vip.minitomcat.bio;

import java.io.OutputStream;

public class Response {

    private OutputStream os;

    public Response(OutputStream os) {
        this.os = os;
    }

    public void write(String s) {
        //用的是HTTP协议，输出也要遵循HTTP协议
        //给到一个状态码 200
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("HTTP/1.1 200 OK\n")
                    .append("Content-Type: text/html;\n")
                    .append("\r\n")
                    .append(s);
            os.write(sb.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.gupao.vip.minitomcat.bio;

import java.io.IOException;
import java.io.InputStream;

public class Request {

    private String url;
    private String method;

    public Request(InputStream in) {
        try {
            byte[] buffer = new byte[1024];
            int len;
            String content = null;
            if((len=in.read(buffer)) > 0) {
                content = new String(buffer, 0, len);
            }

            String line = content.split("\\n")[0];
            String [] arr = line.split("\\s");

            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }
}

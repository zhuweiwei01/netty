package com.gupao.rpc.provider;

import com.gupao.rpc.RpcProtocol;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class ProtocolHandler implements Runnable {

    private Socket socket;
    private Map<String, Object> handlerMap;

    public ProtocolHandler(Map<String, Object> handlerMap, Socket socket) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            RpcProtocol protocol = (RpcProtocol) ois.readObject();
            Object result = invoke(protocol);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcProtocol protocol) throws Exception {
        Class clazz=Class.forName(protocol.getClassName()); //跟去请求的类进行加载
        String className = clazz.getName();
        String version = protocol.getVersion();
        if(!StringUtils.isEmpty(version)) {
            className = className + "-" + version;
        }

        Object service = handlerMap.get(className);
        if(StringUtils.isEmpty(service)) {
            throw new RuntimeException("Service Not Found" + className);
        }

        Object[] parameters = protocol.getParameters();//拿到客户端的请求参数
        Class<?>[] types = new Class[parameters.length];//获取参数的类型
        if(parameters != null && parameters.length > 0) { //有请求参数
            for(int i=0;i<parameters.length;i++) {
                types[i] = parameters[i].getClass();
            }
        }

        Method method=clazz.getMethod(protocol.getMethodName(),types); //sayHello, saveUser找到这个类中的方法
        Object result=method.invoke(service,parameters);//HelloServiceImpl 进行反射调用
        return result;
    }
}

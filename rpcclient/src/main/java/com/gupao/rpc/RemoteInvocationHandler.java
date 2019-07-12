package com.gupao.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {

    private int port;
    private String address;

    public RemoteInvocationHandler(String address, int port) {
        this.port = port;
        this.address = address;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcProtocol rpcProtocol = new RpcProtocol();
        rpcProtocol.setClassName(method.getDeclaringClass().getName());
        rpcProtocol.setParameters(args);
        rpcProtocol.setMethodName(method.getName());
        rpcProtocol.setVersion("v2.0");
        RpcNetTransport rpcNetTransport = new RpcNetTransport();
        Object result = rpcNetTransport.send(rpcProtocol, address, port);
        return result;
    }
}

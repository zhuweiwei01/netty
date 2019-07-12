package com.gupao.rpc.provider;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Component
public class RpcProxyServer implements ApplicationContextAware, InitializingBean {

    private int port;

    private final ExecutorService pool = Executors.newCachedThreadPool();
    private Map<String ,Object> handlerMap = new HashMap<String, Object>();

    public RpcProxyServer(int port) {
        this.port = port;
    }

    /**
     * 这个方式是实现InitializingBean的方法
     * 在初始化bean的时候spring容器会调用该方法
     * 我们在这里做服务监听
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket =null;
        try {
            while (true) {
                socket = serverSocket.accept();
                pool.execute(new ProtocolHandler(handlerMap, socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(socket != null) {
                socket.close();
            }
        }
    }

    /**
     * 初始化handleMap,容器启动时初始化
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取添加了RPCService注解的类
        Map<String, Object> rpcServiceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(rpcServiceMap != null && !rpcServiceMap.isEmpty()) {
            for(Object serviceBean : rpcServiceMap.values()) {
                RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
                String value = rpcService.value().getName();//备注注解内填写的value值
                String version = rpcService.version();
                if(!StringUtils.isEmpty(version)) {
                    value = value + "-" + version;
                }

                handlerMap.put(value, serviceBean);
            }
        }
    }
}

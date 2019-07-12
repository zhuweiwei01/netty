package com.gupao.rpc;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        RpcProxyClient rpcProxyClient=new RpcProxyClient();

        HelloService iHelloService=rpcProxyClient.clientProxy(HelloService.class,"localhost",8080);

        String result=iHelloService.sayHello(null);
        System.out.println(result);
    }
}

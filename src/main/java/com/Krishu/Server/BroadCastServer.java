package com.Krishu.Server;

import com.Krishu.BroadCast.BroadCastEndPoint;
import jakarta.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

public class BroadCastServer {
    public static void main(String[] args) throws DeploymentException, InterruptedException {
        Server server=new Server("localhost",8080,"/",null, BroadCastEndPoint.class);
        server.start();
        System.out.println("Server has been started");
        Thread.currentThread().join();
    }
}

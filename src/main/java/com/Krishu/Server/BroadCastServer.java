package com.Krishu.Server;

import com.Krishu.BroadCast.BroadCastEndPoint;
import jakarta.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

public class BroadCastServer {
    public static void main(String[] args) throws DeploymentException, InterruptedException {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT","8080"));
        Server server = new Server("0.0.0.0", port, "/", null, BroadCastEndPoint.class);
        server.start();
        System.out.println("Server has been started");
        Thread.currentThread().join();
    }
}

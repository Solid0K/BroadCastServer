package com.Krishu.Client;

import jakarta.websocket.*;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

@ClientEndpoint
public class BroadCastClient {
    private Session session;

    @OnOpen
    public void onOpen(Session session){
        this.session=session;
    }

    @OnMessage
    public void onMessage(String message){
        System.out.println("Received message: "+message);
    }

    public void send(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    public static void main(String[] args) throws DeploymentException, IOException {
        WebSocketContainer container=ContainerProvider.getWebSocketContainer();
        BroadCastClient client=new BroadCastClient();
        container.connectToServer(client, URI.create("ws://localhost:8080/chat"));
        System.out.println("Connected to the server");
        Scanner scanner=new Scanner(System.in);
        while(true){
            String message=scanner.nextLine();
            client.send(message);
        }
    }
}

package com.Krishu.BroadCast;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class BroadCastEndPoint {
    private static Set<Session> clients=ConcurrentHashMap.newKeySet();

    @OnOpen
    public void onOpen(Session client){
        clients.add(client);
        System.out.println("Client connected: "+client.getId());
    }

    @OnMessage
    public void sendMessage(String message,Session sender){
        System.out.println("Received Message: "+message);
        for(Session client:clients){
            if(!client.equals(sender)){
                client.getAsyncRemote().sendText(message);
            }
        }
    }

    @OnClose
    public void OnClose(Session client){
        clients.remove(client);
        System.out.println("Remove Client: "+client.getId());
    }

    @OnError
    public void onError(Session client,Throwable throwable){
        clients.remove(client);
        throwable.printStackTrace();
    }
}

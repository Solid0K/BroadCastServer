package com.Krishu.BroadCast;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class BroadCastEndPoint {
    private static final Map<Session,String> clientMap=new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session client){
        clientMap.put(client,"Anonymous");
        System.out.println("Client connected: "+client.getId());
    }

    @OnMessage
    public void Command(String message,Session sender){
        System.out.println("Received Message: "+message);
        if(message.startsWith("/username")){
            String username=message.substring(10);
            clientMap.put(sender,username);
            broadCast(" has joined",sender);
            return;
        }
        if(message.equals("/users")){
            String users=String.join(", ", clientMap.values());
            sender.getAsyncRemote().sendText("Active users: "+users);
            return;
        }
        if(message.startsWith("/msg ")){
            String[] parts=message.split(" ",3);
            if(parts.length < 3){
                sender.getAsyncRemote()
                        .sendText("Usage: /msg <user> <message>");
                return;
            }
            String username=parts[1];
            String messageToSend=parts[2];
            Session receiver=getReceiver(username);
            if(receiver==null){
                sender.getAsyncRemote().sendText("User not found");
            }else{
                receiver.getAsyncRemote().sendText("[PRIVATE] "
                        + clientMap.get(sender)
                        + ": "
                        + messageToSend);
            }
            return;
        }
        broadCast(message,sender);
    }

    private Session getReceiver(String name){
        for(Map.Entry<Session, String> entry:clientMap.entrySet()){
            if(entry.getValue().equals(name)){
                return entry.getKey();
            }
        }
        return null;
    }

    private void broadCast(String message,Session sender){
        for(Session client:clientMap.keySet()){
            if(!client.equals(sender)){
                client.getAsyncRemote().sendText(clientMap.get(sender)+": "+message);
            }
        }
    }

    @OnClose
    public void OnClose(Session client){
        broadCast(" has lefted",client);
        clientMap.remove(client);
        System.out.println("Remove Client: "+client.getId());
    }

    @OnError
    public void onError(Session client,Throwable throwable){
        clientMap.remove(client);
        throwable.printStackTrace();
    }
}

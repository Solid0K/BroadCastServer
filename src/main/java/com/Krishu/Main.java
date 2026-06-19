package com.Krishu;

import com.Krishu.Client.BroadCastClient;
import com.Krishu.Server.BroadCastServer;
import jakarta.websocket.DeploymentException;

import java.io.IOException;

public class Main
{
    public static void main( String[] args ) throws DeploymentException, InterruptedException, IOException {
        if(args.length==0){
            System.out.println("Usage: start or connect");
            return;
        }
        switch(args[0]){
            case "start":{
                BroadCastServer.main(new String[] {});
                break;
            }
            case "connect":{
                BroadCastClient.main(new String[] {});
                break;
            }
            default:{
                System.out.println("Not an option");
            }
        }
    }
}

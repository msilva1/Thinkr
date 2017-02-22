package com.app.thinkr.chatbox.com.app.fmk.msg.ws;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * Created by Kent on 10/11/2016.
 */
@ClientEndpoint
public class WebSocketClient {

    @OnOpen
    public void onOpen(Session session, EndpointConfig conf) {
        System.out.println("Client connection opened!");
        try {
            session.getBasicRemote().sendText("Kent!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Client connection closed!");
    }

    @OnMessage
    public String onMessage(String msg) throws IOException {
        System.out.println(msg);
        return msg;
    }

    @OnError
    public void onError(Session session, Throwable t) {
        System.out.println("On error");
    }
}

package com.android.thinkr.ws;

/**
 * Created by Kent on 1/3/2016.
 */

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * Web Socket client
 * Connects to the server using the specified address and
 * DRAFT 17 of the WebSocket specification.
 *
 * Current implementation echoes the message/data sent from clients.
 * Goal is to process information and update compass/waypoint as applicable
 */
public class WsClient extends WebSocketClient {

    /**
     * Create a new WebSocket client using the specified address.
     * Using WebSocket draft 17
     *
     * @param address the server websocket address. Protocol starts with ws://
     * @see Draft_17
     */
    public WsClient(URI address) {
        super(address, new Draft_17());
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        print("Connection established.");
    }

    @Override
    public void onMessage(final String message) {
        print(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        print("Connection closed.");
    }

    @Override
    public void onError(final Exception ex) {
        print("Error: " + ex.getMessage());
    }

    /**
     * Prints the specified message to the main comm view.
     * This can be invoked from a background thread.
     * @param message the message to be displayed
     */
    private void print(final String message) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                commText.append(message + System.lineSeparator());
//                commText.setSelection(commText.getText().length());
//            }
//        });
    }
}

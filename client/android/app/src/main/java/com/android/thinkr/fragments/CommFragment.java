package com.android.thinkr.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.thinkr.R;
import com.android.thinkr.databinding.FragmentCommBinding;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

public class CommFragment extends BaseFragment {


    private static final String SERVER_ADDRESS = "ws://52.34.251.80:9005";

    private WebSocketClient conn;
    private EditText commText;
    private Button approveButton;
    private Button rejectButton;

    private Timer timer;
    private FragmentCommBinding binding;

    public CommFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = DataBindingUtil.bind(view);

        commText =  binding.webSocketComm;
        approveButton = binding.approveButton;
        rejectButton = binding.rejectButton;

        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send("lock");
                Toast.makeText(
                    getContext(), "Assignment not completed!", Toast.LENGTH_LONG).show();
            }
        });

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send unlock to web socket
                send("unlock");
                Toast.makeText(
                        getContext(), "Assignment completed!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        commText = new EditText(getContext());

        if (conn != null) {
            if (conn.getConnection().isOpen()) {
                print("Disconnecting...");
                conn.close();
                return;
            }
        }

        URI uri = null;
        try {
            uri = new URI(SERVER_ADDRESS);
        } catch (Exception e) {
            Snackbar.make(
                    getView(),
                    e.getMessage(),
                    Snackbar.LENGTH_INDEFINITE);
        }

        if (uri == null) return;

        // Create the connection
        conn = new WsClient(uri);
        conn.connect();


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        send("alarm-check");
                        //Toast.makeText(getActivity(), "alarm-check", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }, 1000, 5000);
    }

    /**
     * Sends a message/data to the server.
     * The current server implementation effectively broadcasts this message to all connected clients.
     */
    private void send(String message) {
        if (conn != null && conn.getConnection().isOpen()) {
            conn.send(message);
        } else {
            commText.append("Connection is inactive" + "\n");
        }
    }

    /**
     * Prints the specified message to the main comm view.
     * This can be invoked from a background thread.
     * @param message the message to be displayed
     */
    private void print(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                commText.append(message + "\n");
                commText.setSelection(commText.getText().length());
            }
        });
    }

    /**
     * Prints the specified message to the main comm view.
     * This can be invoked from a background thread.
     * @param message the message to be displayed
     */
    private void alert(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                binding.tamperedTextView.setText("Tampered!");
                binding.tamperedTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getFragmentTitle() {

        return R.string.approve_assignment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comm;
    }

    /**
     * Web Socket client
     * Connects to the server using the specified address and
     * DRAFT 17 of the WebSocket specification.
     *
     * Current implementation echoes the message/data sent from clients.
     * Goal is to process information and update compass/waypoint as applicable
     */
    private class WsClient extends WebSocketClient {

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

            if(message.toLowerCase().contains("alarm")) {
                if(message.toLowerCase().contains("alert")) {
                    alert(message);
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.tamperedTextView.setText("");
                            binding.tamperedTextView.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            } else {
                print(message);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            print("Connection closed.");
        }

        @Override
        public void onError(final Exception ex) {
            print("Error: " + ex.getMessage());
        }
    }
}

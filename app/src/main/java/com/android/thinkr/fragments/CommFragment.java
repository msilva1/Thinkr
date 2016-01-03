package com.android.thinkr.fragments;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.thinkr.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CommFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SERVER_ADDRESS = "ws://52.34.251.80:9005";

    private WebSocketClient conn;
    private EditText commText;
    private OnFragmentInteractionListener mListener;

    public CommFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommFragment newInstance(String param1, String param2) {
        CommFragment fragment = new CommFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    }

    private void send(Location location) {
        if (conn != null && conn.getConnection().isOpen()) {

            send("Track sent to server.");
        } else {
            commText.append("Connection is inactive" + "\n");
        }
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comm, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
    }
}

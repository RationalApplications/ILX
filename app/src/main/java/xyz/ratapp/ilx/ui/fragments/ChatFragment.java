package xyz.ratapp.ilx.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.info.InfoController;
import xyz.ratapp.ilx.controllers.interfaces.ListSettable;
import xyz.ratapp.ilx.data.dao.Order;
import xyz.ratapp.ilx.ui.activities.InfoActivity;
import xyz.ratapp.ilx.ui.adapters.MessagesAdapter;

public class ChatFragment extends Fragment
        implements ListSettable<Order.Message> {

    private List<Order.Message> messages;
    private ImageView send;
    private EditText textField;
    private RecyclerView rvChat;
    private String btnSendText;
    private InfoController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        send = v.findViewById(R.id.btnSend);
        textField = v.findViewById(R.id.etMessage);
        rvChat = v.findViewById(R.id.rvMessages);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
    }

    public void setController(InfoController controller) {
        this.controller = controller;
    }

    public void setBtnSendText(String btnSendText) {
        this.btnSendText = btnSendText;
    }

    private void setupUI() {
        //work with btn
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();

            }
        });

        if(messages != null) {
            setupData();
        }
    }

    private void sendMessage() {
        if (textField != null && send != null &&
                textField.getText() != null) {
            String text = textField.getText().toString();
            Log.e("MyTag", text);
            if(text.isEmpty()) {
                //TODO: hardcoded
                Toast.makeText(getContext(),
                        "Введите сообщение!",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                //send message to controller
                if(controller != null) {
                    controller.sendMessage(text);
                    textField.setText("");  
                }
            }
        }
    }

    @Override
    public void setData(List<Order.Message> data) {
        this.messages = data;
        setupData();
    }

    private void setupData() {

        if(messages != null && rvChat != null) {
            GridLayoutManager glm = new GridLayoutManager(getActivity(), 1);
            rvChat.setLayoutManager(glm);
            rvChat.setAdapter(new MessagesAdapter(getActivity(), messages));
        }
    }

    public void callToSendMessage() {
        textField.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textField, InputMethodManager.SHOW_IMPLICIT);
    }
}

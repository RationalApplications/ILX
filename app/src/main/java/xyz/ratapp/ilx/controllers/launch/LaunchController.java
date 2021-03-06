package xyz.ratapp.ilx.controllers.launch;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Objects;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.DataController;
import xyz.ratapp.ilx.ui.activities.DetailsActivity;
import xyz.ratapp.ilx.ui.activities.InfoActivity;
import xyz.ratapp.ilx.ui.activities.LaunchActivity;
import xyz.ratapp.ilx.ui.activities.MainActivity;
import xyz.ratapp.ilx.ui.views.CodeInput;

/**
 * Created by timtim on 15/08/2017.
 */

public class LaunchController {

    private LaunchActivity activity;
    private DataController data;
    private boolean firstStart = false;
    private Intent startIntent;

    public LaunchController(LaunchActivity launchActivity) {
        this.activity = launchActivity;
        startIntent = activity.getIntent();
        data = DataController.getInstance();
        firstStart = data.getPrefs(this);
        setupData();
    }

    private void setupData() {
        if(firstStart) {
            activity.setAuthorizeScreen();

            CodeInput passwordCode = activity.findViewById(R.id.ciPassword);
            passwordCode.setCodeReadyListener(new CodeInput.codeReadyListener() {
                @Override
                public void onCodeReady(String code) {
                    activity.onStartLogin();
                    data.authAccessCode(LaunchController.this, code);
                }
            });
        }
    }

    public void authFailed(String throwable) {
        if (firstStart) {
            Toast.makeText(activity,
                    throwable,
                    Toast.LENGTH_LONG).show();
            activity.onLoginFailed();
        }
        else {
            firstStart = true;
            setupData();
        }
    }

    /**
     * send app to the next Screen
     */
    public void next() {
        String type = startIntent.getStringExtra("type");
        String mdKey = startIntent.getStringExtra("md_key");
        Intent next = new Intent(activity, MainActivity.class);

        if(type != null && !type.isEmpty() ||
                (type != null && !type.isEmpty() &&
                        mdKey != null && !mdKey.isEmpty())) {
            next.putExtra("type", type);

            if (type.equals("order_info") ||
                    type.equals("order_chat")) {
                next.putExtra("md_key", mdKey);
            }
        }

        activity.startActivity(next);
        activity.finish();
    }

    public Context getContext() {
        return activity;
    }
}

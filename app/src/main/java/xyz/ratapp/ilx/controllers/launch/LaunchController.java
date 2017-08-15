package xyz.ratapp.ilx.controllers.launch;

import android.content.Intent;
import android.widget.Toast;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.data.DataController;
import xyz.ratapp.ilx.ui.activities.LaunchActivity;
import xyz.ratapp.ilx.ui.activities.MainActivity;
import xyz.ratapp.ilx.ui.views.CodeInput;

/**
 * Created by timtim on 15/08/2017.
 */

public class LaunchController {

    private LaunchActivity activity;
    private DataController data;

    public LaunchController(LaunchActivity launchActivity) {
        this.activity = launchActivity;
        data = DataController.getInstance();
        setupData();
    }

    private void setupData() {
        CodeInput passwordCode = activity.findViewById(R.id.ciPassword);

        passwordCode.setCodeReadyListener(new CodeInput.codeReadyListener() {
            @Override
            public void onCodeReady(String code) {
                activity.onStartAuth();
                data.authAccessCode(LaunchController.this, code);
            }
        });
    }

    public void authSucceseed() {
        next();
    }

    public void authFailed(String throwable) {
        Toast.makeText(activity,
                throwable,
                Toast.LENGTH_LONG).show();
        activity.onAuthFailed();
    }

    /**
     * send app to the next Screen
     */
    public void next() {
        Intent next = new Intent(activity, MainActivity.class);
        activity.startActivity(next);
        activity.finish();
    }
}

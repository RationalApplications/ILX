package xyz.ratapp.ilx.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.view.CodeInput;

public class LaunchActivity extends AppCompatActivity {

    private static final String TEMP_PASS = "123455";
    CodeInput passwordCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        passwordCode = findViewById(R.id.ci_password);
        passwordCode.setCodeReadyListener(new CodeInput.codeReadyListener() {
            @Override
            public void onCodeReady(Character[] code) {
                LoginTask loginTask = new LoginTask();

                StringBuilder sb = new StringBuilder(passwordCode.getCode().length);
                for(Character c : passwordCode.getCode()) {
                    sb.append(c);
                }
                String string = sb.toString();

                loginTask.execute(string);

                //hide keyboard
                View view = LaunchActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }


    //TODO: Send "startLogin" to controller
    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbLoading.setVisibility(View.VISIBLE);
        }

        protected Boolean doInBackground(String... params) {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return TEMP_PASS.equals(params[0]);
        }

        protected void onPostExecute(Boolean result) {
            pbLoading.setVisibility(View.GONE);

            if (result){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.toast_wrong_password , Toast.LENGTH_LONG).show();
            }
        }
    }


}


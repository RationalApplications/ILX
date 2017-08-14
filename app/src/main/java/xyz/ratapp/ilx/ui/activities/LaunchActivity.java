package xyz.ratapp.ilx.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;
import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.ui.views.CodeInput;

public class LaunchActivity extends AppCompatActivity {

    private static final String TEMP_PASS = "123455";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        CodeInput passwordCode = findViewById(R.id.ci_password);
        passwordCode.setInputType(InputType.TYPE_CLASS_NUMBER);
        passwordCode.setCodeReadyListener(new CodeInput.codeReadyListener() {
            @Override
            public void onCodeReady(String code) {
                LoginTask loginTask = new LoginTask();
                loginTask.execute(code);

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

        ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pbLoading);

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
                Toast.makeText(getApplicationContext(), R.string.wrong_password, Toast.LENGTH_LONG).show();
            }
        }
    }


}


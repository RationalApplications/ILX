package xyz.ratapp.ilx.view.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.glomadrian.codeinputlib.CodeInput;

import java.util.concurrent.TimeUnit;

import xyz.ratapp.ilx.R;

public class LaunchActivity extends AppCompatActivity {

    private static final String TEMP_PASS = "123455" ;
    CodeInput passwordCode;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        passwordCode = (CodeInput) findViewById(R.id.ci_password);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginTask loginTask = new LoginTask();

                StringBuilder sb = new StringBuilder(passwordCode.getCode().length);
                for(Character c : passwordCode.getCode()) {
                    sb.append(c);
                }
                String string = sb.toString();

                loginTask.execute(string);
            }
        });
    }


    private class LoginTask extends AsyncTask<String, Void, Integer> {

        ProgressBar pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btn_login.setVisibility(View.GONE);
            pbLoading.setVisibility(View.VISIBLE);
        }

        protected Integer doInBackground(String... params) {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (TEMP_PASS.equals(params[0])){
                return 1;
            }
            else {
                System.out.println(params[0]);
                return 0;
            }
        }

        protected void onPostExecute(Integer result) {
            pbLoading.setVisibility(View.GONE);
            btn_login.setVisibility(View.VISIBLE);
            if (result == 1){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else{
                Toast.makeText(getApplicationContext(), R.string.toast_wrong_password , Toast.LENGTH_LONG).show();
            }
        }
    }


}


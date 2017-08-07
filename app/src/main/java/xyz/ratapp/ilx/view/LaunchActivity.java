package xyz.ratapp.ilx.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.github.glomadrian.codeinputlib.CodeInput;

import xyz.ratapp.ilx.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        CodeInput passwordCode = (CodeInput) findViewById(R.id.ci_password);
    }

}


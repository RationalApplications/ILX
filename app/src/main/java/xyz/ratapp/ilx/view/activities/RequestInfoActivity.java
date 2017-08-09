package xyz.ratapp.ilx.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.data.dao.Request;

public class RequestInfoActivity extends AppCompatActivity {

    private static final String STR_TITLE = "title";
    public static  final String STR_COST = "cost";
    public static  final String STR_COMMISSION = "commission";
    private static final String STR_COMMENT = "comment";
    private static final String STR_DIFFICULT = "difficult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_info);
    }

    public static Intent getIntent(Request request){
        Intent i = new Intent();
        i.putExtra(STR_COST, request.getCost());
        i.putExtra(STR_COMMISSION, request.getCommission());
        i.putExtra(STR_TITLE, request.getAddress());
        i.putExtra(STR_COMMENT, request.getComment());
        i.putExtra(STR_DIFFICULT, request.getDifficult());
        return i;
    }
}

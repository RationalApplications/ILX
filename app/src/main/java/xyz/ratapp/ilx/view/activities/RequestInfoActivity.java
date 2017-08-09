package xyz.ratapp.ilx.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xyz.ratapp.ilx.R;
import xyz.ratapp.ilx.controllers.main.RequestInfoController;
import xyz.ratapp.ilx.data.dao.Request;

public class RequestInfoActivity extends AppCompatActivity {

    public static final String SHOW_DETAILS_OF_STOCK_REQUEST_ACTION =
            "xyz.ratapp.ilx.SHOW_DETAILS_OF_STOCK_REQUEST_ACTION";

    public static final String STR_TITLE = "title";
    public static  final String STR_COST = "cost";
    public static  final String STR_COMMISSION = "commission";
    public static final String STR_COMMENT = "comment";
    public  static final String STR_DIFFICULT = "difficult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_info);

        RequestInfoController controller = new RequestInfoController(this);
    }

    public static Intent getIntent(Request request){
        Intent i = new Intent();
        i.setAction(SHOW_DETAILS_OF_STOCK_REQUEST_ACTION);
        i.putExtra(STR_COST, request.getCost());
        i.putExtra(STR_COMMISSION, request.getCommission());
        i.putExtra(STR_TITLE, request.getAddress());
        i.putExtra(STR_COMMENT, request.getComment());
        i.putExtra(STR_DIFFICULT, request.getDifficult());

        return i;
    }
}

package xyz.ratapp.ilx.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_request_info.*
import kotlinx.android.synthetic.main.item_stock_request.*
import xyz.ratapp.ilx.R
import xyz.ratapp.ilx.controllers.info.InfoController
import xyz.ratapp.ilx.data.dao.Request

class RequestInfoActivity : InfoActivity() {


    companion object {

        val SHOW_DETAILS_OF_STOCK_REQUEST_ACTION = "xyz.ratapp.ilx.SHOW_DETAILS_OF_STOCK_REQUEST_ACTION"

        val STR_TITLE = "title"
        val STR_COST = "cost"
        val STR_COMMISSION = "commission"
        val STR_COMMENT = "comment"
        val STR_DIFFICULT = "difficult"

        fun getIntent(id: String, request: Request): Intent {
            val i = Intent()
            i.action = SHOW_DETAILS_OF_STOCK_REQUEST_ACTION
            i.putExtra("id", id)
            i.putExtra(STR_COST, request.cost)
            i.putExtra(STR_COMMISSION, request.commission)
            i.putExtra(STR_TITLE, request.address)
            i.putExtra(STR_COMMENT, request.comment)
            i.putExtra(STR_DIFFICULT, request.difficult)

            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getIntExtra("THEME", R.style.AppTheme_Active))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_info)
        setupUI()
        InfoController(this)
    }

    fun setupUI() {
        bindData()

        swipeAccept.setOnStateChangeListener {
            val activity = this@RequestInfoActivity

            swipeAccept.isEnabled = false
            val tv = TextView(activity)
            tv.setText(R.string.accepted)
            tv.gravity = Gravity.CENTER
            tv.setTextColor(activity.resources.getColor(R.color.text_color))
            swipeAccept.addView(tv, RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT))
        }
    }

    fun bindData() {
        val data = intent

        val title = data.getStringExtra(STR_TITLE)
        val cost = data.getStringExtra(STR_COST)
        var commission = data.getStringExtra(STR_COMMISSION)
        val comment = data.getStringExtra(STR_COMMENT)
        val difficult = data.getIntExtra(STR_DIFFICULT, -1)
        val delivery = getString(R.string.delivery_mask, cost)
        commission = getString(R.string.delivery_mask, commission)

        tvDeliveryCost.text = delivery
        tvCommission.text = commission

        tvCost.text = cost
        tvComment.text = comment
        tvTitle.text = title
        if(difficult != -1) {
            vDifficult.setBackgroundColor(difficult)
        }
    }
}

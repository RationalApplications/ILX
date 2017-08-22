package xyz.ratapp.ilx.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Visibility
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_request_info.*
import kotlinx.android.synthetic.main.item_stock_request.*
import xyz.ratapp.ilx.R
import xyz.ratapp.ilx.controllers.info.InfoController
import xyz.ratapp.ilx.data.dao.Request
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.net.URL


class RequestInfoActivity : InfoActivity() {


    companion object {

        val SHOW_DETAILS_OF_STOCK_REQUEST_ACTION = "xyz.ratapp.ilx.SHOW_DETAILS_OF_STOCK_REQUEST_ACTION"

        val STR_TITLE = "title"
        val STR_COST = "cost"
        val STR_COMMENT = "comment"
        val STR_DIFFICULT = "difficult"
        val STR_MAP_URL = "map"

        fun getIntent(id: String, request: Request): Intent {
            val i = Intent()
            i.action = SHOW_DETAILS_OF_STOCK_REQUEST_ACTION
            i.putExtra("id", id)
            i.putExtra(STR_COST, request.cost)
            i.putExtra(STR_TITLE, request.address)
            i.putExtra(STR_COMMENT, request.comment)
            i.putExtra(STR_DIFFICULT, request.difficult)
            i.putExtra(STR_MAP_URL, request.image)

            return i
        }
    }

    private var controller: InfoController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getIntExtra("THEME", R.style.AppTheme_Active))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_info)
        setupUI()
        controller = InfoController(this)
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
            controller!!.acceptRequest()
        }
    }

    fun bindData() {
        val data = intent

        val title = data.getStringExtra(STR_TITLE)
        val cost = data.getStringExtra(STR_COST)
        val comment = data.getStringExtra(STR_COMMENT)
        val difficult = data.getIntExtra(STR_DIFFICULT, -1)
        val mapUrl = data.getStringExtra(STR_MAP_URL)

        tvCost.text = cost
        tvComment.text = comment
        tvTitle.text = title
        if(difficult != -1) {
            vDifficult.setBackgroundColor(difficult)
        }

        if (mapUrl == "")
            ivMap.visibility = View.GONE
        else {
            ivMap.visibility = View.VISIBLE

            //TODO: Вставить картинку с помощью глайда
        }
    }
}

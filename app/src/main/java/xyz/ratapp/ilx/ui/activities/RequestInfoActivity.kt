package xyz.ratapp.ilx.ui.activities

import android.content.Intent
import android.os.Bundle
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
import xyz.ratapp.ilx.ui.fragments.RequestFragment
import xyz.ratapp.ilx.ui.fragments.RequestInfoFragment


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
    private var reqInfo: RequestInfoFragment? = null
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getIntExtra("THEME", R.style.AppTheme_Active))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_info)
        reqInfo = RequestInfoFragment()
        id = intent.getStringExtra("id")
        if(id!!.startsWith("h")) {
            setupHistoryUI()
        }

        controller = InfoController(this)
    }

    private fun setupHistoryUI() {
        fl_content.visibility = View.GONE
        stlReqInfoTabs.visibility = View.VISIBLE
        vpReqInfoContainer.visibility = View.VISIBLE
    }

}

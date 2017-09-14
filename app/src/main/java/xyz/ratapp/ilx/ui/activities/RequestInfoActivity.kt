package xyz.ratapp.ilx.ui.activities

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_request_info.*
import xyz.ratapp.ilx.R
import xyz.ratapp.ilx.controllers.info.InfoController
import xyz.ratapp.ilx.ui.fragments.RequestInfoFragment


class RequestInfoActivity : InfoActivity() {

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

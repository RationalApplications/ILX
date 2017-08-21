package xyz.ratapp.ilx.ui.activities

import android.content.Intent
import android.os.Bundle
import xyz.ratapp.ilx.R
import xyz.ratapp.ilx.controllers.info.InfoController
import xyz.ratapp.ilx.controllers.interfaces.DataSettable
import xyz.ratapp.ilx.data.dao.Button
import xyz.ratapp.ilx.data.dao.Order

class DetailsActivity : InfoActivity() {

    companion object {

        var SHOW_DETAILS_OF_REQUEST_ACTION = "xyz.ratapp.ilx.SHOW_DETAILS_OF_REQUEST_ACTION"

        fun getIntent(id: String): Intent {
            val intent = Intent()
            intent.action = SHOW_DETAILS_OF_REQUEST_ACTION
            intent.putExtra("id", id)

            return intent
        }
    }

    private var controller: InfoController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getIntExtra("THEME", R.style.AppTheme_Active))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        controller = InfoController(this)
    }

    fun onPushButton(btn: Button) {
        controller?.onPushButton(btn)
    }

}

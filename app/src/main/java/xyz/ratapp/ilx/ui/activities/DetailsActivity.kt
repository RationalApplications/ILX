package xyz.ratapp.ilx.ui.activities

import android.content.Intent
import android.os.Bundle
import xyz.ratapp.ilx.R
import xyz.ratapp.ilx.controllers.info.InfoController
import xyz.ratapp.ilx.data.dao.Request

class DetailsActivity : InfoActivity() {

    companion object {

        var SHOW_DETAILS_OF_REQUEST_ACTION = "xyz.ratapp.ilx.SHOW_DETAILS_OF_REQUEST_ACTION"

        val STR_ADDRESS = "address"
        val STR_TIME = "time"
        val STR_TASK = "task"
        val STR_DESCRIPTION = "description"
        val STR_NAME = "name"
        val STR_PHONE = "phone"

        fun getIntent(request: Request): Intent {
            val intent = Intent()
            intent.action = SHOW_DETAILS_OF_REQUEST_ACTION
            intent.putExtra(STR_ADDRESS, request.address)
            intent.putExtra(STR_TIME, request.time)
            intent.putExtra(STR_TASK, request.task)
            intent.putExtra(STR_DESCRIPTION, request.comment)
            intent.putExtra(STR_NAME, request.name)
            intent.putExtra(STR_PHONE, request.phone)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getIntExtra("THEME", R.style.AppTheme_Active))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setupUI()
        InfoController(this)
    }

    fun setupUI() {
        //setup rv
    }
}

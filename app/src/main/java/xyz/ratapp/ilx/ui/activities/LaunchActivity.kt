package xyz.ratapp.ilx.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_launch.*
import xyz.ratapp.ilx.R
import xyz.ratapp.ilx.controllers.launch.LaunchController

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        LaunchController(this)
    }

    fun setAuthorizeScreen() {
        setContentView(R.layout.activity_launch)
        //TODO: make keyboard with only numbers
        //ciPassword.setInputType(InputType.TYPE_CLASS_PHONE)
        //val editor = EditorInfo()
        //ciPassword.onCreateInputConnection(EditorInfo()).beginBatchEdit()
    }

    fun onStartLogin() {
        ciPassword.isEnabled = false
        ciPassword.hideKeyBoard()
        pbLoading.visibility = View.VISIBLE
    }

    fun onLoginFailed() {
        ciPassword.isEnabled = true
        pbLoading.visibility = View.INVISIBLE
    }
}


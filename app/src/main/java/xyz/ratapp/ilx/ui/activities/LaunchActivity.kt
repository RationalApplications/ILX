package xyz.ratapp.ilx.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.view.View
import kotlinx.android.synthetic.main.activity_launch.*
import kotlinx.android.synthetic.main.activity_launch.view.*
import xyz.ratapp.ilx.R
import xyz.ratapp.ilx.controllers.launch.LaunchController
import xyz.ratapp.ilx.ui.views.CodeInput

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        LaunchController(this)
    }

    fun setAuthorizeScreen() {
        setContentView(R.layout.activity_launch)
        ciPassword.setInputType(InputType.TYPE_CLASS_NUMBER)
    }

    fun onStartAuth() {
        ciPassword.isEnabled = false
        pbLoading.visibility = View.VISIBLE
    }

    fun onAuthFailed() {
        ciPassword.isEnabled = true
        pbLoading.visibility = View.INVISIBLE
    }
}


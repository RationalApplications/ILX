package xyz.ratapp.ilx.ui.activities

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_launch.*
import xyz.ratapp.ilx.R
import xyz.ratapp.ilx.controllers.launch.LaunchController
import xyz.ratapp.ilx.ui.interfaces.ErrorDisplayable

class LaunchActivity :
        AppCompatActivity(),
        ErrorDisplayable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        LaunchController(this)
    }

    fun setAuthorizeScreen() {
        setContentView(R.layout.activity_launch)
        //TODO: change screen
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

    override fun showError(@StringRes throwable: Int) {
        val thr = getString(throwable)

        Toast.makeText(this,
                thr,
                Toast.LENGTH_LONG).show()
    }

    override fun showError(throwable: String) {
        Toast.makeText(this,
                throwable,
                Toast.LENGTH_LONG).show()
    }
}


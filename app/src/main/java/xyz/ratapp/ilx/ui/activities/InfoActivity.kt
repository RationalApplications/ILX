package xyz.ratapp.ilx.ui.activities

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity

/**
 * Created by timtim on 14/08/2017.
 *
 * ЗАЕБАЛ ЭТОТ АЛЕКСЕЙ
 */

open class InfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableToolbarBack()
    }

    /*
     * All methods in this class need for back-key in toolbar
     */
    fun enableToolbarBack() {
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

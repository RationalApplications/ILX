package xyz.ratapp.ilx.ui.activities

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import xyz.ratapp.ilx.R
import xyz.ratapp.ilx.controllers.main.MainController
import xyz.ratapp.ilx.data.dao.Uuser
import xyz.ratapp.ilx.ui.helpers.ThemePicker


class MainActivity : AppCompatActivity() {

    private var controller: MainController? = null
    private var picker: ThemePicker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controller = MainController(this)
    }

    fun setupUI() {
        //tabs
        stlTabs.setSelectedIndicatorColors(
                resources.getColor(R.color.primary_dark_color))
        stlTabs.setBackgroundResource(R.color.tab_bar_background_color)
        stlTabs.setCustomTabView(R.layout.tab_layout, R.id.tvTabItem)
        stlTabs.setDistributeEvenly(false)

        //drawer
        val toggle = ActionBarDrawerToggle(this, dlMain, toolbar,
                R.string.nav_drawer_open_desc, R.string.nav_drawer_close_desc)
        dlMain.addDrawerListener(toggle)
        toggle.syncState()

        //navigationView
        navView.setNavigationItemSelectedListener(controller)
        picker = ThemePicker(this)
    }

    fun setupToolbar(customView: View) {
        //toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.requests)
        toolbar.addView(customView, Toolbar.LayoutParams(Gravity.END))
    }

    fun bindUser(user: Uuser) {
        Glide.with(this).load(user.preview).error(R.mipmap.ic_launcher).
                into(navView.getHeaderView(0).ivNavIcon)
        navView.getHeaderView(0).tvNavHeader.text = user.courierName
        navView.getHeaderView(0).tvNavText.text = user.workStatus

        picker!!.setTheme(user.isOnline)
    }

    override fun onBackPressed() {
        if (dlMain.isDrawerOpen(GravityCompat.START)) {
            dlMain.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }

}

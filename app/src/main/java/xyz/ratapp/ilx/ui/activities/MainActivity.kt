package xyz.ratapp.ilx.ui.activities

import android.app.TaskStackBuilder
import android.os.Bundle
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
import xyz.ratapp.ilx.data.dao.User
import xyz.ratapp.ilx.ui.helpers.ThemePicker



class MainActivity : AppCompatActivity() {

    //controller
    private var controller: MainController? = null
    //ui
    private var picker: ThemePicker? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        picker = ThemePicker(this)
        controller = MainController(this)
    }

    fun setupUI() {
        setSupportActionBar(toolbar)

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
    }

    fun setupToolbar(customView: View) {
        //toolbar
        toolbar.setTitle(R.string.requests)
        toolbar.addView(customView, Toolbar.LayoutParams(Gravity.END))
    }

    fun bind(user: User) {
        Glide.with(this).load(user.image).asBitmap().into(navView.getHeaderView(0).ivNavIcon)
        navView.getHeaderView(0).tvNavHeader.text = user.fullName
        navView.getHeaderView(0).tvNavText.setText(user.stateText)

        picker!!.setTheme(user.isOnline)
    }

    override fun onBackPressed() {
        if (!controller!!.back()) {
            super.onBackPressed()
        }
    }

}

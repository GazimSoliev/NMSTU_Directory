package com.gazim.directory

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.gazim.directory.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharePref: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharePref = getSharedPreferences("settings", MODE_PRIVATE)
        when (sharePref.getInt("theme", 0)) {
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val drawerLayout: DrawerLayout = binding.root
        setSupportActionBar(binding.appBarMain.toolbar)
        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment).navController
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_short_history, R.id.nav_info, R.id.nav_gallery, R.id.nav_directions, R.id.nav_about), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.textViewLink).setOnClickListener {
            startActivity(
                Intent.parseUri(
                "https://www.magtu.ru/sveden/struct/mnogoprofilnyj-kolledzh.html", Intent.URI_INTENT_SCHEME))
        }
        setContentView(drawerLayout)
        val a = TypedValue()
        theme.resolveAttribute(android.R.attr.windowBackground, a, true)
        if (a.isColorType) {
            // windowBackground is a color
            val color = a.data
            Log.i("back", color.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val item = menu.findItem(R.id.action_settings)
        when (sharePref.getInt("theme", 0)) {
            1 -> item.setIcon(R.drawable.ic_dark)
            2 -> item.setIcon(R.drawable.ic_light)
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun onClickMenuTheme(item: MenuItem) {
        Timer().schedule(timerTask { runOnUiThread { val theme = sharePref.getInt("theme", 0) + 1
            when (theme) {
                1 -> {
                    Toast.makeText(this@MainActivity, "Dark theme", Toast.LENGTH_SHORT).show()
                    item.setIcon(R.drawable.ic_dark)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                2 -> {
                    Toast.makeText(this@MainActivity, "Light theme", Toast.LENGTH_SHORT).show()
                    item.setIcon(R.drawable.ic_light)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                else -> {
                    Toast.makeText(this@MainActivity, "Theme follow system", Toast.LENGTH_SHORT).show()
                    item.setIcon(R.drawable.ic_autotheme)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            sharePref.edit().putInt("theme", if (theme < 3) theme else 0).apply() } }, 1000)
    }

}
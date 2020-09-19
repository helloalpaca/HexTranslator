package com.minseon.hextranslator

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.tabs.TabLayout
import com.minseon.hextranslator.ui.main.SectionsPagerAdapter


class MainActivity : AppCompatActivity() {

    lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)


        /*
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

        /*
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        MobileAds.initialize(this) {}
        */
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.actionbar_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.item1 -> {
                // Toast.makeText(this@MainActivity, "clicked!", Toast.LENGTH_SHORT).show()
                val pInfo: PackageInfo = getPackageManager().getPackageInfo(packageName, 0)
                val appVersion = pInfo.versionName

                val email = Intent(Intent.ACTION_SEND)
                email.data = Uri.parse("mailto:")
                email.type = "text/plain"
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf("jms393497@gmail.com"))
                email.putExtra(Intent.EXTRA_SUBJECT, "<" + getString(R.string.app_name) + " 문의>");
                email.putExtra(Intent.EXTRA_TEXT, "앱 버전 (AppVersion):" + appVersion + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n");

                try {
                    startActivity(Intent.createChooser(email, "Send email..."))
                } catch (e: Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
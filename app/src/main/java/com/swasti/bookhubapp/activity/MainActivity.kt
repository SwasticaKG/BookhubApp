package com.swasti.bookhubapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.FrameLayout
import com.swasti.bookhubapp.R
import com.swasti.bookhubapp.fragment.AboutAppFragment
import com.swasti.bookhubapp.fragment.DashboardFragment
import com.swasti.bookhubapp.fragment.FavouritesFragment
import com.swasti.bookhubapp.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem:MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout=findViewById(R.id.drawerLayout)
        coordinatorLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.framelayout)
        navigationView=findViewById(R.id.navigationView)
        setUpToolbar()

        openDashboard()

        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity,drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null)
            {
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it

            when(it.itemId)
            {
                R.id.dashboard ->{
                    //Toast.makeText(this@MainActivity,"clicked on Dashboard",Toast.LENGTH_SHORT).show()

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, DashboardFragment())
                        .commit()
                    supportActionBar?.title="Dashboard"
                    drawerLayout.closeDrawers()
                }
                R.id.favourites ->{
                   // Toast.makeText(this@MainActivity,"clicked on Favourites",Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, FavouritesFragment())
                        .commit()
                    supportActionBar?.title="Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.profile ->{
                   // Toast.makeText(this@MainActivity,"clicked on Profile",Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, ProfileFragment())
                        .commit()
                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.aboutapp ->{
                  //  Toast.makeText(this@MainActivity,"clicked on About App",Toast.LENGTH_SHORT).show()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.framelayout, AboutAppFragment())
                        .commit()
                    supportActionBar?.title="About App"
                    drawerLayout.closeDrawers()
                }
            }

            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="Toolbar title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home)
        {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openDashboard()
    {
        val fragment= DashboardFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.framelayout,fragment)
        transaction.commit()
        supportActionBar?.title="Dashboard"

        navigationView.setCheckedItem(R.id.dashboard)
    }

    override fun onBackPressed() {
       val frag=supportFragmentManager.findFragmentById(R.id.framelayout)

        when(frag)
        {
            !is DashboardFragment -> openDashboard()

            else -> super.onBackPressed()
        }
    }
}

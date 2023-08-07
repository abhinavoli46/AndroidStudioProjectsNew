package com.example.runfit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.runfit.R
import com.example.runfit.other.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*If our main activity was destroyed and we press notification onCreate() is called again,
         *So from here we need to navigate to Tracking Fragment
        */
        moveFromNotificationToTrackingFragment(intent)


        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbarUp)
        setSupportActionBar(toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                        bottomNavigation.visibility = View.VISIBLE

                    else -> bottomNavigation.visibility = View.GONE
                }
            }


    }
    //If our main activity was not destroyed and we press notification,
    //So from here we need to navigate to Tracking Fragment
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        moveFromNotificationToTrackingFragment(intent)
    }
    //When we press the notification it takes us to main activity and from here we navigate to our actual destination
    //which is Tracking Fragment
    private fun moveFromNotificationToTrackingFragment(intent: Intent?){
        if(intent?.action == Constants.ACTION_SHOW_TRACKING_FRAGMENT){
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navHostFragment.findNavController().navigate(R.id.action_global_tracking_fragment)
        }
    }

    /*
    * NavHostFragment = The area in our main activity where our fragments will be displayed.
    * navController = A controller which keeps check on transition that are performed when we click buttons present in bottom navigation view.
    * We attach our bottom navigation view with this nav controller
    * Here we set up our navigation menu and its functioning according to the navigation graph
    * defined in
    * */
}
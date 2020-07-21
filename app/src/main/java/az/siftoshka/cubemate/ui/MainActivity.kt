package az.siftoshka.cubemate.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import az.siftoshka.cubemate.R
import az.siftoshka.cubemate.utils.MessageListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_timer_alt.*
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), MessageListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstOpen()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            window.navigationBarColor = resources.getColor(R.color.mainBackground)


        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        navController
            .addOnDestinationChangedListener { controller, destination, arguments ->
                val prefs = getSharedPreferences("Tap-Mode", Context.MODE_PRIVATE)
                val tapMode = prefs.getInt("Tap", 0)
                when (destination.id) {
                    R.id.settingsFragment, R.id.timerFragment, R.id.statisticsFragment -> {
                        Timber.d("NAVBAR")
                        if (destination.id == R.id.timerFragment && tapMode == 100) hideStatusBar()
                        else showStatusBar()
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                    else -> bottomNavigationView.visibility = View.GONE
                }
            }
    }

    private fun showStatusBar() {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun hideStatusBar() {
        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun firstOpen() {
        val prefs = getSharedPreferences("First-Open", Context.MODE_PRIVATE)
        val launch = prefs.getInt("Launch", 0)
        if (launch != 101) {
            val editor = getSharedPreferences(
                "First-Open",
                Context.MODE_PRIVATE
            ).edit()
            editor.putInt("Launch", 100)
            editor.apply()
        }
    }

    override fun showMessage(message: String) {
        val snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        snackbar.anchorView = bottomNavigationView
        snackbar.show()
    }
}
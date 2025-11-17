package cl.lte.aplicacionlte

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import cl.lte.aplicacionlte.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Toolbar Setup ---
        setSupportActionBar(binding.appBarMain.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // --- Device Type Detection ---
        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        val screenHeightDp = displayMetrics.heightPixels / displayMetrics.density
        val smallestWidthDp = min(screenWidthDp, screenHeightDp)

        val deviceType = when {
            smallestWidthDp >= 840 -> "desktop"
            smallestWidthDp >= 600 -> "tablet"
            else -> "phone"
        }
        val deviceTextView = findViewById<TextView>(R.id.device_type_textview)
        deviceTextView.text = deviceType

        // --- Navigation Setup ---
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?)!!
        val navController = navHostFragment.navController

        // Hamburger Icon Click Listener
        findViewById<ImageButton>(R.id.hamburger_icon).setOnClickListener {
            binding.drawerLayout?.open()
        }

        // Setup for Drawer (Side Navigation)
        binding.navView?.let {
            appBarConfiguration = AppBarConfiguration(
                setOf(R.id.nav_transform, R.id.nav_reflow, R.id.nav_slideshow, R.id.nav_settings),
                binding.drawerLayout
            )
            it.setupWithNavController(navController)
        }

        // Setup for Bottom Navigation
        binding.appBarMain.contentMain.bottomNavView?.setupWithNavController(navController)

        // --- FAB Setup ---
        binding.appBarMain.fab?.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        val navView: NavigationView? = findViewById(R.id.nav_view)
        if (navView == null) {
            menuInflater.inflate(R.menu.overflow, menu)
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_settings) {
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            navController.navigate(R.id.nav_settings)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // appBarConfiguration is now only for the drawer, so this should work as before.
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

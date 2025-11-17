package cl.lte.aplicacionlte

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import cl.lte.aplicacionlte.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Toolbar Setup ---
        setSupportActionBar(binding.toolbar)
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

        // Hamburger Icon Click Listener
        findViewById<ImageButton>(R.id.hamburger_icon).setOnClickListener {
            binding.drawerLayout?.open()
        }

        // --- FAB Setup ---
        binding.fab?.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        // --- Setup Carousels based on orientation --- 
        // This check is now more complex due to the different layout roots across configurations
        val landscapeView = findViewById<ImageButton>(R.id.arrow_down_zone_1)
        if (landscapeView != null) {
            // Landscape layout is inflated, setup vertical carousels
            setupVerticalCarousels()
        } else {
            // Portrait layout is inflated, setup horizontal carousels
            setupHorizontalCarousels()
        }
    }

    private fun setupHorizontalCarousels() {
        setupHorizontalCarousel(R.id.carousel_zone_1, R.id.arrow_left_zone_1, R.id.arrow_right_zone_1, listOf(R.drawable.foto1, R.drawable.foto2, R.drawable.foto3))
        setupHorizontalCarousel(R.id.carousel_zone_2, R.id.arrow_left_zone_2, R.id.arrow_right_zone_2, listOf(R.drawable.foto4, R.drawable.foto5, R.drawable.foto6))
        setupHorizontalCarousel(R.id.carousel_zone_3, R.id.arrow_left_zone_3, R.id.arrow_right_zone_3, listOf(R.drawable.foto7, R.drawable.foto8, R.drawable.foto9))
    }

    private fun setupVerticalCarousels() {
        setupVerticalCarousel(R.id.carousel_zone_1, R.id.arrow_down_zone_1, listOf(R.drawable.foto1, R.drawable.foto2, R.drawable.foto3))
        setupVerticalCarousel(R.id.carousel_zone_2, R.id.arrow_down_zone_2, listOf(R.drawable.foto4, R.drawable.foto5, R.drawable.foto6))
        setupVerticalCarousel(R.id.carousel_zone_3, R.id.arrow_down_zone_3, listOf(R.drawable.foto7, R.drawable.foto8, R.drawable.foto9))
    }

    private fun setupHorizontalCarousel(@IdRes carouselId: Int, @IdRes leftArrowId: Int, @IdRes rightArrowId: Int, images: List<Int>) {
        val carouselView = findViewById<ViewPager2>(carouselId) ?: return
        val arrowLeft = findViewById<ImageButton>(leftArrowId) ?: return
        val arrowRight = findViewById<ImageButton>(rightArrowId) ?: return

        if (images.isEmpty()) return
        carouselView.adapter = ImageCarouselAdapter(images)
        val middlePosition = Integer.MAX_VALUE / 2
        carouselView.setCurrentItem(middlePosition - middlePosition % images.size, false)

        arrowLeft.setOnClickListener { carouselView.currentItem -= 1 }
        arrowRight.setOnClickListener { carouselView.currentItem += 1 }
    }

    private fun setupVerticalCarousel(@IdRes carouselId: Int, @IdRes downArrowId: Int, images: List<Int>) {
        val carouselView = findViewById<ViewPager2>(carouselId) ?: return
        val arrowDown = findViewById<ImageButton>(downArrowId) ?: return

        if (images.isEmpty()) return
        carouselView.adapter = ImageCarouselAdapter(images)
        val middlePosition = Integer.MAX_VALUE / 2
        carouselView.setCurrentItem(middlePosition - middlePosition % images.size, false)

        arrowDown.setOnClickListener { carouselView.currentItem += 1 }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}

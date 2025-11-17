package cl.lte.aplicacionlte

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

        // Hamburger Icon Click Listener
        findViewById<ImageButton>(R.id.hamburger_icon).setOnClickListener {
            binding.drawerLayout?.open()
        }

        // --- FAB Setup ---
        binding.appBarMain.fab?.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        // --- Setup Carousels ---
        setupCarousel(
            carouselId = R.id.carousel_zone_1,
            leftArrowId = R.id.arrow_left_zone_1,
            rightArrowId = R.id.arrow_right_zone_1,
            images = listOf(R.drawable.foto1, R.drawable.foto2, R.drawable.foto3)
        )

        setupCarousel(
            carouselId = R.id.carousel_zone_2,
            leftArrowId = R.id.arrow_left_zone_2,
            rightArrowId = R.id.arrow_right_zone_2,
            images = listOf(R.drawable.foto4, R.drawable.foto5, R.drawable.foto6)
        )

        setupCarousel(
            carouselId = R.id.carousel_zone_3,
            leftArrowId = R.id.arrow_left_zone_3,
            rightArrowId = R.id.arrow_right_zone_3,
            images = listOf(R.drawable.foto7, R.drawable.foto8, R.drawable.foto9)
        )
    }

    private fun setupCarousel(
        @IdRes carouselId: Int,
        @IdRes leftArrowId: Int,
        @IdRes rightArrowId: Int,
        images: List<Int>
    ) {
        val carouselView = findViewById<ViewPager2>(carouselId)
        val arrowLeft = findViewById<ImageButton>(leftArrowId)
        val arrowRight = findViewById<ImageButton>(rightArrowId)

        if (images.isEmpty()) return // Do not setup if there are no images

        carouselView.adapter = ImageCarouselAdapter(images)

        // Start in the middle to allow scrolling left and right from the beginning
        val middlePosition = Integer.MAX_VALUE / 2
        carouselView.setCurrentItem(middlePosition - middlePosition % images.size, false)

        arrowLeft.setOnClickListener {
            carouselView.currentItem = carouselView.currentItem - 1
        }

        arrowRight.setOnClickListener {
            carouselView.currentItem = carouselView.currentItem + 1
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
        // Previous navigation logic was here.
        return super.onOptionsItemSelected(item)
    }
}

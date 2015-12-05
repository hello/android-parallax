package `is`.hello.parallax

import `is`.hello.parallax.util.CardItemDecoration
import `is`.hello.parallax.util.ParallaxRecyclerScrollListener
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        this.recyclerView = findViewById(R.id.activity_main_recycler) as RecyclerView
        recyclerView!!.addOnScrollListener(ParallaxRecyclerScrollListener())
        recyclerView!!.addItemDecoration(CardItemDecoration(resources))

        // This is a little ugly, but it's fairly important that the bitmap drawables
        // be decoded beforehand, otherwise we get real shit scroll performance.
        // In practice, using Picasso should take care of this detail, and it could
        // even take care of pre-scaling the images on older devices.
        val insightCards = arrayOf(
                InsightCard(resources, R.drawable.ambient_light, R.string.title_ambient_light),
                InsightCard(resources, R.drawable.associating, R.string.title_associating),
                InsightCard(resources, R.drawable.duration, R.string.title_duration),
                InsightCard(resources, R.drawable.habits, R.string.title_habits),
                InsightCard(resources, R.drawable.humidity, R.string.title_humidity),
                InsightCard(resources, R.drawable.light_exposure, R.string.title_light_exposure),
                InsightCard(resources, R.drawable.movement, R.string.title_movement),
                InsightCard(resources, R.drawable.particulates, R.string.title_particulates),
                InsightCard(resources, R.drawable.partner, R.string.title_partner),
                InsightCard(resources, R.drawable.sense, R.string.title_sense),
                InsightCard(resources, R.drawable.sound, R.string.title_sound),
                InsightCard(resources, R.drawable.temperature, R.string.title_temperature),
                InsightCard(resources, R.drawable.waking_up, R.string.title_waking_up)
        )
        recyclerView!!.adapter = InsightCardAdapter(this, insightCards)
    }

    override fun onDestroy() {
        super.onDestroy()

        recyclerView!!.clearOnScrollListeners()
    }
}

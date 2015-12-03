package is.hello.parallax;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import is.hello.parallax.util.CardItemDecoration;
import is.hello.parallax.util.ParallaxRecyclerScrollListener;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Resources resources = getResources();

        this.recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler);
        recyclerView.addOnScrollListener(new ParallaxRecyclerScrollListener());
        recyclerView.addItemDecoration(new CardItemDecoration(resources));

        // This is a little ugly, but it's fairly important that the bitmap drawables
        // be decoded beforehand, otherwise we get real shit scroll performance.
        // In practice, using Picasso should take care of this detail, and it could
        // even take care of pre-scaling the images on older devices.
        final InsightCard[] insightCards = {
                new InsightCard(resources, R.drawable.ambient_light, R.string.title_ambient_light),
                new InsightCard(resources, R.drawable.associating, R.string.title_associating),
                new InsightCard(resources, R.drawable.duration, R.string.title_duration),
                new InsightCard(resources, R.drawable.habits, R.string.title_habits),
                new InsightCard(resources, R.drawable.humidity, R.string.title_humidity),
                new InsightCard(resources, R.drawable.light_exposure, R.string.title_light_exposure),
                new InsightCard(resources, R.drawable.movement, R.string.title_movement),
                new InsightCard(resources, R.drawable.particulates, R.string.title_particulates),
                new InsightCard(resources, R.drawable.partner, R.string.title_partner),
                new InsightCard(resources, R.drawable.sense, R.string.title_sense),
                new InsightCard(resources, R.drawable.sound, R.string.title_sound),
                new InsightCard(resources, R.drawable.temperature, R.string.title_temperature),
                new InsightCard(resources, R.drawable.waking_up, R.string.title_waking_up),
        };
        final InsightCardAdapter adapter = new InsightCardAdapter(this, insightCards);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        recyclerView.clearOnScrollListeners();
    }
}

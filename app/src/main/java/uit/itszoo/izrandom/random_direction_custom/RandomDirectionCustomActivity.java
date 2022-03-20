package uit.itszoo.izrandom.random_direction_custom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;

import uit.itszoo.izrandom.R;

public class RandomDirectionCustomActivity extends AppCompatActivity {
    private int[] images = {R.drawable.ic_random_direction,
            R.drawable.ic_random_direction_2,
            R.drawable.ic_random_direction_3,
            R.drawable.ic_random_direction_4,
            R.drawable.ic_random_direction_5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_direction_custom);

        CarouselView carouselView = findViewById(R.id.carouselView);

        carouselView.setSize(images.length);
        carouselView.setResource(R.layout.image_view_carousel);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                ImageView imageView = view.findViewById(R.id.imageView);
                imageView.setImageDrawable(getDrawable(images[position]));
            }
        });
        // After you finish setting up, show the CarouselView
        carouselView.show();
    }
}
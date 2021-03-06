package uit.itszoo.izrandom.random_module.random_direction.random_direction_custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.ArrayList;
import java.util.Collections;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.random_direction.RandomDirectionActivity;
import uit.itszoo.izrandom.random_module.random_direction.model.Arrow;
import uit.itszoo.izrandom.random_module.random_direction.source.ArrowSource;

public class RandomDirectionCustomActivity extends AppCompatActivity {
    public static final String SELECTED_ARROW = "SELECTED_ARROW";
    private ArrayList<Arrow> images = (ArrayList<Arrow>) ArrowSource.arrows.clone();

    ImageButton backButton;
    CarouselView carouselView;
    ImageView imageView;
    ImageButton confirmButton;
    Arrow initialArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_direction_custom);
        initialArrow = (Arrow) getIntent().getSerializableExtra(RandomDirectionActivity.CURRENT_ARROW);

        swapInitialArrowToLead();
        initView();
        setupCarouselView();
    }

    private void swapInitialArrowToLead() {
        int initialArrowIndex = images.indexOf(initialArrow);
        Collections.swap(images, 0, initialArrowIndex);
    }

    private void initView() {
        backButton = findViewById(R.id.bb_rand_dir_custom);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        confirmButton = findViewById(R.id.confirm_button_rand_dir_custom);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent();
                intentBack.putExtra(RandomDirectionCustomActivity.SELECTED_ARROW, images.get(carouselView.getCurrentItem()));
                setResult(Activity.RESULT_OK, intentBack);
                finish();
            }
        });
    }

    private void setupCarouselView() {
        carouselView = findViewById(R.id.carouselView);

        carouselView.setSize(images.size());
        carouselView.setResource(R.layout.image_view_carousel);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                imageView = view.findViewById(R.id.imageView);
                imageView.setImageDrawable(getDrawable(images.get(position).getLayout()));
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();
    }
}
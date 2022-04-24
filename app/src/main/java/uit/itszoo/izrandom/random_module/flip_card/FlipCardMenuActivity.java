package uit.itszoo.izrandom.random_module.flip_card;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import uit.itszoo.izrandom.R;

public class FlipCardMenuActivity extends AppCompatActivity implements FlipCardMenuContract.View{

    FlipCardMenuContract.Presenter presenter;
    CarouselView carouselView;
    TextView cardNameTextView;
    ImageButton backButton;
    ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_card_menu);

        initView();
        setListenerForView();
        setupCarouselView();

        presenter = new FlipCardMenuPresenter(getApplicationContext(), this);
        setPresenter(presenter);

    }

    private void initView() {
        backButton = findViewById(R.id.bb_flip_card_menu);
        addButton = findViewById(R.id.flip_card_add);
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        toCustomScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentToCustom = new Intent(getApplicationContext(), RandomDirectionCustomActivity.class);
//                intentToCustom.putExtra(RandomDirectionActivity.CURRENT_ARROW, presenter.getCurrentArrow());
//                intentLauncher.launch(intentToCustom);
//            }
//        });

    }

    @Override
    public void setPresenter(FlipCardMenuContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void setupCarouselView() {
        carouselView = findViewById(R.id.flip_card_carouselView);

        carouselView.setSize(5);
        carouselView.setResource(R.layout.card_view_carousel);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                cardNameTextView = view.findViewById(R.id.txt_flip_card_name);
                cardNameTextView.setText("Hâm nóng tình yêu");
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();
    }
}

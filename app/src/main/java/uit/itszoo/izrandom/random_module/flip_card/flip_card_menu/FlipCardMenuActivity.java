package uit.itszoo.izrandom.random_module.flip_card.flip_card_menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.ArrayList;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.FlipCardActivity;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_add.FlipCardAddActivity;
import uit.itszoo.izrandom.random_module.random_integer.random_integer_custom.RandomIntegerCustomActivity;

public class FlipCardMenuActivity extends AppCompatActivity implements FlipCardMenuContract.View {
    public static final String IS_ADD_OR_EDIT = "IS_ADD_OR_EDIT";
    public static final String CARD_NAME_TO_EDIT = "CARD_NAME_TO_EDIT";

    FlipCardMenuContract.Presenter presenter;
    CarouselView carouselView;
    ImageButton backButton;
    ImageButton toAddScreenButton;
    Button startButton;

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
        toAddScreenButton = findViewById(R.id.flip_card_add);
        startButton = findViewById(R.id.btn_flip_card_menu_start);
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toAddScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToFlipCardAdd = new Intent(getApplicationContext(), FlipCardAddActivity.class);
                intentToFlipCardAdd.putExtra(FlipCardMenuActivity.IS_ADD_OR_EDIT, "ADD");
                intentToFlipCardAdd.putExtra(FlipCardMenuActivity.CARD_NAME_TO_EDIT, "");
                intentLauncher.launch(intentToFlipCardAdd);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToFlipCard = new Intent(getApplicationContext(), FlipCardActivity.class);
//                intentToFlipCard.putExtra(RandomDirectionActivity.CURRENT_ARROW, presenter.getCurrentArrow());
//                intentLauncher.launch(intentToFlipCard);
                startActivity(intentToFlipCard);
            }
        });

    }

    @Override
    public void setPresenter(FlipCardMenuContract.Presenter presenter) {
        this.presenter = presenter;
    }

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        ArrayList<String> newListCusNum = data.getIntegerArrayListExtra(RandomIntegerCustomActivity.NEW_CUS_NUM);
//                        if (newListCusNum != null) {
//                            ranNumPresenter.setListCusNum(newListCusNum);
//                        }

                    }
                }
            });

    private void setupCarouselView() {
        carouselView = findViewById(R.id.flip_card_carouselView);

        carouselView.setSize(5);
        carouselView.setResource(R.layout.card_view_carousel);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                String cardName = "Hâm nóng tình yêu";

                CardView cardViewCarouselItem;
                TextView cardNameTextView;

                cardViewCarouselItem = view.findViewById(R.id.cardView_flip_card_carousel);
                cardNameTextView = view.findViewById(R.id.txt_flip_card_name);
                cardNameTextView.setText(cardName);

                cardViewCarouselItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentToFlipCardAdd = new Intent(getApplicationContext(), FlipCardAddActivity.class);
                        intentToFlipCardAdd.putExtra(FlipCardMenuActivity.IS_ADD_OR_EDIT, "EDIT");
                        intentToFlipCardAdd.putExtra(FlipCardMenuActivity.CARD_NAME_TO_EDIT, cardName);
                        intentLauncher.launch(intentToFlipCardAdd);

                    }
                });
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();
    }
}

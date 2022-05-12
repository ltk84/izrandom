package uit.itszoo.izrandom.random_module.flip_card.flip_card_menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.OffsetType;

import java.util.ArrayList;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.FlipCardActivity;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_add.FlipCardAddActivity;
import uit.itszoo.izrandom.random_module.random_integer.random_integer_custom.RandomIntegerCustomActivity;

public class FlipCardMenuActivity extends AppCompatActivity implements FlipCardMenuContract.View {
    public static final String IS_ADD_OR_EDIT = "IS_ADD_OR_EDIT";
    public static final String CARD_NAME_TO_EDIT = "CARD_NAME_TO_EDIT";
    public static final String CARD_POSITION_TO_EDIT = "CARD_POSITION_TO_EDIT";

    ArrayList<String> listCardName = new ArrayList<>();

    FlipCardMenuContract.Presenter presenter;
    CarouselView carouselView;
    ImageButton backButton;
    ImageButton toAddScreenButton;
    Button startButton;

    ConstraintLayout ads;

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
        carouselView = findViewById(R.id.flip_card_carouselView);

        ads = findViewById(R.id.abcsda);

        //TODO get list card names from database
        for(int i = 0; i < 5; i++) {
            listCardName.add("Hâm nóng tình yêu " + String.valueOf(i));
        }
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

                        Intent data = result.getData();
                        String isAddOrEdit = data.getStringExtra(FlipCardAddActivity.IS_ADD_OR_EDIT);
                        String newCardName = data.getStringExtra(FlipCardAddActivity.NEW_CARD_NAME);

                        if (isAddOrEdit.equals("ADD")) {
                            listCardName.add(0, newCardName);
                            setupCarouselView();
                        }
                        else if (isAddOrEdit.equals("EDIT")) {
                            int editedCardPosition = data.getIntExtra(FlipCardAddActivity.EDITED_CARD_POSITION, 0);
                            listCardName.set(editedCardPosition, newCardName);
                            setupCarouselView();
                            carouselView.setCurrentItem(editedCardPosition);
                        }

                    }
                }
            });

    private void setupCarouselView() {
        carouselView.setSize(listCardName.size());
        carouselView.setResource(R.layout.card_view_carousel);

        carouselView.measure(View.MeasureSpec.makeMeasureSpec(ads.getWidth(),
                View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0,
                        View.MeasureSpec.UNSPECIFIED));

        Log.i("FlipCardMenuActivity", "carouselView getMeasuredHeight: " + String.valueOf(carouselView.getMeasuredHeight()));

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                String cardName = listCardName.get(position);

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
                        intentToFlipCardAdd.putExtra(FlipCardMenuActivity.CARD_POSITION_TO_EDIT, position);
                        intentLauncher.launch(intentToFlipCardAdd);

                    }
                });
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();
    }

}

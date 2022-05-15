package uit.itszoo.izrandom.random_module.flip_card.flip_card_menu;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.OffsetType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.FlipCardActivity;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_add.FlipCardAddActivity;
import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;
import uit.itszoo.izrandom.random_module.random_integer.random_integer_custom.RandomIntegerCustomActivity;

public class FlipCardMenuActivity extends AppCompatActivity implements FlipCardMenuContract.View {
    public static final String IS_ADD_OR_EDIT = "IS_ADD_OR_EDIT";
    public static final String CARD_COLLECTION_TO_EDIT = "CARD_NAME_TO_EDIT";
    public static final String CARD_POSITION_TO_EDIT = "CARD_POSITION_TO_EDIT";
    public static final String CARD_COLLECTION_TO_FLIP = "CARD_COLLECTION_TO_FLIP";

    List<CardCollectionModel> listCardCollections = new ArrayList<>();

    FlipCardMenuContract.Presenter presenter;
    CarouselView carouselView;
    ImageButton backButton;
    ImageButton toAddScreenButton;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_card_menu);

        presenter = new FlipCardMenuPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        listCardCollections = presenter.getListCardCollections();
        Log.i("FlipCardMenuActivity", "listCardCollections size: " + String.valueOf(listCardCollections.size()));

        initView();
        setListenerForView();
        setupCarouselView();

    }

    private void initView() {
        backButton = findViewById(R.id.bb_flip_card_menu);
        toAddScreenButton = findViewById(R.id.flip_card_add);
        startButton = findViewById(R.id.btn_flip_card_menu_start);
        carouselView = findViewById(R.id.flip_card_carouselView);
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
                intentToFlipCardAdd.putExtra(FlipCardMenuActivity.CARD_COLLECTION_TO_EDIT, new CardCollectionModel(UUID.randomUUID().toString(), "", System.currentTimeMillis()));
                intentLauncher.launch(intentToFlipCardAdd);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToFlipCard = new Intent(getApplicationContext(), FlipCardActivity.class);
                intentToFlipCard.putExtra(FlipCardMenuActivity.CARD_COLLECTION_TO_FLIP, listCardCollections.get(carouselView.getCurrentItem()));
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
                        Intent data = result.getData();
                        String isAddOrEdit = data.getStringExtra(FlipCardAddActivity.IS_ADD_OR_EDIT);
                        CardCollectionModel cardCollectionModel = (CardCollectionModel) data.getSerializableExtra(FlipCardAddActivity.EDITED_CARD_COLLECTION);

                        if (isAddOrEdit.equals("ADD")) {
                            listCardCollections.add(0, cardCollectionModel);
                            setupCarouselView();
                            carouselView.setCurrentItem(0);
                        }
                        else if (isAddOrEdit.equals("EDIT")) {
                            int editedCardPosition = data.getIntExtra(FlipCardAddActivity.EDITED_CARD_POSITION, 0);
                            listCardCollections.set(editedCardPosition, cardCollectionModel);
                            setupCarouselView();
                            carouselView.setCurrentItem(editedCardPosition);
                        }

                    }
                }
            });

    private void setupCarouselView() {
        carouselView.setSize(listCardCollections.size());
        carouselView.setResource(R.layout.card_view_carousel);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                String cardName = listCardCollections.get(position).getCardName().toString();

                CardView cardViewCarouselItem;
                TextView cardNameTextView;
                ImageButton deleteImageButton;

                cardViewCarouselItem = view.findViewById(R.id.cardView_flip_card_carousel);
                cardNameTextView = view.findViewById(R.id.txt_flip_card_name);
                deleteImageButton = view.findViewById(R.id.btn_flip_card_delete);
                cardNameTextView.setText(cardName);

                cardViewCarouselItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentToFlipCardAdd = new Intent(getApplicationContext(), FlipCardAddActivity.class);
                        intentToFlipCardAdd.putExtra(FlipCardMenuActivity.IS_ADD_OR_EDIT, "EDIT");
                        intentToFlipCardAdd.putExtra(FlipCardMenuActivity.CARD_COLLECTION_TO_EDIT, listCardCollections.get(position));
                        intentToFlipCardAdd.putExtra(FlipCardMenuActivity.CARD_POSITION_TO_EDIT, position);
                        intentLauncher.launch(intentToFlipCardAdd);

                    }
                });

                deleteImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(FlipCardMenuActivity.this)
                                .setTitle("Hộp thư xác nhận")
                                .setMessage("Bạn có chắc muốn xóa thẻ bài \"" + cardName + "\"?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        presenter.deleteAllCardsInCollection(listCardCollections.get(position).getId());
                                        presenter.deleteCardCollection(listCardCollections.get(position));

                                        listCardCollections.remove(position);
                                        setupCarouselView();
                                        Toast.makeText(FlipCardMenuActivity.this, "Đã xóa thẻ bài", Toast.LENGTH_SHORT).show();
                                    }})
                                .setNegativeButton("Hủy", null).show();
                    }
                });
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();
    }

}

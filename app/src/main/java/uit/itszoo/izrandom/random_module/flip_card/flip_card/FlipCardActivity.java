package uit.itszoo.izrandom.random_module.flip_card.flip_card;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.jama.carouselview.CarouselViewListener;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuActivity;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuContract;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuPresenter;
import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

public class FlipCardActivity extends AppCompatActivity implements FlipCardContract.View {
    CardCollectionModel cardCollectionModel;
    List<CardModel> listCardModels = new ArrayList<>();

    FlipCardContract.Presenter presenter;
    ImageButton backButton;
    Button backToMenuButton;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_card);

        presenter = new FlipCardPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        cardCollectionModel = (CardCollectionModel) getIntent().getSerializableExtra(FlipCardMenuActivity.CARD_COLLECTION_TO_FLIP);
        listCardModels = presenter.getListCardModelByCollectionId(cardCollectionModel.getId());
        Collections.shuffle(listCardModels);

        initView();
        setListenerForView();
        setUpGridView();

    }

    private void initView() {
        backButton = findViewById(R.id.bb_flip_card);
        gridView = findViewById(R.id.flip_card_grid_view);
        backToMenuButton = findViewById(R.id.btn_flip_card_back_to_menu);
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        backToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void setPresenter(FlipCardContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void setUpGridView() {
        int numberOfColumns = calNumColumnGridView(listCardModels.size());
        int cardWidth = calCardWidthGridView(numberOfColumns);
        float cardContentTextSize = calCardContentTextSize(numberOfColumns);
        int verticalSpacing = calGridViewVerticalSpacing(numberOfColumns);

        gridView.setNumColumns(numberOfColumns);
        gridView.setVerticalSpacing(verticalSpacing);
        CardItemAdapter cardItemAdapter = new CardItemAdapter(this, FlipCardActivity.this, listCardModels, cardWidth, cardContentTextSize);
        gridView.setAdapter(cardItemAdapter);

    }

    private int calNumColumnGridView(int numberOfItems) {
        if (numberOfItems == 1) return 1;
        else if (numberOfItems == 2 || numberOfItems == 4) return 2;
        else if (numberOfItems == 3 || numberOfItems == 5 || numberOfItems == 6 || numberOfItems == 9) return 3;
        else if (numberOfItems == 7 || numberOfItems == 8 || (numberOfItems >= 10 && numberOfItems <= 16)) return 4;
        else if (numberOfItems >= 17) return 5;
        return 5;
    }

    private int calCardWidthGridView(int numberOfColumns) {
        if (numberOfColumns == 1) return 240;
        else if (numberOfColumns == 2) return 120;
        else if (numberOfColumns == 3) return 80;
        else if (numberOfColumns == 4) return 60;
        else if (numberOfColumns == 5) return 52;
        return 52;
    }

    private float calCardContentTextSize(int numberOfColumns) {
        if (numberOfColumns == 1) return 17;
        else if (numberOfColumns == 2) return 10;
        else if (numberOfColumns == 3) return 6;
        else if (numberOfColumns == 4) return 5;
        else if (numberOfColumns == 5) return 4;
        return 4;
    }

    private int calGridViewVerticalSpacing(int numberOfColumns) {
        if (numberOfColumns == 1) return 8;
        else if (numberOfColumns == 2) return 48;
        else if (numberOfColumns == 3) return 24;
        else if (numberOfColumns == 4) return 12;
        else if (numberOfColumns == 5) return 0;
        return 0;
    }

}

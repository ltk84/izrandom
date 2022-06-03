package uit.itszoo.izrandom.play_module.truth_dare.truth_dare_cards;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.play_module.truth_dare.game_session.TruthDareGameSessionActivity;
import uit.itszoo.izrandom.play_module.truth_dare.models.TruthDareCard;
import uit.itszoo.izrandom.play_module.truth_dare.models.TruthDareCardView;

public class TruthDareCardsActivity extends AppCompatActivity implements TruthDareCardsContract.View {
    TruthDareCardsContract.Presenter presenter;
    ImageButton addCardButton;
    ImageButton backButton;
    LinearLayout cardHolder;

    MaterialButton startButton;

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truth_dare_cards);

        presenter = new TruthDareCardsPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        initView();
        setListenerForView();

        presenter.getCards().observe(this, truthDareCards -> {
            System.out.println(truthDareCards.size());
            initList(truthDareCards);
        });
    }

    public void initView() {
        addCardButton = findViewById(R.id.add_card_button);
        backButton = findViewById(R.id.bb_truth_dare_cards);
        cardHolder = findViewById(R.id.card_holder);
        startButton = findViewById((R.id.start_button));
    }

    public void setListenerForView() {
        addCardButton.setOnClickListener(view -> {
//            TruthDareCardView card = new TruthDareCardView(this);
//            cardHolder.addView(card);
            presenter.addCard();
        });
        backButton.setOnClickListener(view -> onBackPressed());
        startButton.setOnClickListener(view -> {
            ArrayList<String> cards = new ArrayList<>();
            int cardHolderCount = cardHolder.getChildCount();
            for (int i = 0; i < cardHolderCount; i++) {
                TruthDareCard cardInfo = ((TruthDareCardView) cardHolder.getChildAt(i)).getCardInfo();
                int cardInfoCount = cardInfo.getCount();
                for (int j = 0; j < cardInfoCount; j++) {
                    cards.add(cardInfo.getContent());
                }
            }
            Collections.shuffle(cards);

            Intent intentToGameSession = new Intent(getApplicationContext(), TruthDareGameSessionActivity.class);
            intentToGameSession.putStringArrayListExtra("cards", cards);
            intentLauncher.launch(intentToGameSession);
        });
    }

    @Override
    public void setPresenter(TruthDareCardsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    void initList(List<TruthDareCard> cards) {
        cardHolder.removeAllViews();
        for (TruthDareCard card :
                cards) {
            TruthDareCardView cardView = new TruthDareCardView(this);
            cardView.setData(card);
            cardView.setCallBack(new TruthDareCardView.MyCallBackInterface() {
                @Override
                public void editCard(TruthDareCard editedCard) {
                    presenter.editCard(editedCard);
                }

                @Override
                public void deleteCard(String id) {
                    presenter.deleteCard(id);
                }
            });
            cardHolder.addView(cardView);
        }
    }
}
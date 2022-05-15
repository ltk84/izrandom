package uit.itszoo.izrandom.play_module.truth_dare.truth_dare_cards;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.play_module.truth_dare.game_session.TruthDareGameSessionActivity;
import uit.itszoo.izrandom.play_module.truth_dare.models.TruthDareCardView;

public class TruthDareCardsActivity extends AppCompatActivity {

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

        initView();
        setListenerForView();
    }

    public void initView() {
        addCardButton = findViewById(R.id.add_card_button);
        backButton = findViewById(R.id.bb_truth_dare_cards);
        cardHolder = findViewById(R.id.card_holder);
        startButton = findViewById((R.id.start_button));
    }

    public void setListenerForView() {
        addCardButton.setOnClickListener(view -> {
            TruthDareCardView card = new TruthDareCardView(this);
            cardHolder.addView(card);
        });
        backButton.setOnClickListener(view -> onBackPressed());
        startButton.setOnClickListener(view -> {
            Intent intentToGameSession = new Intent(getApplicationContext(), TruthDareGameSessionActivity.class);
            intentLauncher.launch(intentToGameSession);
        });
    }
}
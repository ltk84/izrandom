package uit.itszoo.izrandom.play_module.truth_dare;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.play_module.truth_dare.models.TruthDareCardView;
import uit.itszoo.izrandom.play_module.truth_dare.truth_dare_cards.TruthDareCardsActivity;

public class TruthDareActivity extends AppCompatActivity {
    MaterialButton startButton;
    MaterialButton cardButton;

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truth_dare);

        initView();
        setListenerForView();
    }

    public void initView() {
        startButton = findViewById(R.id.start_button);
        cardButton = findViewById(R.id.card_button);
    }


    public void setListenerForView() {
        startButton.setOnClickListener(view -> {
            Intent intentToCard = new Intent(getApplicationContext(), TruthDareCardsActivity.class);
            intentLauncher.launch(intentToCard);
        });
        cardButton.setOnClickListener(view -> {
            Intent intentToCard = new Intent(getApplicationContext(), TruthDareCardsActivity.class);
            intentLauncher.launch(intentToCard);
        });
    }
}
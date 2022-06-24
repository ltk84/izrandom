package uit.itszoo.izrandom.random_module.roll_dice.roll_dice_custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.iigo.library.DiceLoadingView;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.roll_dice.RollDiceActivity;
import uit.itszoo.izrandom.random_module.roll_dice.model.Dice;
import uit.itszoo.izrandom.random_module.roll_dice.source.DiceSource;

public class RollDiceCustomActivity extends AppCompatActivity {
    public static final String SELECTED_DICE = "SELECTED_DICE";
    List<Dice> dicesTheme = (List<Dice>) DiceSource.dices.clone();
    CarouselView carouselView;
    DiceLoadingView diceView;
    ImageButton backButton;
    ImageButton confirmButton;
    Dice initialDice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_dice_custom);
        initialDice = (Dice) getIntent().getSerializableExtra(RollDiceActivity.CURRENT_DICE);
        swapInitialDiceToLead();
        setupCarouselView();
        initView();
    }

    private void swapInitialDiceToLead() {
        int initialDiceIndex = dicesTheme.indexOf(initialDice);
        Collections.swap(dicesTheme, 0, initialDiceIndex);
    }

    private void initView() {
        backButton = findViewById(R.id.bb_roll_dice);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        confirmButton = findViewById(R.id.confirm_button_roll_dice);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent();
                intentBack.putExtra(RollDiceCustomActivity.SELECTED_DICE, dicesTheme.get(carouselView.getCurrentItem()));
                setResult(Activity.RESULT_OK, intentBack);
                finish();
            }
        });
    }

    private void setupCarouselView() {
        carouselView = findViewById(R.id.carouselView);

        carouselView.setSize(dicesTheme.size());
        carouselView.setResource(R.layout.dice_view_carousel);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                diceView = view.findViewById(R.id.dice_view_carousel);
                applyDiceTheme(position);
            }
        });
        // After you finish setting up, show the CarouselView
        carouselView.show();
    }

    private void applyDiceTheme(int id) {
        diceView.setFirstSideDiceBgColor(getResources().getColor(dicesTheme.get(id).getBackgroundColor(), getTheme()));
        diceView.setFirstSideDiceBorderColor(getResources().getColor(dicesTheme.get(id).getBorderColor(), getTheme()));
        diceView.setFirstSidePointColor(getResources().getColor(dicesTheme.get(id).getPointColor(), getTheme()));

        diceView.setSecondSideDiceBgColor(getResources().getColor(dicesTheme.get(id).getBackgroundColor(), getTheme()));
        diceView.setSecondSideDiceBorderColor(getResources().getColor(dicesTheme.get(id).getBorderColor(), getTheme()));
        diceView.setSecondSidePointColor(getResources().getColor(dicesTheme.get(id).getPointColor(), getTheme()));

        diceView.setThirdSideDiceBgColor(getResources().getColor(dicesTheme.get(id).getBackgroundColor(), getTheme()));
        diceView.setThirdSideDiceBorderColor(getResources().getColor(dicesTheme.get(id).getBorderColor(), getTheme()));
        diceView.setThirdSidePointColor(getResources().getColor(dicesTheme.get(id).getPointColor(), getTheme()));

        diceView.setFourthSideDiceBgColor(getResources().getColor(dicesTheme.get(id).getBackgroundColor(), getTheme()));
        diceView.setFourthSideDiceBorderColor(getResources().getColor(dicesTheme.get(id).getBorderColor(), getTheme()));
        diceView.setFourthSidePointColor(getResources().getColor(dicesTheme.get(id).getPointColor(), getTheme()));
    }
}
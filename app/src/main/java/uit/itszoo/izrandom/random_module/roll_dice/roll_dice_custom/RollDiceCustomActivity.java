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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.roll_dice.model.DiceLayout;

public class RollDiceCustomActivity extends AppCompatActivity {
    public static final String SELECTED_DICE = "SELECTED_DICE";
    List<DiceLayout> dicesTheme = new ArrayList<DiceLayout>(Arrays.asList(
            new DiceLayout(R.color.regularColorDiceBackground, R.color.regularColorDiceBorder, R.color.regularColorDicePoint),
            new DiceLayout(R.color.draculaColorDiceBackground, R.color.draculaColorDiceBorder, R.color.draculaColorDicePoint),
            new DiceLayout(R.color.poisonColorDiceBackground, R.color.poisonColorDiceBorder, R.color.poisonColorDicePoint),
            new DiceLayout(R.color.chingChongColorDiceBackground, R.color.chingChongColorDiceBorder, R.color.chingChongColorDicePoint),
            new DiceLayout(R.color.softColorDiceBackground, R.color.softColorDiceBorder, R.color.softColorDicePoint),
            new DiceLayout(R.color.lavenderColorDiceBackground, R.color.lavenderColorDiceBorder, R.color.lavenderColorDicePoint)
    ));
    CarouselView carouselView;
    DiceLoadingView diceView;
    ImageButton backButton;
    ImageButton confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_dice_custom);
        setupCarouselView();
        initView();
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
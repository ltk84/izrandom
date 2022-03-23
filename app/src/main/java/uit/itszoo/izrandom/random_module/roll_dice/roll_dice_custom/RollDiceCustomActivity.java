package uit.itszoo.izrandom.random_module.roll_dice.roll_dice_custom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.iigo.library.DiceLoadingView;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.Map;

import uit.itszoo.izrandom.R;

public class RollDiceCustomActivity extends AppCompatActivity {
    int[][] dicesTheme = {
            { R.color.regularColorDiceBackground, R.color.regularColorDiceBorder, R.color.regularColorDicePoint },
            { R.color.draculaColorDiceBackground, R.color.draculaColorDiceBorder, R.color.draculaColorDicePoint },
            { R.color.poisonColorDiceBackground, R.color.poisonColorDiceBorder, R.color.poisonColorDicePoint },
            { R.color.chingChongColorDiceBackground, R.color.chingChongColorDiceBorder, R.color.chingChongColorDicePoint },
            { R.color.softColorDiceBackground, R.color.softColorDiceBorder, R.color.softColorDicePoint },
            { R.color.lavenderColorDiceBackground, R.color.lavenderColorDiceBorder, R.color.lavenderColorDicePoint },
    };
    CarouselView carouselView;
    DiceLoadingView diceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_dice_custom);
        setupCarouselView();
    }

    private void setupCarouselView() {
        carouselView = findViewById(R.id.carouselView);

        carouselView.setSize(dicesTheme.length);
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
        diceView.setFirstSideDiceBgColor(getResources().getColor(dicesTheme[id][0], getTheme()));
        diceView.setFirstSideDiceBorderColor(getResources().getColor(dicesTheme[id][1], getTheme()));
        diceView.setFirstSidePointColor(getResources().getColor(dicesTheme[id][2], getTheme()));

        diceView.setSecondSideDiceBgColor(getResources().getColor(dicesTheme[id][0], getTheme()));
        diceView.setSecondSideDiceBorderColor(getResources().getColor(dicesTheme[id][1], getTheme()));
        diceView.setSecondSidePointColor(getResources().getColor(dicesTheme[id][2], getTheme()));

        diceView.setThirdSideDiceBgColor(getResources().getColor(dicesTheme[id][0], getTheme()));
        diceView.setThirdSideDiceBorderColor(getResources().getColor(dicesTheme[id][1], getTheme()));
        diceView.setThirdSidePointColor(getResources().getColor(dicesTheme[id][2], getTheme()));

        diceView.setFourthSideDiceBgColor(getResources().getColor(dicesTheme[id][0], getTheme()));
        diceView.setFourthSideDiceBorderColor(getResources().getColor(dicesTheme[id][1], getTheme()));
        diceView.setFourthSidePointColor(getResources().getColor(dicesTheme[id][2], getTheme()));
    }
}
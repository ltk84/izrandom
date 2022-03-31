package uit.itszoo.izrandom.random_module.flip_coin.flip_coin_custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_coin.FlipCoinActivity;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;
import uit.itszoo.izrandom.random_module.flip_coin.source.CoinSource;

public class FlipCoinCustomActivity extends AppCompatActivity {
    public static final String SELECTED_COIN = "SELECTED_COIN";
    CarouselView carouselView;
    ImageButton backButton;
    ImageButton confirmButton;
    List<Coin> coinThemeList;
    ImageView coinView;
    ScaleAnimation scaleAnimation;
    List<ImageView> coinViewList;
    int flipPivot = 0;
    Coin initialCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin_custom);
        initialCoin = (Coin) getIntent().getSerializableExtra(FlipCoinActivity.CURRENT_COIN);
        initView();
        swapInitialCoinToLead();
        setupCarouselView();
    }

    private void swapInitialCoinToLead() {
        int initialCoinIndex = 0;
        for (Coin c :
                coinThemeList) {
            if (c.getId().compareTo(initialCoin.getId()) == 0) {
                initialCoinIndex = coinThemeList.indexOf(c);
                break;
            }
        }
        Collections.swap(coinThemeList, 0, initialCoinIndex);
    }

    private void initView() {
        coinViewList = new ArrayList<ImageView>();
        coinThemeList = (List<Coin>) CoinSource.coins.clone();

        backButton = findViewById(R.id.bb_flip_coin);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        confirmButton = findViewById(R.id.confirm_button_flip_coin);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBack = new Intent();
                intentBack.putExtra(FlipCoinCustomActivity.SELECTED_COIN, coinThemeList.get(carouselView.getCurrentItem()));
                setResult(Activity.RESULT_OK, intentBack);
                finish();
            }
        });
    }

    private void setupCarouselView() {
        carouselView = findViewById(R.id.carouselView);

        carouselView.setSize(coinThemeList.size());
        carouselView.setResource(R.layout.image_view_coin);
        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                coinView = view.findViewById(R.id.coinView);
                coinView.setImageDrawable(getDrawable(coinThemeList.get(position).getDrawableTail()));
                coinViewList.add(coinView);
            }
        });
        carouselView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initScaleAnimation();
                startAnimation();
            }
        }, 300);
        // After you finish setting up, show the CarouselView
        carouselView.show();
    }

    private void startAnimation() {
        for (int i = 0; i < coinViewList.size(); i++) {
            coinViewList.get(i).startAnimation(scaleAnimation);
        }
    }

    public void initScaleAnimation() {
        scaleAnimation = new ScaleAnimation(
                1, 0,
                1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (flipPivot % 2 == 0) {
                    for (int i = 0; i < coinViewList.size(); i++) {
                        ImageView thisCoinView = coinViewList.get(i);
                        Coin thisCoin = coinThemeList.get(i);
                        if (thisCoin.isHead) {
                            thisCoinView.setImageDrawable(getDrawable(thisCoin.getDrawableTail()));
                            thisCoin.isHead = false;
                        } else {
                            thisCoinView.setImageDrawable(getDrawable(thisCoin.getDrawableHead()));
                            thisCoin.isHead = true;
                        }
                    }
                    flipPivot = 0;
                }
                flipPivot++;
            }
        });
    }
}
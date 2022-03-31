package uit.itszoo.izrandom.random_module.flip_coin.flip_coin_custom;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;
import uit.itszoo.izrandom.random_module.roll_dice.roll_dice_custom.RollDiceCustomActivity;

public class FlipCoinCustomActivity extends AppCompatActivity {

    CarouselView carouselView;
    ImageButton backButton;
    ImageButton confirmButton;
    List<Coin> coinThemeList;
    ImageView coinView;
    ScaleAnimation scaleAnimation;
    List<ImageView> coinViewList;

    boolean isHead;
    int flipPivot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin_custom);
        initView();
        isHead = false;
        setupCarouselView();
    }

    private void initView() {
        coinViewList = new ArrayList<ImageView>();
        coinThemeList = new ArrayList<Coin>(
            Arrays.asList(
                new Coin(1, R.drawable.ic_coin_1_head, R.drawable.ic_coin_1_tail, false),
                new Coin(1, R.drawable.ic_coin_2_head, R.drawable.ic_coin_2_tail, false)
            )
        );

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
                coinView.setImageDrawable(getDrawable(coinThemeList.get(position).getDrawableHead()));
                coinView.setId(View.generateViewId());
                coinViewList.add(coinView);
                System.out.println("id" + coinView.getId());
            }
        });
        carouselView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initScaleAnimation();
                System.out.println(coinViewList.size());
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
        scaleAnimation.setDuration(300);
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
                        ImageView thisCoinView = findViewById(coinViewList.get(i).getId());
                        System.out.println(i + " Alo: " + coinViewList.get(i).getId());
                        if (isHead) {
                            thisCoinView.setImageDrawable(getDrawable(R.drawable.ic_random_dice));
                            isHead = false;
                        } else {
                            thisCoinView.setImageDrawable(getDrawable(R.drawable.ic_check_button));
                            isHead = true;
                        }
                    }
                    flipPivot = 0;
                }
                flipPivot++;
            }
        });
    }
}
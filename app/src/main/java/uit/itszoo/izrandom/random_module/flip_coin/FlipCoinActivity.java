package uit.itszoo.izrandom.random_module.flip_coin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import uit.itszoo.izrandom.R;

public class FlipCoinActivity extends AppCompatActivity implements FlipCoinContract.View {
    FlipCoinContract.Presenter presenter;

    ImageView coinView;

    ScaleAnimation scaleAnimation;

    boolean head;
    int flipPivot;
    boolean isCoinFlying;
    Handler handlerHoldEvent;
    Runnable executeHoldEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_coin);

        presenter = new FlipCoinPresenter(getApplicationContext(), this);
        setPresenter(presenter);
        initView();
    }

    @Override
    public void setPresenter(FlipCoinContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                rollForever();
                handlerHoldEvent.postDelayed(executeHoldEvent, 1000);
                return true;
            case (MotionEvent.ACTION_UP):
                handlerHoldEvent.removeCallbacks(executeHoldEvent);
                if (!isCoinFlying) {
                    flipCoinInOne();
                } else {
                    moveCoinDown();
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    public void initView() {
        initScaleAnimation();
        coinView = findViewById(R.id.coinView);
        head = true;
        flipPivot = 0;
        isCoinFlying = false;
        handlerHoldEvent = new Handler();
        executeHoldEvent = () -> {
            isCoinFlying = true;
            moveCoinUp();
        };
    }

    public void flipCoinInOne() {

        // Roll 2D Animation
        scaleAnimation.setRepeatCount(5);

        // Set Move Up Animation
        TranslateAnimation moveUpAnimation = new TranslateAnimation(0, 0, 0, -800);
        moveUpAnimation.setDuration(500);
        moveUpAnimation.setFillAfter(true);
        moveUpAnimation.setInterpolator(new FastOutSlowInInterpolator());

        // Set Move Down Animation
        TranslateAnimation moveDownAnimation = new TranslateAnimation(0, 0, 0, 800);
        moveDownAnimation.setDuration(1500);
        moveDownAnimation.setFillAfter(true);
        moveDownAnimation.setInterpolator(new BounceInterpolator());

        // Combine animations
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(moveUpAnimation);
        animationSet.addAnimation(moveDownAnimation);
        animationSet.setFillAfter(true);

        coinView.startAnimation(animationSet);
    }

    public void moveCoinUp() {

        // Roll 2D Animation
        scaleAnimation.setRepeatCount(Animation.INFINITE);

        // Set Move Up Animation
        TranslateAnimation moveUpAnimation = new TranslateAnimation(0, 0, 0,
                -400);
        moveUpAnimation.setDuration(500);
        moveUpAnimation.setFillAfter(true);
        moveUpAnimation.setInterpolator(new FastOutSlowInInterpolator());

        // Combine animations
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(moveUpAnimation);
        animationSet.setFillAfter(true);

        coinView.startAnimation(animationSet);
    }

    public void moveCoinDown() {

        // Roll 2D Animation
        scaleAnimation.setRepeatCount(5);

        // Set Move Down Animation
        TranslateAnimation moveDownAnimation = new TranslateAnimation(0, 0, -400, 0);
        moveDownAnimation.setDuration(1500);
        moveDownAnimation.setFillAfter(true);
        moveDownAnimation.setInterpolator(new BounceInterpolator());

        // Combine animations
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(moveDownAnimation);
        animationSet.setFillAfter(true);

        coinView.startAnimation(animationSet);
    }

    public void rollForever() {

        // Roll 2D Animation
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        coinView.startAnimation(scaleAnimation);
    }

    public void initScaleAnimation() {
        scaleAnimation = new ScaleAnimation(
                1f, 1f,
                1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(100);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);

        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isCoinFlying = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (flipPivot % 2 == 0) {
                    if (head) {
                        coinView.setImageResource(R.drawable.ic_coin_1_head);
                        head = false;
                    } else {
                        coinView.setImageResource(R.drawable.ic_coin_1_tail);
                        head = true;
                    }
                    flipPivot = 0;
                }
                flipPivot++;
            }
        });
    }
}
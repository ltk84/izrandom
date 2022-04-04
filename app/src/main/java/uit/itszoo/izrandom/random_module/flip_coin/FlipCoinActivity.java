package uit.itszoo.izrandom.random_module.flip_coin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.MotionEventCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_coin.flip_coin_custom.FlipCoinCustomActivity;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;
import uit.itszoo.izrandom.random_module.flip_coin.source.CoinSource;

public class FlipCoinActivity extends AppCompatActivity implements FlipCoinContract.View {
    public static final String CURRENT_COIN = "CURRENT_COIN";
    FlipCoinContract.Presenter presenter;

    // Views
    ViewGroup layout;
    ImageView initCoinView;
    List<ImageView> coinViewList;
    ImageButton toCustomScreenButton;
    ImageButton backButton;
    TextView textGuide;
    TextView textCoinCount;
    TextView textCoinCountValue;
    ImageButton decreaseButton;
    ImageButton increaseButton;
    Drawable defaultBackgroundDecreaseButton;
    Drawable defaultBackgroundIncreaseButton;

    // Animation & Events
    ScaleAnimation scaleAnimation;
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

        presenter.getUserConfig().observe(this, config -> {
            Coin coinFromDB = CoinSource.findCoin(config.coinId);
            presenter.initCoinList(coinViewList, coinFromDB);
            applyChangeCoin(coinFromDB);
        });
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
        layout = findViewById(R.id.layout_flip_coin);

        toCustomScreenButton = findViewById(R.id.custom_button);
        backButton = findViewById(R.id.bb_flip_coin);
        textGuide = findViewById(R.id.txt_guide);
        textCoinCount = findViewById(R.id.text_coin_count);
        textCoinCountValue = findViewById(R.id.text_coin_count_value);

        decreaseButton = findViewById(R.id.btn_decrease_coin_count);
        defaultBackgroundDecreaseButton = decreaseButton.getBackground();
        decreaseButton.setImageAlpha(75);
        decreaseButton.setBackground(null);

        increaseButton = findViewById(R.id.btn_increase_coin_count);
        defaultBackgroundIncreaseButton = increaseButton.getBackground();

        initCoinView = findViewById(R.id.coinView);
        coinViewList = new ArrayList<>();
        coinViewList.add(initCoinView);

        setListenerForView();

        initScaleAnimation();
        flipPivot = 0;
        isCoinFlying = false;
        handlerHoldEvent = new Handler();
        executeHoldEvent = () -> {
            isCoinFlying = true;
            moveCoinUp();
        };
    }

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    System.out.println(result.getResultCode());
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Coin selectedCoin = (Coin) data.getSerializableExtra(FlipCoinCustomActivity.SELECTED_COIN);
                        if (selectedCoin != null) {
                            presenter.changeCoin(selectedCoin);
                        }
                    }
                }
            });

    public void setListenerForView() {
        backButton.setOnClickListener(view -> onBackPressed());

        toCustomScreenButton.setOnClickListener(view -> {
            Intent intentToCustom = new Intent(getApplicationContext(), FlipCoinCustomActivity.class);
            intentToCustom.putExtra(FlipCoinActivity.CURRENT_COIN, presenter.getCoinAppearance());
            intentLauncher.launch(intentToCustom);
            System.out.println("ALo");
        });

        increaseButton.setOnClickListener(view -> {
            if (coinViewList.size() + 1 < 4) {
                addNewCoin();
                textCoinCountValue.setText(String.valueOf(coinViewList.size()));
            }
        });

        decreaseButton.setOnClickListener(view -> {
            if (coinViewList.size() - 1 > 0) {
                removeACoin();
                textCoinCountValue.setText(String.valueOf(coinViewList.size()));
            }
        });

        textCoinCountValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().compareTo("1") == 0) {
                    decreaseButton.setImageAlpha(75);
                    decreaseButton.setBackground(null);
                } else if (charSequence.toString().compareTo("2") == 0) {
                    decreaseButton.setImageAlpha(255);
                    decreaseButton.setBackground(defaultBackgroundDecreaseButton);
                    increaseButton.setImageAlpha(255);
                    increaseButton.setBackground(defaultBackgroundIncreaseButton);
                } else if (charSequence.toString().compareTo("3") == 0) {
                    increaseButton.setImageAlpha(75);
                    increaseButton.setBackground(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void removeACoin() {
        ImageView nextLastCoinView = coinViewList.get(coinViewList.size() - 2);
        ImageView lastCoinView = coinViewList.get(coinViewList.size() - 1);

        layout.removeView(lastCoinView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) layout);
        constraintSet.connect(nextLastCoinView.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END, 0);
        constraintSet.applyTo((ConstraintLayout) layout);

        coinViewList.remove(lastCoinView);
        presenter.removeCoin();
    }

    private void addNewCoin() {
        ImageView lastCoinView = coinViewList.get(coinViewList.size() - 1);
        ImageView newCoinView = (ImageView) LayoutInflater.from(this).inflate(R.layout.image_view_coin, null);
        newCoinView.setId(View.generateViewId());

        Resources r = this.getResources();
        int top = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                56,
                r.getDisplayMetrics()
        );
        int width = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100,
                r.getDisplayMetrics()
        );
        int height = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100,
                r.getDisplayMetrics()
        );
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);
        layoutParams.setMargins(0, top, 0, 0);
        newCoinView.setLayoutParams(layoutParams);
        layout.addView(newCoinView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) layout);
        constraintSet.connect(lastCoinView.getId(), ConstraintSet.END, newCoinView.getId(), ConstraintSet.START, 0);
        constraintSet.connect(newCoinView.getId(), ConstraintSet.START, lastCoinView.getId(), ConstraintSet.END, 0);
        constraintSet.connect(newCoinView.getId(), ConstraintSet.END, R.id.layout_flip_coin, ConstraintSet.END, 0);
        constraintSet.connect(newCoinView.getId(), ConstraintSet.TOP, R.id.toolbar, ConstraintSet.BOTTOM);
        constraintSet.connect(newCoinView.getId(), ConstraintSet.BOTTOM, R.id.layout_flip_coin, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo((ConstraintLayout) layout);

        coinViewList.add(newCoinView);
        presenter.addCoin(newCoinView);
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

        for (int i = 0; i < coinViewList.size(); i++) {
            coinViewList.get(i).startAnimation(animationSet);
        }
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

        for (int i = 0; i < coinViewList.size(); i++) {
            coinViewList.get(i).startAnimation(animationSet);
        }
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

        for (int i = 0; i < coinViewList.size(); i++) {
            coinViewList.get(i).startAnimation(animationSet);
        }
    }

    public void rollForever() {

        // Roll 2D Animation
        scaleAnimation.setRepeatCount(Animation.INFINITE);

        for (int i = 0; i < coinViewList.size(); i++) {
            coinViewList.get(i).startAnimation(scaleAnimation);
        }
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
                textGuide.setVisibility(View.GONE);
                textCoinCount.setVisibility(View.GONE);
                textCoinCountValue.setVisibility(View.GONE);
                decreaseButton.setVisibility(View.GONE);
                increaseButton.setVisibility(View.GONE);
                generateRandomHeadTail();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textGuide.setVisibility(View.VISIBLE);
                textCoinCount.setVisibility(View.VISIBLE);
                textCoinCountValue.setVisibility(View.VISIBLE);
                decreaseButton.setVisibility(View.VISIBLE);
                increaseButton.setVisibility(View.VISIBLE);
                isCoinFlying = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (flipPivot % 2 == 0) {
                    for (int i = 0; i < coinViewList.size(); i++) {
                        Coin thisCoin = presenter.getCoinFromViewId(coinViewList.get(i).getId());
                        if (thisCoin.isHead) {
                            coinViewList.get(i).setImageResource(thisCoin.getDrawableTail());
                            thisCoin.isHead = false;
                        } else {
                            coinViewList.get(i).setImageResource(thisCoin.getDrawableHead());
                            thisCoin.isHead = true;
                        }
                    }
                    flipPivot = 0;
                }
                flipPivot++;
            }
        });
    }

    private void generateRandomHeadTail() {
        Random random = new Random();

        for (int i = 0; i < coinViewList.size(); i++) {
            Coin thisCoin = presenter.getCoinFromViewId(coinViewList.get(i).getId());
            boolean isHead = random.nextBoolean();
            System.out.println(i + " " + isHead);

            if (isHead) {
                coinViewList.get(i).setImageResource(thisCoin.getDrawableHead());
                thisCoin.isHead = true;
            } else {
                coinViewList.get(i).setImageResource(thisCoin.getDrawableTail());
                thisCoin.isHead = false;
            }
        }
    }

    @Override
    public void applyChangeCoin(Coin coin) {
        for (int i = 0; i < coinViewList.size(); i++) {
            coinViewList.get(i).setImageDrawable(getDrawable(coin.getDrawableHead()));
        }
    }
}
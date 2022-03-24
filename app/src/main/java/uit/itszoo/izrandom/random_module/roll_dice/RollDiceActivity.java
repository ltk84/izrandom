package uit.itszoo.izrandom.random_module.roll_dice;

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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
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

import com.iigo.library.DiceLoadingView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.roll_dice.model.Dice;
import uit.itszoo.izrandom.random_module.roll_dice.roll_dice_custom.RollDiceCustomActivity;
import uit.itszoo.izrandom.random_module.roll_dice.source.DiceSource;

public class RollDiceActivity extends AppCompatActivity implements RollDiceContract.View {
    public static final String CURRENT_DICE = "CURRENT_DICE";

    ImageButton toCustomScreenButton;
    ImageButton backButton;
    RollDiceContract.Presenter presenter;
    ViewGroup layout;
    TextView textGuide;
    TextView textDiceCount;
    TextView textDiceCountValue;
    ImageButton decreaseButton;
    ImageButton increaseButton;
    Drawable defaultBackgroundDecreaseButton;
    Drawable defaultBackgroundIncreaseButton;

    // Dynamic view
    DiceLoadingView initDiceView;
    List<DiceLoadingView> diceViewList;

    // Animation variables
    AnimationSet animationSet;
    Animation rotateAnimation;
    TranslateAnimation moveUpAnimation;
    TranslateAnimation moveDownAnimation;
    int resultNumber = 1;
    boolean isDiceFlying = false;
    Handler handlerHoldEvent;
    Runnable executeHoldEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_dice);

        initView();
        setListenerForView();

        presenter = new RollDicePresenter(getApplicationContext(), this);
        setPresenter(presenter);

        presenter.getUserConfig().observe(this, userConfiguration -> {
            presenter.initDice(DiceSource.dices.stream().filter(dice -> dice.getId().compareTo(userConfiguration.diceId) == 0).findFirst().get());
            applyTheme(presenter.getCurrentDice());
        });

        executeHoldEvent = () -> {
            isDiceFlying = true;
            executeMoveUpAndRoll(diceViewList);
        };
        handlerHoldEvent = new Handler();
    }

    @Override
    public void applyTheme(Dice layout) {
        initDiceView.setFirstSideDiceBgColor(getResources().getColor(layout.getBackgroundColor(), getTheme()));
        initDiceView.setFirstSideDiceBorderColor(getResources().getColor(layout.getBorderColor(), getTheme()));
        initDiceView.setFirstSidePointColor(getResources().getColor(layout.getPointColor(), getTheme()));

        initDiceView.setSecondSideDiceBgColor(getResources().getColor(layout.getBackgroundColor(), getTheme()));
        initDiceView.setSecondSideDiceBorderColor(getResources().getColor(layout.getBorderColor(), getTheme()));
        initDiceView.setSecondSidePointColor(getResources().getColor(layout.getPointColor(), getTheme()));

        initDiceView.setThirdSideDiceBgColor(getResources().getColor(layout.getBackgroundColor(), getTheme()));
        initDiceView.setThirdSideDiceBorderColor(getResources().getColor(layout.getBorderColor(), getTheme()));
        initDiceView.setThirdSidePointColor(getResources().getColor(layout.getPointColor(), getTheme()));

        initDiceView.setFourthSideDiceBgColor(getResources().getColor(layout.getBackgroundColor(), getTheme()));
        initDiceView.setFourthSideDiceBorderColor(getResources().getColor(layout.getBorderColor(), getTheme()));
        initDiceView.setFourthSidePointColor(getResources().getColor(layout.getPointColor(), getTheme()));
    }

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Dice selectedDice = (Dice) data.getSerializableExtra(RollDiceCustomActivity.SELECTED_DICE);
                        if (selectedDice != null) {
                            presenter.changeDice(selectedDice);
                        }

                    }
                }
            });


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                executeOnlyRoll(diceViewList);
                handlerHoldEvent.postDelayed(executeHoldEvent, 1000);
                return true;
            case (MotionEvent.ACTION_UP):
                handlerHoldEvent.removeCallbacks(executeHoldEvent);
                if (!isDiceFlying) {
                    executeRollInOne(diceViewList);
                } else {
                    executeMoveDown(diceViewList);
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void initView() {
        backButton = findViewById(R.id.bb_roll_dice);
        layout = findViewById(R.id.layout_roll_dice);
        initDiceView = findViewById(R.id.diceView);
        initDiceView.setDuration(0);
        initDiceView.setRepeatCount(0);
        textGuide = findViewById(R.id.txt_guide);
        toCustomScreenButton = findViewById(R.id.custom_button);
        textDiceCount = findViewById(R.id.text_dice_count);
        textDiceCountValue = findViewById(R.id.text_dice_count_value);

        decreaseButton = findViewById(R.id.btn_decrease_dice_count);
        defaultBackgroundDecreaseButton = decreaseButton.getBackground();
        decreaseButton.setImageAlpha(75);

        increaseButton = findViewById(R.id.btn_increase_dice_count);
        defaultBackgroundIncreaseButton = increaseButton.getBackground();

        decreaseButton.setBackground(null);
        diceViewList = new ArrayList<>();
        diceViewList.add(initDiceView);

    }


    public void setListenerForView() {
        backButton.setOnClickListener(view -> onBackPressed());

        toCustomScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCustom = new Intent(getApplicationContext(), RollDiceCustomActivity.class);
                intentToCustom.putExtra(RollDiceActivity.CURRENT_DICE, presenter.getCurrentDice());
                intentLauncher.launch(intentToCustom);
            }
        });

        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (diceViewList.size() + 1 < 5) {
                    addNewDice();
                    textDiceCountValue.setText(String.valueOf(diceViewList.size()));
                }
            }
        });

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (diceViewList.size() - 1 > 0) {
                    removeADice();
                    textDiceCountValue.setText(String.valueOf(diceViewList.size()));
                }
            }
        });

        textDiceCountValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().compareTo("1") == 0) {
                    decreaseButton.setImageAlpha(75);
                    decreaseButton.setBackground(null);
                } else if (charSequence.toString().compareTo("4") == 0) {
                    increaseButton.setImageAlpha(75);
                    increaseButton.setBackground(null);
                } else if (charSequence.toString().compareTo("2") == 0) {
                    decreaseButton.setImageAlpha(255);
                    decreaseButton.setBackground(defaultBackgroundDecreaseButton);
                } else if (charSequence.toString().compareTo("3") == 0) {
                    increaseButton.setImageAlpha(255);
                    increaseButton.setBackground(defaultBackgroundIncreaseButton);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void setPresenter(RollDiceContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void executeRollInOne(List<DiceLoadingView> diceViewList) {

        // Set 2D Rotate Animation
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(2);
        rotateAnimation.setDuration(400);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        // Set Move Up Animation
        moveUpAnimation = new TranslateAnimation(0, 0, 0, -800);
        moveUpAnimation.setDuration(500);
        moveUpAnimation.setFillAfter(true);
        moveUpAnimation.setInterpolator(new FastOutSlowInInterpolator());
        // Set Move Down Animation
        moveDownAnimation = new TranslateAnimation(0, 0, 0, 800);
        moveDownAnimation.setDuration(1500);
        moveDownAnimation.setFillAfter(true);
        moveDownAnimation.setInterpolator(new BounceInterpolator());

        // Combine animations
        animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(moveUpAnimation);
        animationSet.addAnimation(moveDownAnimation);
        animationSet.setFillAfter(true);

        for (int i = 0; i <  diceViewList.size(); i++) {
            // Set result to dice view
            Random rand = new Random();
            int randomValue = rand.nextInt(7);
            resultNumber = randomValue;
            diceViewList.get(i).setFirstSideDiceNumber(randomValue);

            // Set 3d Rotate Animation
            diceViewList.get(i).setInterpolator(new LinearInterpolator());
            diceViewList.get(i).setRepeatCount(2);
            diceViewList.get(i).setDuration(400);
            diceViewList.get(i).startAnimation(animationSet);
        }

        setRotateAnimationListener();
    }

    @Override
    public void executeMoveUpAndRoll(List<DiceLoadingView> diceViewList) {

        // Set 2D Rotate Forever Animation
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        // Set Move Up Animation
        moveUpAnimation = new TranslateAnimation(0, 0, 0,
                -400);
        moveUpAnimation.setDuration(500);
        moveUpAnimation.setFillAfter(true);
        moveUpAnimation.setInterpolator(new FastOutSlowInInterpolator());

        animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(moveUpAnimation);
        animationSet.setFillAfter(true);


        for (int i = 0; i <  diceViewList.size(); i++) {
            // Set 3d Rotate Animation.
            diceViewList.get(i).setInterpolator(new LinearInterpolator());
            diceViewList.get(i).setRepeatCount(Animation.INFINITE);
            diceViewList.get(i).setDuration(400);

            diceViewList.get(i).startAnimation(animationSet);
        }

        setRotateAnimationListener();
    }

    @Override
    public void executeMoveDown(List<DiceLoadingView> diceViewList) {

        // Set 2D Rotate Animation
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(2);
        rotateAnimation.setDuration(400);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        // Set Move Down Animation
        moveDownAnimation = new TranslateAnimation(0, 0, -400, 0);
        moveDownAnimation.setDuration(1500);
        moveDownAnimation.setFillAfter(true);
        moveDownAnimation.setInterpolator(new BounceInterpolator());

        // Combine animations
        animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(moveDownAnimation);
        animationSet.setFillAfter(true);

        for (int i = 0; i <  diceViewList.size(); i++) {
            // Set result to dice view
            Random rand = new Random();
            int randomValue = rand.nextInt(7);
            resultNumber = randomValue;
            diceViewList.get(i).setFirstSideDiceNumber(randomValue);

            // Set 3d Rotate Animation
            diceViewList.get(i).setInterpolator(new LinearInterpolator());
            diceViewList.get(i).setRepeatCount(2);
            diceViewList.get(i).setDuration(400);

            diceViewList.get(i).startAnimation(animationSet);
        }

        setRotateAnimationListener();
    }

    @Override
    public void executeOnlyRoll(List<DiceLoadingView> diceViewList) {

        // Set 2D Rotate Forever Animation
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(600);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        for (int i = 0; i <  diceViewList.size(); i++) {
            // Set 3d Rotate Animation.
            diceViewList.get(i).setInterpolator(new LinearInterpolator());
            diceViewList.get(i).setRepeatCount(Animation.INFINITE);
            diceViewList.get(i).setDuration(400);

            diceViewList.get(i).startAnimation(rotateAnimation);
        }

        setRotateAnimationListener();
    }

    private void setRotateAnimationListener() {
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textGuide.setVisibility(View.GONE);
                textDiceCount.setVisibility(View.GONE);
                textDiceCountValue.setVisibility(View.GONE);
                decreaseButton.setVisibility(View.GONE);
                increaseButton.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textGuide.setVisibility(View.VISIBLE);
                textDiceCount.setVisibility(View.VISIBLE);
                textDiceCountValue.setVisibility(View.VISIBLE);
                decreaseButton.setVisibility(View.VISIBLE);
                increaseButton.setVisibility(View.VISIBLE);
                isDiceFlying = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void addNewDice() {
        DiceLoadingView lastDiceView = diceViewList.get(diceViewList.size() - 1);
        DiceLoadingView newDiceView = (DiceLoadingView) LayoutInflater.from(this).inflate(R.layout.dice_view_carousel, null);
        newDiceView.setId(View.generateViewId());
        newDiceView.setDuration(0);
        newDiceView.setRepeatCount(0);

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
        layoutParams.setMargins(0,top,0,0);
        newDiceView.setLayoutParams(layoutParams);
        layout.addView(newDiceView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) layout);
        constraintSet.connect(lastDiceView.getId(),ConstraintSet.END,newDiceView.getId(),ConstraintSet.START,0);
        constraintSet.connect(newDiceView.getId(),ConstraintSet.START,lastDiceView.getId(),ConstraintSet.END,0);
        constraintSet.connect(newDiceView.getId(),ConstraintSet.END,R.id.layout_roll_dice,ConstraintSet.END,0);
        constraintSet.connect(newDiceView.getId(),ConstraintSet.TOP,R.id.toolbar,ConstraintSet.BOTTOM);
        constraintSet.connect(newDiceView.getId(),ConstraintSet.BOTTOM,R.id.layout_roll_dice,ConstraintSet.BOTTOM,0);
        constraintSet.applyTo((ConstraintLayout) layout);

        diceViewList.add(newDiceView);
    }

    private void removeADice() {
        DiceLoadingView nextLastDiceView = diceViewList.get(diceViewList.size() - 2);
        DiceLoadingView lastDiceView = diceViewList.get(diceViewList.size() - 1);

        layout.removeView(lastDiceView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) layout);
        constraintSet.connect(nextLastDiceView.getId(),ConstraintSet.END, layout.getId(),ConstraintSet.END,0);
        constraintSet.applyTo((ConstraintLayout) layout);

        diceViewList.remove(lastDiceView);
    }
}
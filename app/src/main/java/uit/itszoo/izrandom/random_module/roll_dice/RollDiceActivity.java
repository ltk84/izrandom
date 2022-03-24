package uit.itszoo.izrandom.random_module.roll_dice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.core.view.MotionEventCompat;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.iigo.library.DiceLoadingView;

import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.roll_dice.model.DiceLayout;
import uit.itszoo.izrandom.random_module.roll_dice.roll_dice_custom.RollDiceCustomActivity;

public class RollDiceActivity extends AppCompatActivity implements RollDiceContract.View {
    public static final String CURRENT_DICE = "CURRENT_DICE";

    ImageButton toCustomScreenButton;
    ImageButton backButton;
    RollDiceContract.Presenter rollDicePresenter;
    ViewGroup layout;
    DiceLoadingView diceView;
    TextView textGuide;

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

        rollDicePresenter = new RollDicePresenter(this);
        setPresenter(rollDicePresenter);

        executeHoldEvent = () -> {
            isDiceFlying = true;
            executeMoveUpAndRoll();
        };
        handlerHoldEvent = new Handler();
    }

    @Override
    public void applyTheme(DiceLayout layout) {
        diceView.setFirstSideDiceBgColor(getResources().getColor(layout.getBackgroundColor(), getTheme()));
        diceView.setFirstSideDiceBorderColor(getResources().getColor(layout.getBorderColor(), getTheme()));
        diceView.setFirstSidePointColor(getResources().getColor(layout.getPointColor(), getTheme()));

        diceView.setSecondSideDiceBgColor(getResources().getColor(layout.getBackgroundColor(), getTheme()));
        diceView.setSecondSideDiceBorderColor(getResources().getColor(layout.getBorderColor(), getTheme()));
        diceView.setSecondSidePointColor(getResources().getColor(layout.getPointColor(), getTheme()));

        diceView.setThirdSideDiceBgColor(getResources().getColor(layout.getBackgroundColor(), getTheme()));
        diceView.setThirdSideDiceBorderColor(getResources().getColor(layout.getBorderColor(), getTheme()));
        diceView.setThirdSidePointColor(getResources().getColor(layout.getPointColor(), getTheme()));

        diceView.setFourthSideDiceBgColor(getResources().getColor(layout.getBackgroundColor(), getTheme()));
        diceView.setFourthSideDiceBorderColor(getResources().getColor(layout.getBorderColor(), getTheme()));
        diceView.setFourthSidePointColor(getResources().getColor(layout.getPointColor(), getTheme()));
    }

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        DiceLayout selectedDice = (DiceLayout) data.getSerializableExtra(RollDiceCustomActivity.SELECTED_DICE);
                        if (selectedDice != null) {
                            applyTheme(selectedDice);
                        }

                    }
                }
            });


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                executeOnlyRoll();
                handlerHoldEvent.postDelayed(executeHoldEvent, 1000);
                return true;
            case (MotionEvent.ACTION_UP):
                handlerHoldEvent.removeCallbacks(executeHoldEvent);
                if (!isDiceFlying) {
                    executeRollInOne();
                } else {
                    executeMoveDown();
                }
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void initView() {
        backButton = findViewById(R.id.bb_roll_dice);
        layout = findViewById(R.id.layout_roll_dice);
        diceView = findViewById(R.id.diceView);
        diceView.setDuration(0);
        diceView.setRepeatCount(0);
        textGuide = findViewById(R.id.txt_guide);
        toCustomScreenButton = findViewById(R.id.custom_button);
    }


    public void setListenerForView() {
        backButton.setOnClickListener(view -> onBackPressed());

        toCustomScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCustom = new Intent(getApplicationContext(), RollDiceCustomActivity.class);
                intentLauncher.launch(intentToCustom);
            }
        });

    }

    @Override
    public void setPresenter(RollDiceContract.Presenter presenter) {
        this.rollDicePresenter = presenter;
    }

    @Override
    public void executeRollInOne() {

        // Set result to dice view
        Random rand = new Random();
        int randomValue = rand.nextInt(7);
        resultNumber = randomValue;
        diceView.setFirstSideDiceNumber(randomValue);

        // Set 2D Rotate Animation
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(3);
        rotateAnimation.setDuration(800);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        // Set 3d Rotate Animation
        diceView.setInterpolator(new LinearInterpolator());
        diceView.setRepeatCount(7);
        diceView.setDuration(400);

        // Set Move Up Animation
        moveUpAnimation = new TranslateAnimation(0, 0, 0, -800);
        moveUpAnimation.setDuration(500);
        moveUpAnimation.setFillAfter(true);
        moveUpAnimation.setInterpolator(new FastOutSlowInInterpolator());
        // Set Move Down Animation
        moveDownAnimation = new TranslateAnimation(0, 0, 0, 800);
        moveDownAnimation.setDuration(2500);
        moveDownAnimation.setFillAfter(true);
        moveDownAnimation.setInterpolator(new BounceInterpolator());

        // Combine animations
        animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(moveUpAnimation);
        animationSet.addAnimation(moveDownAnimation);
        animationSet.setFillAfter(true);

        diceView.startAnimation(animationSet);

        setRotateAnimationListener();
    }

    @Override
    public void executeMoveUpAndRoll() {

        // Set 2D Rotate Forever Animation
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        // Set 3d Rotate Animation.
        diceView.setInterpolator(new LinearInterpolator());
        diceView.setRepeatCount(Animation.INFINITE);
        diceView.setDuration(400);

        // Set Move Up Animation
        moveUpAnimation = new TranslateAnimation(0, 0, 0,
                -400);
        moveUpAnimation.setDuration(500);
        moveUpAnimation.setFillAfter(true);
        moveUpAnimation.setInterpolator(new FastOutSlowInInterpolator());

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(moveUpAnimation);
        animationSet.setFillAfter(true);

        diceView.startAnimation(animationSet);

        setRotateAnimationListener();
    }

    @Override
    public void executeMoveDown() {

        // Set result to dice view
        Random rand = new Random();
        int randomValue = rand.nextInt(7);
        resultNumber = randomValue;
        diceView.setFirstSideDiceNumber(randomValue);

        // Set 2D Rotate Animation
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(3);
        rotateAnimation.setDuration(800);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        // Set 3d Rotate Animation
        diceView.setInterpolator(new LinearInterpolator());
        diceView.setRepeatCount(7);
        diceView.setDuration(400);

        // Set Move Down Animation
        moveDownAnimation = new TranslateAnimation(0, 0, -400, 0);
        moveDownAnimation.setDuration(2500);
        moveDownAnimation.setFillAfter(true);
        moveDownAnimation.setInterpolator(new BounceInterpolator());

        // Combine animations
        animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(moveDownAnimation);
        animationSet.setFillAfter(true);

        diceView.startAnimation(animationSet);

        setRotateAnimationListener();
    }

    @Override
    public void executeOnlyRoll() {

        // Set 2D Rotate Forever Animation
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);

        // Set 3d Rotate Animation.
        diceView.setInterpolator(new LinearInterpolator());
        diceView.setRepeatCount(Animation.INFINITE);
        diceView.setDuration(400);

        diceView.startAnimation(rotateAnimation);

        setRotateAnimationListener();
    }

    private void setRotateAnimationListener() {
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textGuide.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textGuide.setVisibility(View.VISIBLE);
                isDiceFlying = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
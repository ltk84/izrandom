package uit.itszoo.izrandom.random_module.roll_dice;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import uit.itszoo.izrandom.random_module.random_direction.RandomDirectionContract;
import uit.itszoo.izrandom.random_module.random_direction.RandomDirectionPresenter;
import uit.itszoo.izrandom.random_module.random_direction.random_direction_custom.RandomDirectionCustomActivity;

public class RollDiceActivity extends AppCompatActivity implements RollDiceContract.View  {
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

    float lastPosition;

    float moveDownDeltaPivot = 0;
    float moveDownDelta = 800;

    boolean isDiceMoveUp = false;

    int resultNumber = 1;
    float lastDegree = 0;

    private static final String TAG = "AnimationStarter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_dice);

        initView();
        setListenerForView();

        rollDicePresenter = new RollDicePresenter(this);
        setPresenter(rollDicePresenter);
    }

//    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        Intent data = result.getData();
//                        int selectedArrow = data.getIntExtra(RandomDirectionCustomActivity.SELECTED_ARROW, 0);
//                        if (selectedArrow != 0) {
//                            arrowView.setImageDrawable(getDrawable(selectedArrow));
//                        }
//                    }
//                }
//            });


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                executeSpinForever();
                return true;
            case (MotionEvent.ACTION_UP):
                executeSpin();
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
        lastPosition = diceView.getY();
        textGuide = findViewById(R.id.txt_guide);
        toCustomScreenButton = findViewById(R.id.custom_button);
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        toCustomScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentToCustom = new Intent(getApplicationContext(), RandomDirectionCustomActivity.class);
//                intentLauncher.launch(intentToCustom);
//            }
//        });

    }

    @Override
    public void setPresenter(RollDiceContract.Presenter presenter) {
        this.rollDicePresenter = presenter;
    }

    @Override
    public void executeSpin() {

        // Set result to dice view
        Random rand = new Random();
        int randomValue = rand.nextInt(7);
        resultNumber = randomValue;
        diceView.setFirstSideDiceNumber(randomValue);

        // Set 2D Rotate Animation
        rotateAnimation = new RotateAnimation(lastDegree, 360, Animation.RELATIVE_TO_SELF,
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
        moveUpAnimation = new TranslateAnimation(0, 0, 0, -moveDownDelta);
        moveUpAnimation.setDuration(500);
        moveUpAnimation.setFillAfter(true);
        moveUpAnimation.setInterpolator(new FastOutSlowInInterpolator());
        // Set Move Down Animation
        moveDownAnimation = new TranslateAnimation(0, 0, moveDownDeltaPivot, moveDownDelta);
        moveDownAnimation.setDuration(2500);
        moveDownAnimation.setFillAfter(true);
        moveDownAnimation.setInterpolator(new BounceInterpolator());

        // Combine animations
        animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        if (!isDiceMoveUp) {
            animationSet.addAnimation(moveUpAnimation);
        }
        animationSet.addAnimation(moveDownAnimation);
        animationSet.setFillAfter(true);

        diceView.startAnimation(animationSet);

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
                isDiceMoveUp = false;
                moveDownDelta = 800;
                moveDownDeltaPivot = 0;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        moveUpAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isDiceMoveUp = true;
                moveDownDelta = 0;
                moveDownDeltaPivot = -400;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void executeSpinForever() {

        // Set 2D Rotate Forever Animation
        rotateAnimation = new RotateAnimation(lastDegree, 360, Animation.RELATIVE_TO_SELF,
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

}
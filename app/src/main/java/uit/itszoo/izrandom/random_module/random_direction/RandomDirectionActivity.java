package uit.itszoo.izrandom.random_module.random_direction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.random_direction.random_direction_custom.RandomDirectionCustomActivity;

public class RandomDirectionActivity extends AppCompatActivity implements RandomDirectionContract.View {
    ImageButton toCustomScreenButton;
    ImageButton backButton;
    RandomDirectionContract.Presenter randDirPresenter;
    ViewGroup layout;
    Animation rotateAnimation;
    ImageView arrowView;
    TextView textGuide;
    float lastPosition = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_direction);

        initView();
        setListenerForView();

        randDirPresenter = new RandomDirectionPresenter(this);
        setPresenter(randDirPresenter);
    }

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int selectedArrow = data.getIntExtra(RandomDirectionCustomActivity.SELECTED_ARROW, 0);
                        if (selectedArrow != 0) {
                            arrowView.setImageDrawable(getDrawable(selectedArrow));
                        }
                    }
                }
            });


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
        backButton = findViewById(R.id.bb_rand_dir);
        layout = findViewById(R.id.layout_random_direction);
        arrowView = findViewById(R.id.im_arrow);
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

        toCustomScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCustom = new Intent(getApplicationContext(), RandomDirectionCustomActivity.class);
                intentLauncher.launch(intentToCustom);
            }
        });

    }

    @Override
    public void setPresenter(RandomDirectionContract.Presenter presenter) {
        this.randDirPresenter = presenter;
    }

    @Override
    public void executeSpin() {
        Random rand = new Random();
        int randomValue = rand.nextInt(361);

        rotateAnimation = new RotateAnimation(lastPosition, 360 * 10 + randomValue, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3500);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setFillAfter(true);
        arrowView.startAnimation(rotateAnimation);

        lastPosition = randomValue;

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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    @Override
    public void executeSpinForever() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(300);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        arrowView.startAnimation(rotateAnimation);

        setRotateAnimationListener();
    }
}
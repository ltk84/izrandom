package uit.itszoo.izrandom.random_direction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_direction_custom.RandomDirectionCustomActivity;

public class RandomDirectionActivity extends AppCompatActivity implements RandomDirectionContract.View {
    ImageButton customButton;

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

        customButton = findViewById(R.id.custom_button);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRandomDirectionCustom = new Intent(getApplicationContext(), RandomDirectionCustomActivity.class);
                startActivity(intentToRandomDirectionCustom);
            }
        });
        initView();
        setListenerForView();

        randDirPresenter = new RandomDirectionPresenter(this);
        setPresenter(randDirPresenter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                System.out.println("Down");
                executeSpinForever();

                return true;

            case (MotionEvent.ACTION_UP):
                executeSpin();
                return true;

            default:
                return super.onTouchEvent(event);
        }
    }

    public void initView() {
        backButton = findViewById(R.id.back_button);
        layout = findViewById(R.id.layout_random_direction);
        arrowView = findViewById(R.id.im_arrow);
        textGuide = findViewById(R.id.txt_guide);

    }

    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
    }
}
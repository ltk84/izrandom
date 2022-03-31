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
import androidx.core.content.ContextCompat;
import androidx.core.view.MotionEventCompat;

import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.random_direction.model.Arrow;
import uit.itszoo.izrandom.random_module.random_direction.random_direction_custom.RandomDirectionCustomActivity;
import uit.itszoo.izrandom.random_module.random_direction.source.ArrowSource;

public class RandomDirectionActivity extends AppCompatActivity implements RandomDirectionContract.View {
    public static final String CURRENT_ARROW = "CURRENT_ARROW";

    ImageButton toCustomScreenButton;
    ImageButton backButton;
    RandomDirectionContract.Presenter presenter;
    ViewGroup layout;
    Animation rotateAnimation;
    ImageView arrowView;
    TextView textGuide;
    View blinkView;
    Animation blinkAnimation;
    float lastPosition = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_direction);

        initView();
        setListenerForView();

        presenter = new RandomDirectionPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        presenter.getUserConfig().observe(this, config -> {
            presenter.initArrow(ArrowSource.arrows.stream().filter(arrow -> arrow.getId().compareTo(config.arrowId) == 0).findFirst().get());
            arrowView.setImageDrawable(getDrawable(presenter.getCurrentArrow().getLayout()));
        });
    }

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Arrow selectedArrow = (Arrow) data.getSerializableExtra(RandomDirectionCustomActivity.SELECTED_ARROW);
                        if (selectedArrow != null) {
                            presenter.changeArrow(selectedArrow);
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
                intentToCustom.putExtra(RandomDirectionActivity.CURRENT_ARROW, presenter.getCurrentArrow());
                intentLauncher.launch(intentToCustom);
            }
        });

    }

    @Override
    public void setPresenter(RandomDirectionContract.Presenter presenter) {
        this.presenter = presenter;
    }

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

                if (blinkView != null) {
                    blinkView.clearAnimation();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //textGuide.setVisibility(View.VISIBLE);
                float arrowRotation = lastPosition + 45;
                if (arrowRotation >= 0 && arrowRotation < 90) {
                    executeBlinkEffect(2);
                } else if (arrowRotation >= 90 && arrowRotation < 180) {
                    executeBlinkEffect(3);
                } else if (arrowRotation >= 180 && arrowRotation < 225) {
                    executeBlinkEffect(4);
                } else {
                    executeBlinkEffect(1);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void executeSpinForever() {
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(150);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);
        arrowView.startAnimation(rotateAnimation);

        setRotateAnimationListener();
    }

    private void executeBlinkEffect(int quadrant) {
        blinkAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_animation);


        switch (quadrant) {
            case 1:
                blinkView = findViewById(R.id.blinking_rectangle_1);
                break;
            case 2:
                blinkView = findViewById(R.id.blinking_rectangle_2);
                break;
            case 3:
                blinkView = findViewById(R.id.blinking_rectangle_3);
                break;
            default:
                blinkView = findViewById(R.id.blinking_rectangle_4);
                break;
        }

        blinkView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blinking_rectangle));
        blinkView.startAnimation(blinkAnimation);

        blinkAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textGuide.setVisibility(View.VISIBLE);
                blinkView.setBackgroundResource(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void applyChangeArrow(int arrow) {
        arrowView.setImageDrawable(getDrawable(arrow));
    }
}
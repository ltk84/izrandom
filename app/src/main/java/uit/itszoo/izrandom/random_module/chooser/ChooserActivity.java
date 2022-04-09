package uit.itszoo.izrandom.random_module.chooser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.chooser.model.ChooserRing;

public class ChooserActivity extends AppCompatActivity implements ChooserContract.View {

    ChooserContract.Presenter presenter;

    ViewGroup chooserLayout;
    ViewGroup chooserHolderLayout;

    ChooserRing ring1;
    ChooserRing ring2;
    ChooserRing ring3;
    ChooserRing ring4;
    int numberOfTheChosenOne = 1; // 1, 2, 3
    boolean[] theChosenOne;

    Handler handlerHoldEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        presenter = new ChooserPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        initView();
    }

    private void initView() {
        chooserLayout = findViewById(R.id.chooser_layout);
        chooserHolderLayout = findViewById(R.id.chooser_holder_layout);
        handlerHoldEvent = new Handler();

        theChosenOne = new boolean[4];

        chooserLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int countPointer = motionEvent.getPointerCount();
                ChooserActivity.this.handleActionRing1(motionEvent);
                if (countPointer > 1) {
                    ChooserActivity.this.handleActionRing2(motionEvent);
                    if (countPointer > 2) {
                        ChooserActivity.this.handleActionRing3(motionEvent);
                        if (countPointer > 3) {
                            ChooserActivity.this.handleActionRing4(motionEvent);
                        }
                    }
                }

                return true;
            }
        });

    }

    private ChooserRing initRing(MotionEvent motionEvent, int pointerIndex) {
        ImageView circle = new ImageView(getApplicationContext());
        circle.setLayoutParams (new ConstraintLayout.LayoutParams(300, 300));
        circle.setImageDrawable(getDrawable(R.drawable.chooser_shape));

        circle.setX(motionEvent.getX(pointerIndex) - (circle.getLayoutParams().width >> 1));
        circle.setY(motionEvent.getY(pointerIndex) - (circle.getLayoutParams().height >> 1));

        float x = motionEvent.getX(pointerIndex);
        float y = motionEvent.getY(pointerIndex);

        float dx = 0;
        float dy = 0;

        return new ChooserRing(pointerIndex, circle, x, y, dx, dy);

    }

    private void handleChooseRing(MotionEvent motionEvent, ChooserRing ring, boolean isChosen) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1f, 1.5f,
                1f, 1.5f,
                Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring.getCircle().getLayoutParams().width,
                Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring.getCircle().getLayoutParams().height);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(4);
        scaleAnimation.setFillAfter(isChosen);
        scaleAnimation.setDuration(500);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isChosen) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            1f, 0f,
                            1f, 0f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring.getCircle().getWidth(),
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring.getCircle().getHeight());
                    scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
                    scaleAnimation.setDuration(400);
                    ring.getCircle().startAnimation(scaleAnimation);
                    chooserHolderLayout.removeView(ring.getCircle());
                } else {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            1.5f, 2f,
                            1.5f, 2f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring.getCircle().getLayoutParams().width,
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring.getCircle().getLayoutParams().height);
                    scaleAnimation.setInterpolator(new OvershootInterpolator());
                    scaleAnimation.setFillAfter(true);
                    scaleAnimation.setDuration(500);
                    ring.getCircle().startAnimation(scaleAnimation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ring.getCircle().startAnimation(scaleAnimation);
    }

    private void randomTheChosenOne(int numberOfTheChosenOne) {
        if (numberOfTheChosenOne > theChosenOne.length) {
            System.out.println("Number of chosen ones is greater than the chosen ones list");
            return;
        }

        Arrays.fill(theChosenOne, Boolean.FALSE);

        Random random = new Random();
        for (int i = 0; i < numberOfTheChosenOne; i++) {
            int randomIndex = random.nextInt(numberOfTheChosenOne);
            theChosenOne[randomIndex] = true;
        }
    }

    private void handleActionRing1(MotionEvent motionEvent) {

        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            if (ring1 == null) {
                ring1 = initRing(motionEvent, 0);
            } else {
                ring1.getCircle().setX(motionEvent.getX(0) - (ring1.getCircle().getLayoutParams().width >> 1));
                ring1.getCircle().setY(motionEvent.getY(0) - (ring1.getCircle().getLayoutParams().height >> 1));

                ring1.setX(motionEvent.getX(0));
                ring1.setY(motionEvent.getY(0));
                ring1.setDx(0);
                ring1.setDy(0);
            }
            runOnUiThread(() -> {
                if (ring1.getCircle().getParent() == null) {
                    chooserHolderLayout.addView(ring1.getCircle());

                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            0f, 1f,
                            0f, 1f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring1.getCircle().getLayoutParams().width,
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring1.getCircle().getLayoutParams().height);
                    scaleAnimation.setInterpolator(new OvershootInterpolator());
                    scaleAnimation.setDuration(400);

                    ring1.getCircle().startAnimation(scaleAnimation);


                    if (motionEvent.getPointerCount() > numberOfTheChosenOne) {
                        randomTheChosenOne(numberOfTheChosenOne);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring1, theChosenOne[0]), 1000);
                    }
                }
            });
        }

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            runOnUiThread(() -> {
                if (ring1 != null) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            1f, 0f,
                            1f, 0f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring1.getCircle().getWidth(),
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring1.getCircle().getHeight());
                    scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
                    scaleAnimation.setDuration(400);
                    ring1.getCircle().startAnimation(scaleAnimation);
                    chooserHolderLayout.removeView(ring1.getCircle());
                }
            });
        }

        if (action == MotionEvent.ACTION_MOVE) {
            runOnUiThread(() -> {
                if (ring1 != null) {
                    ring1.setDx(motionEvent.getX(0) - ring1.getX());
                    ring1.setDy(motionEvent.getY(0) - ring1.getY());

                    ring1.getCircle().setX(ring1.getCircle().getX() + ring1.getDx());
                    ring1.getCircle().setY(ring1.getCircle().getY() + ring1.getDy());

                    ring1.setX(motionEvent.getX(0));
                    ring1.setY(motionEvent.getY(0));
                }
            });
        }
    }

    private void handleActionRing2(MotionEvent motionEvent) {

        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            if (ring2 == null) {
                ring2 = initRing(motionEvent, 1);
            } else {
                ring2.getCircle().setX(motionEvent.getX(1) - (ring2.getCircle().getLayoutParams().width >> 1));
                ring2.getCircle().setY(motionEvent.getY(1) - (ring2.getCircle().getLayoutParams().height >> 1));

                ring2.setX(motionEvent.getX(1));
                ring2.setY(motionEvent.getY(1));
                ring2.setDx(0);
                ring2.setDy(0);
            }
            runOnUiThread(() -> {
                if (ring2.getCircle().getParent() == null) {
                    chooserHolderLayout.addView(ring2.getCircle());

                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            0f, 1f,
                            0f, 1f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring2.getCircle().getLayoutParams().width,
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring2.getCircle().getLayoutParams().height);
                    scaleAnimation.setInterpolator(new OvershootInterpolator());
                    scaleAnimation.setDuration(400);

                    ring2.getCircle().startAnimation(scaleAnimation);


                    if (motionEvent.getPointerCount() > numberOfTheChosenOne) {
                        randomTheChosenOne(numberOfTheChosenOne);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring1,theChosenOne[0]), 1000);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring2,theChosenOne[1]), 1000);
                    }
                }
            });
        }

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            runOnUiThread(() -> {
                if (ring2 != null) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            1f, 0f,
                            1f, 0f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring2.getCircle().getWidth(),
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring2.getCircle().getHeight());
                    scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
                    scaleAnimation.setDuration(400);
                    ring2.getCircle().startAnimation(scaleAnimation);
                    chooserHolderLayout.removeView(ring2.getCircle());
                }
            });
        }

        if (action == MotionEvent.ACTION_MOVE) {
            runOnUiThread(() -> {
                if (ring2 != null) {
                    ring2.setDx(motionEvent.getX(1) - ring2.getX());
                    ring2.setDy(motionEvent.getY(1) - ring2.getY());

                    ring2.getCircle().setX(ring2.getCircle().getX() + ring2.getDx());
                    ring2.getCircle().setY(ring2.getCircle().getY() + ring2.getDy());

                    ring2.setX(motionEvent.getX(1));
                    ring2.setY(motionEvent.getY(1));
                }
            });
        }
    }

    private void handleActionRing3(MotionEvent motionEvent) {

        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            if (ring3 == null) {
                ring3 = initRing(motionEvent, 2);
            } else {
                ring3.getCircle().setX(motionEvent.getX(2) - (ring3.getCircle().getLayoutParams().width >> 1));
                ring3.getCircle().setY(motionEvent.getY(2) - (ring3.getCircle().getLayoutParams().height >> 1));

                ring3.setX(motionEvent.getX(2));
                ring3.setY(motionEvent.getY(2));
                ring3.setDx(0);
                ring3.setDy(0);
            }
            runOnUiThread(() -> {
                if (ring3.getCircle().getParent() == null) {
                    chooserHolderLayout.addView(ring3.getCircle());

                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            0f, 1f,
                            0f, 1f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring3.getCircle().getLayoutParams().width,
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring3.getCircle().getLayoutParams().height);
                    scaleAnimation.setInterpolator(new OvershootInterpolator());
                    scaleAnimation.setDuration(400);

                    ring3.getCircle().startAnimation(scaleAnimation);
                    if (motionEvent.getPointerCount() > numberOfTheChosenOne) {
                        randomTheChosenOne(numberOfTheChosenOne);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring1,theChosenOne[0]), 1000);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring2,theChosenOne[1]), 1000);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring3,theChosenOne[2]), 1000);
                    }
                }
            });
        }

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            runOnUiThread(() -> {
                if (ring3 != null) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            1f, 0f,
                            1f, 0f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring3.getCircle().getWidth(),
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring3.getCircle().getHeight());
                    scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
                    scaleAnimation.setDuration(400);
                    ring3.getCircle().startAnimation(scaleAnimation);
                    chooserHolderLayout.removeView(ring3.getCircle());
                }
            });
        }

        if (action == MotionEvent.ACTION_MOVE) {
            runOnUiThread(() -> {
                if (ring3 != null) {
                    ring3.setDx(motionEvent.getX(2) - ring3.getX());
                    ring3.setDy(motionEvent.getY(2) - ring3.getY());

                    ring3.getCircle().setX(ring3.getCircle().getX() + ring3.getDx());
                    ring3.getCircle().setY(ring3.getCircle().getY() + ring3.getDy());

                    ring3.setX(motionEvent.getX(2));
                    ring3.setY(motionEvent.getY(2));
                }
            });
        }
    }

    private void handleActionRing4(MotionEvent motionEvent) {

        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            if (ring4 == null) {
                ring4 = initRing(motionEvent, 3);
            } else {
                ring4.getCircle().setX(motionEvent.getX(3) - (ring4.getCircle().getLayoutParams().width >> 1));
                ring4.getCircle().setY(motionEvent.getY(3) - (ring4.getCircle().getLayoutParams().height >> 1));

                ring4.setX(motionEvent.getX(3));
                ring4.setY(motionEvent.getY(3));
                ring4.setDx(0);
                ring4.setDy(0);
            }
            runOnUiThread(() -> {
                if (ring4.getCircle().getParent() == null) {
                    chooserHolderLayout.addView(ring4.getCircle());

                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            0f, 1f,
                            0f, 1f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring4.getCircle().getLayoutParams().width,
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring4.getCircle().getLayoutParams().height);
                    scaleAnimation.setInterpolator(new OvershootInterpolator());
                    scaleAnimation.setDuration(400);

                    ring4.getCircle().startAnimation(scaleAnimation);

                    if (motionEvent.getPointerCount() > numberOfTheChosenOne) {
                        randomTheChosenOne(numberOfTheChosenOne);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring1,theChosenOne[0]), 1000);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring2,theChosenOne[1]), 1000);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring3,theChosenOne[2]), 1000);
                        handlerHoldEvent.postDelayed(() -> handleChooseRing(motionEvent, ring4,theChosenOne[3]), 1000);
                    }
                }
            });
        }

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP) {
            runOnUiThread(() -> {
                if (ring4 != null) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            1f, 0f,
                            1f, 0f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX()/ring4.getCircle().getWidth(),
                            Animation.RELATIVE_TO_SELF, motionEvent.getY()/ring4.getCircle().getHeight());
                    scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
                    scaleAnimation.setDuration(400);
                    ring4.getCircle().startAnimation(scaleAnimation);
                    chooserHolderLayout.removeView(ring4.getCircle());
                }
            });
        }

        if (action == MotionEvent.ACTION_MOVE) {
            runOnUiThread(() -> {
                if (ring4 != null) {
                    ring4.setDx(motionEvent.getX(3) - ring4.getX());
                    ring4.setDy(motionEvent.getY(3) - ring4.getY());

                    ring4.getCircle().setX(ring4.getCircle().getX() + ring4.getDx());
                    ring4.getCircle().setY(ring4.getCircle().getY() + ring4.getDy());

                    ring4.setX(motionEvent.getX(3));
                    ring4.setY(motionEvent.getY(3));
                }
            });
        }
    }

    @Override
    public void setPresenter(ChooserContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
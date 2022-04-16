package uit.itszoo.izrandom.random_module.chooser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.chooser.chooser_custom.ChooserCustomActivity;
import uit.itszoo.izrandom.random_module.chooser.model.ChooserRing;

public class ChooserActivity extends AppCompatActivity implements ChooserContract.View {

    ChooserContract.Presenter presenter;

    ImageButton toCustomScreenButton;
    ImageButton backButton;

    ViewGroup chooserLayout;
    ViewGroup chooserHolderLayout;
    boolean isAnimation = false;
    boolean isChoosing = false;
    boolean isFinished = false;

    ChooserRing ring1;
    ChooserRing ring2;
    ChooserRing ring3;
    ChooserRing ring4;

    List<ChooserRing> ringList = new ArrayList<>();


    int numberOfTheChosenOne = 1; // 1, 2, 3
    boolean[] theChosenOne;

    Handler handlerHoldEvent;
    Handler handlerCancelEvent;
    Handler handlerEvent;
    Handler handlerCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        presenter = new ChooserPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        initView();
        setListenerForView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerCheck.removeCallbacks(null);
    }

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                    }
                }
            });

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
                Intent intentToCustom = new Intent(getApplicationContext(), ChooserCustomActivity.class);
                intentLauncher.launch(intentToCustom);
            }
        });

    }

    void initRings(MotionEvent motionEvent, int countPointer) {
        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            if (isFinished) {
                return;
            }

            int pointerIndex = motionEvent.getActionIndex();
            int pointerId = motionEvent.getPointerId(pointerIndex);
//            System.out.println("init index: " + pointerIndex + " id: " + pointerId);

            List<ChooserRing> result =
                    ringList.stream().filter(item -> item.getId() == pointerId).collect(Collectors.toList());
            if (result.size() == 0) {
                ChooserRing newRing = initRing(motionEvent, pointerIndex);
                ringList.add(newRing);
                if (newRing.getCircle().getParent() == null) {
//                    if (!isAnimation) {
                    chooserHolderLayout.addView(newRing.getCircle());
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            0f, 1f,
                            0f, 1f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX(pointerIndex) / newRing.getCircle().getLayoutParams().width,
                            Animation.RELATIVE_TO_SELF, motionEvent.getY(pointerIndex) / newRing.getCircle().getLayoutParams().height);
                    scaleAnimation.setInterpolator(new OvershootInterpolator());
                    scaleAnimation.setDuration(400);
                    newRing.getCircle().startAnimation(scaleAnimation);
//                    }
                }
            }
        }
    }

    void moveRings(MotionEvent motionEvent) {
        for (int i = 0; i < ringList.size(); i++) {
            int pointerIndex = motionEvent.findPointerIndex(ringList.get(i).getId());
            handleMoveRing(motionEvent, ringList.get(i), pointerIndex);
        }
    }

    private void initView() {
        backButton = findViewById(R.id.bb_chooser);
        toCustomScreenButton = findViewById(R.id.custom_button);
        chooserLayout = findViewById(R.id.chooser_layout);
        chooserHolderLayout = findViewById(R.id.chooser_holder_layout);
        handlerHoldEvent = new Handler();
        handlerCancelEvent = new Handler();
        handlerEvent = new Handler();
        theChosenOne = new boolean[4];
        handlerCheck = new Handler(Looper.getMainLooper());

        chooserLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


//                if (isChoosing) {
//                    isChoosing = false;
//                    if (ring1 != null) ring1.getCircle().clearAnimation();
//                    if (ring2 != null) ring2.getCircle().clearAnimation();
//                    if (ring3 != null) ring3.getCircle().clearAnimation();
//                    if (ring4 != null) ring4.getCircle().clearAnimation();
//                    return true;
//                }


                int countPointer = motionEvent.getPointerCount();
//                if (isFinished && countPointer > 1 && (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN || motionEvent.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN)) {
//                    return true;
//                }

//                ring1 = handleInitRing(motionEvent, ring1, 0);
//                if (ring1 != null) {
//                    int ringID1 = motionEvent.findPointerIndex(ring1.getIndex());
//                    if (ringID1 != -1) {
//                        ring1 = handleMoveRing(motionEvent, ring1, ringID1);
//                    }
//                }
//
//                if (countPointer > 1) {
//                    ring2 = handleInitRing(motionEvent, ring2, 1);
//                    if (ring2 != null) {
//                        int ringID2 = motionEvent.findPointerIndex(ring2.getIndex());
//                        if (ringID2 != -1) {
//                            ring2 = handleMoveRing(motionEvent, ring2, ringID2);
//                        }
//                    }
//
//                    if (countPointer > 2) {
//                        ring3 = handleInitRing(motionEvent, ring3, 2);
//                        if (ring3 != null) {
//                            int ringID3 = motionEvent.findPointerIndex(ring3.getIndex());
//                            if (ringID3 != -1) {
//                                ring3 = handleMoveRing(motionEvent, ring3, ringID3);
//                            }
//                        }
//                        if (countPointer > 3) {
//                            ring4 = handleInitRing(motionEvent, ring4, 3);
//                            if (ring4 != null) {
//                                int ringID4 = motionEvent.findPointerIndex(ring4.getIndex());
//                                if (ringID4 != -1) {
//                                    ring4 = handleMoveRing(motionEvent, ring4, ringID4);
//                                }
//                            }
//                        }
//                    }
//                }

                initRings(motionEvent, countPointer);
                moveRings(motionEvent);
                chooseRing(motionEvent);
                cancelChooseRings(motionEvent, countPointer);
                cancelRings(motionEvent);
                return true;
            }
        });

    }


    void cancelChooseRings(MotionEvent motionEvent, int countPointer) {
        int action = motionEvent.getActionMasked();
        if (action != MotionEvent.ACTION_MOVE) {
            System.out.println("isChoosing: " + isChoosing + " actiton: " + MotionEvent.actionToString(action));
        }
        if (isChoosing) {
            if (action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_POINTER_UP) {

                for (int i = 0; i < countPointer; i++) {
                    handleCancelChoosing(motionEvent, ringList.get(i));
                }

//                if (ringList.size() - 1 == 1) {
//                    return;
//                }
                if (numberOfTheChosenOne < ringList.size() && !isChoosing && !isFinished) {
                    int triggerPointerIndex = motionEvent.getActionIndex();
                    handlerHoldEvent.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            randomTheChosenOne(numberOfTheChosenOne, countPointer - 1);
                            for (int i = 0; i < ringList.size(); i++) {
                                ChooserRing ring = ringList.get(i);
                                if (motionEvent.findPointerIndex(ring.getId()) != triggerPointerIndex) {
                                    handleChooseRing(motionEvent, ring, motionEvent.findPointerIndex(ring.getId()), theChosenOne[i]);
                                }

                            }
                        }
                    }, 2000);
                }
            }

        }
    }

    private void chooseRing(MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        int countPointer = motionEvent.getPointerCount();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            if (numberOfTheChosenOne < countPointer && !isChoosing && !isFinished) {
                handlerHoldEvent.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        randomTheChosenOne(numberOfTheChosenOne, countPointer);
                        for (int i = 0; i < ringList.size(); i++) {
                            ChooserRing ring = ringList.get(i);
                            handleChooseRing(motionEvent, ring, motionEvent.findPointerIndex(ring.getId()), theChosenOne[i]);

                        }
                    }
                }, 2000);
            }
        }
    }

//    private ChooserRing handleInitRing(MotionEvent motionEvent, ChooserRing ring, int pointerIndex) {
//        int action = motionEvent.getActionMasked();
//        int index = pointerIndex;
//
//        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
//            if (ring == null) {
//                ring = initRing(motionEvent, pointerIndex);
//            } else {
//                index = motionEvent.findPointerIndex(ring.getId());
//
//                if (index != -1) {
//                    ring.getCircle().setX(motionEvent.getX(index) - (ring.getCircle().getLayoutParams().width >> 1));
//                    ring.getCircle().setY(motionEvent.getY(index) - (ring.getCircle().getLayoutParams().height >> 1));
//
//                    ring.setX(motionEvent.getX(index));
//                    ring.setY(motionEvent.getY(index));
//                    ring.setDx(0);
//                    ring.setDy(0);
//                }
//            }
//            if (ring.getCircle().getParent() == null && index != -1) {
//                if (!isAnimation) {
//                    chooserHolderLayout.addView(ring.getCircle());
//                    ScaleAnimation scaleAnimation = new ScaleAnimation(
//                            0f, 1f,
//                            0f, 1f,
//                            Animation.RELATIVE_TO_SELF, motionEvent.getX(index) / ring.getCircle().getLayoutParams().width,
//                            Animation.RELATIVE_TO_SELF, motionEvent.getY(index) / ring.getCircle().getLayoutParams().height);
//                    scaleAnimation.setInterpolator(new OvershootInterpolator());
//                    scaleAnimation.setDuration(400);
//                    ring.getCircle().startAnimation(scaleAnimation);
//                }
//            }
//        }
//        return ring;
//    }

    private ChooserRing handleMoveRing(MotionEvent motionEvent, ChooserRing ring, int pointerIndex) {
        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_MOVE) {

            runOnUiThread(() -> {
                if (ring != null) {
                    ring.setDx(motionEvent.getX(pointerIndex) - ring.getX());
                    ring.setDy(motionEvent.getY(pointerIndex) - ring.getY());

                    ring.getCircle().setX(ring.getCircle().getX() + ring.getDx());
                    ring.getCircle().setY(ring.getCircle().getY() + ring.getDy());

                    ring.setX(motionEvent.getX(pointerIndex));
                    ring.setY(motionEvent.getY(pointerIndex));
                }
            });

        }
        return ring;
    }

    private void handleCancelChoosing(MotionEvent motionEvent, ChooserRing ring) {

        if (ring != null) {
            int pointerIndex = motionEvent.findPointerIndex(ring.getId());
            if (pointerIndex == -1) {
                return;
            }
            ScaleAnimation scaleAnimation = new ScaleAnimation(
                    1.5f, 1f,
                    1.5f, 1f,
                    Animation.RELATIVE_TO_SELF, motionEvent.getX(pointerIndex) / ring.getCircle().getLayoutParams().width,
                    Animation.RELATIVE_TO_SELF, motionEvent.getY(pointerIndex) / ring.getCircle().getLayoutParams().height);
            scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
            scaleAnimation.setDuration(400);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isChoosing = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ring.getCircle().startAnimation(scaleAnimation);
        }

        isChoosing = false;
        System.out.println("done cancel choosing");
    }

    private void cancelRings(MotionEvent motionEvent) {

        int action = motionEvent.getActionMasked();
        ChooserRing ring = null;
        int removedPointerIndex = -1;

        if (action == MotionEvent.ACTION_UP) {
            removedPointerIndex = 0;
            isChoosing = false;
            isFinished = false;
        } else if (action == MotionEvent.ACTION_POINTER_UP) {
            removedPointerIndex = motionEvent.getActionIndex();
//            isChoosing = false;
        }

        if (removedPointerIndex < 0) {
            return;
        }

        int removePointerID = motionEvent.getPointerId(removedPointerIndex);
//        System.out.println("Leave index: " + removedPointerIndex + " id: " + removePointerID);
        List<ChooserRing> result = ringList.stream().filter(item -> item.getId() == removePointerID).collect(Collectors.toList());
        if (result.size() != 0) {
            ring = result.get(0);
        }
//        System.out.println("find ring: " + ring);
        if (ring != null) {
            ScaleAnimation scaleAnimation = new ScaleAnimation(
                    1f, 0f,
                    1f, 0f,
                    Animation.RELATIVE_TO_SELF, motionEvent.getX(removedPointerIndex) / ring.getCircle().getWidth(),
                    Animation.RELATIVE_TO_SELF, motionEvent.getY(removedPointerIndex) / ring.getCircle().getHeight());
            scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
            scaleAnimation.setDuration(400);
            ChooserRing finalRing = ring;
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isAnimation = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    chooserHolderLayout.removeView(finalRing.getCircle());
                    isAnimation = false;
//                    if (isChoosing) {
//                        handlerEvent.removeCallbacksAndMessages(null);
//                        handleCancelChoosing(motionEvent, ring1);
//                        handleCancelChoosing(motionEvent, ring2);
//                        handleCancelChoosing(motionEvent, ring3);
//                        handleCancelChoosing(motionEvent, ring4);
//                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            ring.getCircle().startAnimation(scaleAnimation);
            chooserHolderLayout.removeView(ring.getCircle());
            ringList.remove(ringList.indexOf(finalRing));

        }
    }

    private ChooserRing initRing(MotionEvent motionEvent, int pointerIndex) {
        ImageView circle = new ImageView(getApplicationContext());
        circle.setId(View.generateViewId());
        circle.setLayoutParams(new ConstraintLayout.LayoutParams(300, 300));
        circle.setImageDrawable(getDrawable(R.drawable.chooser_shape));

        circle.setX(motionEvent.getX(pointerIndex) - (circle.getLayoutParams().width >> 1));
        circle.setY(motionEvent.getY(pointerIndex) - (circle.getLayoutParams().height >> 1));

        float x = motionEvent.getX(pointerIndex);
        float y = motionEvent.getY(pointerIndex);

        float dx = 0;
        float dy = 0;

        return new ChooserRing(motionEvent.getPointerId(pointerIndex), circle, x, y, dx, dy);
    }

    private void handleChooseRing(MotionEvent motionEvent, ChooserRing ring, int pointerIndex, boolean isChosen) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1f, 1.2f,
                1f, 1.2f,
                Animation.RELATIVE_TO_SELF, motionEvent.getX(pointerIndex) / ring.getCircle().getLayoutParams().width,
                Animation.RELATIVE_TO_SELF, motionEvent.getY(pointerIndex) / ring.getCircle().getLayoutParams().height);
        scaleAnimation.setInterpolator(new OvershootInterpolator());
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(4);
        scaleAnimation.setDuration(500);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isChoosing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isChosen) {
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            1f, 0f,
                            1f, 0f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX(pointerIndex) / ring.getCircle().getLayoutParams().width,
                            Animation.RELATIVE_TO_SELF, motionEvent.getY(pointerIndex) / ring.getCircle().getLayoutParams().height);
                    scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
                    scaleAnimation.setDuration(400);
                    ring.getCircle().startAnimation(scaleAnimation);
                    chooserHolderLayout.removeView(ring.getCircle());
                } else {
                    ImageView circle = ring.getCircle();
                    int reqWidth = (int) (circle.getWidth() * 1.5);
                    int reqHeight = (int) (circle.getHeight() * 1.5);

                    circle.setLayoutParams(new RelativeLayout.LayoutParams(reqWidth, reqHeight));

                    circle.setX(motionEvent.getX(pointerIndex) - (reqWidth >> 1));
                    circle.setY(motionEvent.getY(pointerIndex) - (reqHeight >> 1));
                    ring.setX(motionEvent.getX(pointerIndex));
                    ring.setY(motionEvent.getY(pointerIndex));

                    ring.setDx(0);
                    ring.setDy(0);
                }
                isChoosing = false;
                isFinished = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ring.getCircle().startAnimation(scaleAnimation);
    }

    private void randomTheChosenOne(int numberOfTheChosenOne, int pointerCount) {
        if (numberOfTheChosenOne > theChosenOne.length) {
            System.out.println("Number of chosen ones is greater than the chosen ones list");
            return;
        }

        Arrays.fill(theChosenOne, Boolean.FALSE);

        Random random = new Random();
        for (int i = 0; i < numberOfTheChosenOne; i++) {
            int randomIndex = random.nextInt(pointerCount);
            theChosenOne[randomIndex] = true;
        }
    }

    @Override
    public void setPresenter(ChooserContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
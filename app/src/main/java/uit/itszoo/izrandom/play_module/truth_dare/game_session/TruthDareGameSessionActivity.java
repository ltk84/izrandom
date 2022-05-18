package uit.itszoo.izrandom.play_module.truth_dare.game_session;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.chooser.model.ChooserRing;

public class TruthDareGameSessionActivity extends AppCompatActivity {
    ImageButton backButton;
    ViewGroup chooserLayout;
    ViewGroup chooserHolderLayout;
    TextView guideTextView;


    boolean isChoosing = false;
    boolean isFinished = false;

    List<ChooserRing> ringList = new ArrayList<>();
    int numberOfTheChosenOne = 1; // 1, 2, 3

    boolean[] theChosenOne;

    Handler handlerHoldEvent;
    Handler handlerCancelEvent;
    Handler handlerEvent;

    ArrayList<String> cards;
    boolean cardTurn = false;
    boolean doneChooser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truth_dare_game_session);

        initView();
        setListenerForView();

        cards = getIntent().getStringArrayListExtra("cards");
    }


    private void initView() {
        backButton = findViewById(R.id.back_button);
        chooserLayout = findViewById(R.id.truth_dare_game_session_layout);
        chooserHolderLayout = findViewById(R.id.chooser_holder_layout);
        handlerHoldEvent = new Handler();
        handlerCancelEvent = new Handler();
        handlerEvent = new Handler();
        theChosenOne = new boolean[4];

        guideTextView = findViewById(R.id.txt_guide);

        chooserLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!cardTurn) {
                    int countPointer = motionEvent.getPointerCount();

                    actionWithComponents(motionEvent);

                    initRings(motionEvent);
                    moveRings(motionEvent);
                    cancelChooseRings(motionEvent, countPointer);
                    cancelRings(motionEvent);
                    chooseRing(motionEvent);
                }
                return true;
            }
        });
    }
    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void handleCardTurn() {

        // Xử lý hiện thị bộ thẻ bài ở đây. Bộ thẻ bài ở đây là ArrayList<String> có tên biến là cards (đã được xáo trộn và truyền vào activity này rồi).

        // Các thẻ bài ở đây có thể ấn để lật.

        // Tạo thêm 1 Button hiện cùng với bộ thẻ bài. -> Ấn vào nút đó thì sẽ ẩn bộ thẻ bài và ẩn cái button này lun, và sau đó chạy hàm backToChooser.
        backToChooser();
    }

    public void backToChooser() {
        cardTurn = false;
        doneChooser = false;
        guideTextView.setVisibility(View.VISIBLE);
    }

    private void actionWithComponents(MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            guideTextView.setVisibility(View.INVISIBLE);
        } else if (action == MotionEvent.ACTION_UP) {
            guideTextView.setVisibility(View.VISIBLE);
        }
    }

    void initRings(MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            if (isFinished) {
                return;
            }

            int pointerIndex = motionEvent.getActionIndex();
            int pointerId = motionEvent.getPointerId(pointerIndex);

            List<ChooserRing> result =
                    ringList.stream().filter(item -> item.getId() == pointerId).collect(Collectors.toList());
            if (result.size() == 0) {
                ChooserRing newRing = initRing(motionEvent, pointerIndex);
                ringList.add(newRing);
                if (newRing.getCircle().getParent() == null) {

                    chooserHolderLayout.addView(newRing.getCircle());
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            0f, 1f,
                            0f, 1f,
                            Animation.RELATIVE_TO_SELF, motionEvent.getX(pointerIndex) / newRing.getCircle().getLayoutParams().width,
                            Animation.RELATIVE_TO_SELF, motionEvent.getY(pointerIndex) / newRing.getCircle().getLayoutParams().height);
                    scaleAnimation.setInterpolator(new OvershootInterpolator());
                    scaleAnimation.setDuration(400);
                    newRing.getCircle().startAnimation(scaleAnimation);

                }
            }
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

    void moveRings(MotionEvent motionEvent) {
        for (int i = 0; i < ringList.size(); i++) {
            int pointerIndex = motionEvent.findPointerIndex(ringList.get(i).getId());
            handleMoveRing(motionEvent, ringList.get(i), pointerIndex);
        }
    }

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

    void cancelChooseRings(MotionEvent motionEvent, int countPointer) {
        int action = motionEvent.getActionMasked();
        if (isChoosing) {
            if (action == MotionEvent.ACTION_POINTER_DOWN || action == MotionEvent.ACTION_POINTER_UP) {
                handlerHoldEvent.removeCallbacksAndMessages(null);
                handlerCancelEvent.removeCallbacksAndMessages(null);
                for (int i = 0; i < countPointer; i++) {
                    handleCancelChoosing(motionEvent, ringList.get(i));
                }
            }
        }
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
        }

        if (removedPointerIndex < 0) {
            return;
        }

        handlerHoldEvent.removeCallbacksAndMessages(null);

        int removePointerID = motionEvent.getPointerId(removedPointerIndex);
        List<ChooserRing> result = ringList.stream().filter(item -> item.getId() == removePointerID).collect(Collectors.toList());
        if (result.size() != 0) {
            ring = result.get(0);
        }

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

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    chooserHolderLayout.removeView(finalRing.getCircle());
                    if (doneChooser) {
                        cardTurn = true;
                        guideTextView.setVisibility(View.INVISIBLE);
                        handleCardTurn();
                    }
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

    private void chooseRing(MotionEvent motionEvent) {
        int action = motionEvent.getActionMasked();
        int countPointer = motionEvent.getPointerCount();

        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN) {
            if (numberOfTheChosenOne < countPointer && !isChoosing && !isFinished) {
                handlerHoldEvent.removeCallbacksAndMessages(null);
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
        } else if (action == MotionEvent.ACTION_POINTER_UP) {
            int actualCountPointer = countPointer - 1;
            int removePointerIndex = motionEvent.getActionIndex();
            int removePointerID = motionEvent.getPointerId(removePointerIndex);

            if (numberOfTheChosenOne < actualCountPointer && !isChoosing && !isFinished) {
                handlerHoldEvent.removeCallbacksAndMessages(null);
                handlerHoldEvent.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        randomTheChosenOne(numberOfTheChosenOne, actualCountPointer);
                        for (int i = 0; i < ringList.size(); i++) {
                            ChooserRing ring = ringList.get(i);
                            if (ring.getId() == removePointerID) {
                                continue;
                            }
                            handleChooseRing(motionEvent, ring, motionEvent.findPointerIndex(ring.getId()), theChosenOne[i]);
                        }
                    }
                }, 2000);
            }
        }
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

                    circle.setImageDrawable(getDrawable(R.drawable.chosen_chooser_shape));
                    circle.setX(motionEvent.getX(pointerIndex) - (reqWidth >> 1));
                    circle.setY(motionEvent.getY(pointerIndex) - (reqHeight >> 1));
                    ring.setX(motionEvent.getX(pointerIndex));
                    ring.setY(motionEvent.getY(pointerIndex));

                    ring.setDx(0);
                    ring.setDy(0);
                }
                isChoosing = false;
                isFinished = true;
                doneChooser = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ring.getCircle().startAnimation(scaleAnimation);
    }

    private void randomTheChosenOne(int numberOfTheChosenOne, int pointerCount) {
        theChosenOne = new boolean[ringList.size()];
        if (numberOfTheChosenOne > theChosenOne.length) {
            System.out.println("Number of chosen ones is greater than the chosen ones list");
            return;
        }

        Arrays.fill(theChosenOne, Boolean.FALSE);

        Random random = new Random();
        int countRandomTime = 0;
        ArrayList randomRes = new ArrayList();
        while (countRandomTime != numberOfTheChosenOne) {
            int randomIndex = random.nextInt(pointerCount);
            if (!randomRes.contains(randomIndex)) {
                randomRes.add(randomIndex);
                countRandomTime++;
                theChosenOne[randomIndex] = true;
            }
        }

    }

}
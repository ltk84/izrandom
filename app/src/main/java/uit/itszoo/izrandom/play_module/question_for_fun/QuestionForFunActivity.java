package uit.itszoo.izrandom.play_module.question_for_fun;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wajahatkarim3.easyflipview.EasyFlipView;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.Duration;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;

public class QuestionForFunActivity extends AppCompatActivity implements QuestionForFunContract.View {
    private boolean isCardFlipping = false;
    private int indexCard = 0;

    List<String> listCardContents = new ArrayList<>();
    CardStackAdapter cardStackAdapter;
    CardStackLayoutManager cardStackLayoutManager;

    QuestionForFunContract.Presenter presenter;
    ImageButton backButton;

    CardStackView cardStackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_for_fun);

        presenter = new QuestionForFunPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        //TODO get listCardContents from database
        for (int i = 0; i < 5; i++) {
            listCardContents.add("What did you want to be when you grew up? " + String.valueOf(i));
        }

        initView();
        setListenerForView();
        setUpCardStackView();

    }

    private void initView() {
        backButton = findViewById(R.id.bb_question_for_fun);
        cardStackView = findViewById(R.id.question_for_fun_cardStackView);
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void setUpCardStackView() {
        cardStackLayoutManager = new CardStackLayoutManager(QuestionForFunActivity.this);
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.Automatic);
        cardStackLayoutManager.setDirections(Direction.HORIZONTAL);
        cardStackLayoutManager.setSwipeThreshold(0.1f);

        SwipeAnimationSetting swipeAnimationSetting = new SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(1000)
                .setInterpolator(new android.view.animation.AccelerateInterpolator())
                .build();
        cardStackLayoutManager.setSwipeAnimationSetting(swipeAnimationSetting);

        RewindAnimationSetting rewindAnimationSetting = new RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(new android.view.animation.DecelerateInterpolator())
                .build();
        cardStackLayoutManager.setRewindAnimationSetting(rewindAnimationSetting);

        cardStackView.setLayoutManager(cardStackLayoutManager);

        cardStackAdapter = new CardStackAdapter(QuestionForFunActivity.this, listCardContents.subList(0, 2), false);
        cardStackView.setAdapter(cardStackAdapter);

        cardStackView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                isCardFlipping = false;
            }
        });

        cardStackView.setOnTouchListener(new OnSwipeTouchListener(QuestionForFunActivity.this) {
            @Override
            public void onClick() {
                if (indexCard >= 0 && indexCard < listCardContents.size()) {
                    if (!isCardFlipping) {
                        EasyFlipView easyFlipView = (EasyFlipView) cardStackLayoutManager.getTopView().findViewById(R.id.card_stack_item);
                        if (easyFlipView.getCurrentFlipState().toString().equals("FRONT_SIDE")) {
                            isCardFlipping = true;
                            easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
                                @Override
                                public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                                    isCardFlipping = false;
                                }
                            });
                            easyFlipView.flipTheView();
                        }
                        else if (easyFlipView.getCurrentFlipState().toString().equals("BACK_SIDE")) {
                            swipeCardStackView();
                        }
                    }
                    else {
                        Toast.makeText(QuestionForFunActivity.this, "Thẻ bài đang lật, vui lòng thử lại sau vài giây", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onSwipeTop() {

            }

            @Override
            public void onSwipeRight() {
                if (indexCard >= 0 && indexCard < listCardContents.size()) {
                    if (!isCardFlipping) {
                        EasyFlipView easyFlipView = (EasyFlipView) cardStackLayoutManager.getTopView().findViewById(R.id.card_stack_item);
                        if (easyFlipView.getCurrentFlipState().toString().equals("FRONT_SIDE")) {
                            if (indexCard > 0) {
                                rewindCardStackView();
                            }
                        }
                        else if (easyFlipView.getCurrentFlipState().toString().equals("BACK_SIDE")) {
                            isCardFlipping = true;
                            easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
                                @Override
                                public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                                    isCardFlipping = false;
                                }
                            });
                            easyFlipView.flipTheView();
                        }
                    }
                    else {
                        Toast.makeText(QuestionForFunActivity.this, "Thẻ bài đang lật, vui lòng thử lại sau vài giây", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (indexCard > 0 && indexCard >= listCardContents.size()) {
                    rewindCardStackView();
                }
            }

            @Override
            public void onSwipeLeft() {
                if (indexCard >= 0 && indexCard < listCardContents.size()) {
                    if (!isCardFlipping) {
                        EasyFlipView easyFlipView = (EasyFlipView) cardStackLayoutManager.getTopView().findViewById(R.id.card_stack_item);
                        if (easyFlipView.getCurrentFlipState().toString().equals("FRONT_SIDE")) {
                            isCardFlipping = true;
                            easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
                                @Override
                                public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                                    isCardFlipping = false;
                                }
                            });
                            easyFlipView.flipTheView();
                        }
                        else if (easyFlipView.getCurrentFlipState().toString().equals("BACK_SIDE")) {
                            swipeCardStackView();
                        }
                    }
                    else {
                        Toast.makeText(QuestionForFunActivity.this, "Thẻ bài đang lật, vui lòng thử lại sau vài giây", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onSwipeBottom() {

            }
        });
    }

    @Override
    public void setPresenter(QuestionForFunContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void swipeCardStackView() {
        if (!isCardFlipping) {
            if (indexCard < listCardContents.size() - 1) {
                isCardFlipping = true;
                cardStackView.swipe();
                indexCard++;

                new java.util.Timer().schedule(

                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here, and if you have to refresh UI put this code:
                                runOnUiThread(new   Runnable() {
                                    public void run() {
                                        //your code
                                        resetAdapter("Swipe");

                                    }
                                });
                            }
                        },
                        1000
                );
            }
            else if (indexCard == listCardContents.size() - 1) {
                cardStackView.swipe();
                indexCard++;
            }
        }
        else {
            Toast.makeText(QuestionForFunActivity.this, "Thẻ bài đang lật, vui lòng thử lại sau vài giây", Toast.LENGTH_SHORT).show();
        }
    }

    private void rewindCardStackView() {
        if (!isCardFlipping) {
            if (indexCard > 0 && indexCard < listCardContents.size()) {
                isCardFlipping = true;
                cardStackView.rewind();
                indexCard--;

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here, and if you have to refresh UI put this code:
                                runOnUiThread(new   Runnable() {
                                    public void run() {
                                        //your code
                                        resetAdapter("Rewind");
                                    }
                                });
                            }
                        },
                        800
                );
            }
            else if (indexCard >= listCardContents.size()) {
                cardStackView.rewind();
                indexCard--;
            }
        }
        else {
            Toast.makeText(QuestionForFunActivity.this, "Thẻ bài đang lật, vui lòng thử lại sau vài giây", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetAdapter(String isSwipeOrRewind) {
        if (indexCard > 0 && indexCard < listCardContents.size() - 1) {
            cardStackAdapter = new CardStackAdapter(QuestionForFunActivity.this, listCardContents.subList(indexCard - 1, indexCard + 2), !isSwipeOrRewind.equals("Swipe"));
            cardStackView.setAdapter(cardStackAdapter);
            cardStackView.scrollToPosition(1);
        }
        else if (indexCard == 0) {
            cardStackAdapter = new CardStackAdapter(QuestionForFunActivity.this, listCardContents.subList(0, 2), !isSwipeOrRewind.equals("Swipe"));
            cardStackView.setAdapter(cardStackAdapter);
        }
        else if (indexCard == listCardContents.size() - 1) {
            cardStackAdapter = new CardStackAdapter(QuestionForFunActivity.this, listCardContents.subList(indexCard - 1, indexCard + 1), !isSwipeOrRewind.equals("Swipe"));
            cardStackView.setAdapter(cardStackAdapter);
            cardStackView.scrollToPosition(1);
        }
    }



}

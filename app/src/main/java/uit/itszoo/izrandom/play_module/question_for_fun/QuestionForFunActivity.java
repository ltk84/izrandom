package uit.itszoo.izrandom.play_module.question_for_fun;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wajahatkarim3.easyflipview.EasyFlipView;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;

public class QuestionForFunActivity extends AppCompatActivity implements QuestionForFunContract.View, CardStackListener {
    private static boolean isCardFlipped = false;
    private static boolean isCardFlipping = false;
    private static int indexCard = 0;

    List<String> listCardContents = new ArrayList<>();

    QuestionForFunContract.Presenter presenter;
    ImageButton backButton;

    Button swipeButton;

    CardStackView cardStackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_for_fun);

        presenter = new QuestionForFunPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        //TODO get listCardContents from database
        for (int i = 0; i < 20; i++) {
            listCardContents.add("What did you want to be when you grew up? " + String.valueOf(i));
        }

        initView();
        setListenerForView();
        setUpCardStackView();

    }

    private void initView() {
        backButton = findViewById(R.id.bb_question_for_fun);
        cardStackView = (CardStackView) findViewById(R.id.question_for_fun_cardStackView);

        swipeButton = findViewById(R.id.btn_swipe_question_for_fun);
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        swipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("QuestionForFunActivity", "swipeButton onClick called");
                cardStackView.swipe();
            }
        });

    }

    private void setUpCardStackView() {
        CardStackLayoutManager cardStackLayoutManager = new CardStackLayoutManager(QuestionForFunActivity.this);
        cardStackLayoutManager.setSwipeableMethod(SwipeableMethod.Automatic);
        cardStackLayoutManager.setDirections(Direction.HORIZONTAL);

        SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(1000)
                .setInterpolator(new android.view.animation.AccelerateInterpolator())
                .build();
        cardStackLayoutManager.setSwipeAnimationSetting(setting);

        cardStackView.setLayoutManager(cardStackLayoutManager);

        CardStackAdapter cardStackAdapter = new CardStackAdapter(QuestionForFunActivity.this, listCardContents);
        cardStackView.setAdapter(cardStackAdapter);



//        cardStackView.setOnTouchListener(new OnSwipeTouchListener(QuestionForFunActivity.this) {
//            @Override
//            public void onClick() {
////                Toast.makeText(QuestionForFunActivity.this, "click", Toast.LENGTH_SHORT).show();
////                Log.i("QuestionForFunActivity", "cardStackView onTouchListener onClick called" + String.valueOf(indexCard));
////                if (!isCardFlipping) {
////                    if(!isCardFlipped) {
////                        if (indexCard >= 0 && indexCard < listCardContents.size()) {
////                            Log.i("QuestionForFunActivity", "indexCard value: " + String.valueOf(indexCard));
////                            isCardFlipping = true;
////                            EasyFlipView easyFlipView = (EasyFlipView) cardStackLayoutManager.getTopView().findViewById(cardStackAdapter.listIdEasyFlipView.get(indexCard));
//////                            EasyFlipView easyFlipView = (EasyFlipView) cardStackView.getChildViewHolder(cardStackView.getChildAt(indexCard)).itemView.findViewById(cardStackAdapter.listIdEasyFlipView.get(indexCard));
////                            easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
////                                @Override
////                                public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
////                                    Log.i("QuestionForFunActivity", "easyFlipView onViewFlipCompleted called");
////                                    isCardFlipping = false;
////                                }
////                            });
////
////                            easyFlipView.flipTheView();
////                            isCardFlipped = true;
////                        }
////                        else {
////                            Log.i("QuestionForFunActivity", "indexCard is out of bound");
////                        }
////                    }
////                    else {
////                        cardStackView.swipe();
////                        isCardFlipped = false;
////                        indexCard++;
////
////
////
////                    }
////                }
////                else {
////                    Log.i("QuestionForFunActivity", "Card is flipping");
////                }
//
//
//            }
//
//            @Override
//            public void onSwipeTop() {
//                Toast.makeText(QuestionForFunActivity.this, "top", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSwipeRight() {
//                Toast.makeText(QuestionForFunActivity.this, "right", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSwipeLeft() {
//                Toast.makeText(QuestionForFunActivity.this, "left", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSwipeBottom() {
//                Toast.makeText(QuestionForFunActivity.this, "bottom", Toast.LENGTH_SHORT).show();
//            }
//
//        });

    }

    @Override
    public void setPresenter(QuestionForFunContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCardDragging(Direction direction, float ratio) {

    }

    @Override
    public void onCardSwiped(Direction direction) {
        Log.i("QuestionForFunActivity", "onCardSwiped called");
    }

    @Override
    public void onCardRewound() {

    }

    @Override
    public void onCardCanceled() {

    }

    @Override
    public void onCardAppeared(View view, int position) {

    }

    @Override
    public void onCardDisappeared(View view, int position) {

    }
}

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
import java.util.Collections;
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

        initListCardContents();
        initView();
        setListenerForView();
        setUpCardStackView();

    }

    private void initListCardContents() {
        listCardContents.add("Có điều gì đó bạn đã mơ ước từ lâu không? Điều gì đã ngăn cản bạn khỏi nó?");
        listCardContents.add("Nếu thú cưng của bạn có thể trả lời một câu hỏi, bạn sẽ hỏi chúng điều gì?");
        listCardContents.add("Bộ phim hoạt hình yêu thích của bạn khi còn nhỏ là gì?");
        listCardContents.add("Cuốn sách cuối cùng bạn đọc là gì? Bạn muốn đề xuất nó cho một người khác không?");
        listCardContents.add("Bạn có sưu tầm thứ gì không?");
        listCardContents.add("Nếu bạn có thể có bất kỳ sức mạnh ma thuật nào, đó sẽ là gì?");
        listCardContents.add("Mô tả bữa tiệc lý tưởng của bạn.");
        listCardContents.add("Bộ phim buồn nhất bạn đã xem là gì?");
        listCardContents.add("Điều gì bạn ước bạn thực sự giỏi?");
        listCardContents.add("Điều gì bạn yêu thích về quê hương của bạn?");
        listCardContents.add("Trò chơi boardgame yêu thích của bạn là gì?");
        listCardContents.add("Kỳ nghỉ đáng nhớ nhất của bạn đã diễn ra như thế nào?");
        listCardContents.add("Bạn có một tài năng tiềm ẩn nào không? Đó là gì?");
        listCardContents.add("Điều gì khiến bạn cảm thấy tự tin?");
        listCardContents.add("Người nổi tiếng nhất mà bạn từng gặp là ai?");
        listCardContents.add("Có dự án nào bạn đã bắt đầu nhưng chưa kết thúc?");
        listCardContents.add("Bài hát yêu thích của bạn là gì?");
        listCardContents.add("Bạn ghét món ăn nào nhất? Tại sao?");
        listCardContents.add("Bạn thích món ăn nào nhất? Tại sao?");
        listCardContents.add("Cách ngu ngốc nhất mà bạn từng tự làm mình bị thương là gì?");
        listCardContents.add("Bạn sẽ cưỡi con vật nào, nếu có cơ hội?");
        listCardContents.add("Bạn có một mùi hương yêu thích không? Đó là gì?");
        listCardContents.add("Bạn muốn nuôi con vật gì làm thú cưng?");
        listCardContents.add("Ước mơ thời thơ ấu của bạn là gì?");
        listCardContents.add("Công việc đầu tiên của bạn là gì?");
        listCardContents.add("Môn thể thao yêu thích của bạn là gì?");
        listCardContents.add("Bạn đã bao giờ giành được bất cứ điều gì trong một cuộc thi chưa? Đó là cái gì vậy?");
        listCardContents.add("Hãy nói về một điều mà mọi người đều thích còn bạn thì không?");
        listCardContents.add("Bạn có thói quen nào muốn thay đổi không?");
        listCardContents.add("Điều xấu hổ nhất đã xảy ra với bạn là gì?");
        listCardContents.add("Mô tả thói quen buổi sáng lý tưởng của bạn?");
        listCardContents.add("Bạn thích làm gì khi có thời gian ở một mình?");
        listCardContents.add("Bạn có muốn nổi tiếng không? Nổi tiếng theo cách nào?");
        listCardContents.add("Theo bạn, một ngày hoàn hảo là như thế nào?");
        listCardContents.add("Bạn cảm thấy biết ơn vì điều gì trong cuộc sống của mình?");
        listCardContents.add("Sự kiện nào ấn tượng nhất bạn từng tham gia?");
        listCardContents.add("Từ yêu thích của bạn là gì?");
        listCardContents.add("Điều ngu ngốc nhất bạn sẽ làm nếu chỉ còn một tuần để sống là gì?");
        listCardContents.add("Bạn có bao giờ ước mình được sinh ra ở một thế kỷ khác chưa?");
        listCardContents.add("Bạn tự mô tả bản thân là người như thế nào?");
        listCardContents.add("Ai là người quan trọng nhất trong cuộc đời bạn?");
        listCardContents.add("Điều kỳ lạ nhất bạn đã làm trong một ngày?");
        listCardContents.add("Bạn thích gì nhất ở bản thân?");
        listCardContents.add("Điều đầu tiên bạn làm khi thức dậy vào buổi sáng là gì?");
        listCardContents.add("Điều gì làm bạn sợ nhất?");
        listCardContents.add("Điều gì làm bạn phấn khích nhất?");
        listCardContents.add("Kiểu thời trang mà bạn thích là gì?");
        listCardContents.add("Màu yêu thích của bạn là gì?");
        listCardContents.add("Một món quá quý giá nhất mà bạn đã nhận được là gì?");
        listCardContents.add("Nếu có một điều ước, bạn sẽ ước điều gì?");

        Collections.shuffle(listCardContents);
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

package uit.itszoo.izrandom.random_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;

import com.iigo.library.DiceLoadingView;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.chooser.ChooserActivity;
import uit.itszoo.izrandom.random_module.divide_team.DivideTeamActivity;
import uit.itszoo.izrandom.random_module.flip_coin.FlipCoinActivity;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.LuckyWheelActivity;
import uit.itszoo.izrandom.random_module.raffle.RaffleActivity;
import uit.itszoo.izrandom.random_module.random_direction.RandomDirectionActivity;
import uit.itszoo.izrandom.random_module.random_integer.RandomIntegerActivity;
import uit.itszoo.izrandom.random_module.roll_dice.RollDiceActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RandomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RandomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CardView randomDirectionCardView;
    CardView rollDiceCardView;
    CardView flipCoinCardView;
    CardView luckyWheelCardView;
    CardView randomNumberCardView;
    CardView randomListCardView;
    CardView chooserCardView;
    CardView raffleCardView;
    CardView divideTeamCardView;

    ImageView ivDirection;
    CardView flipCardCardView;
    Animation rotateAnimation;

    DiceLoadingView dlvRollDice;

    ImageView ivCoin;
    ScaleAnimation coinAnimation;
    int flipPivot = 0;
    Coin coin = new Coin("1", 1, R.drawable.ic_coin_1_head, R.drawable.ic_coin_1_tail, false);

    public RandomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RandomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RandomFragment newInstance(String param1, String param2) {
        RandomFragment fragment = new RandomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        setListenerForView();

        performAnimation();
    }

    public void performAnimation() {
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0:
                rotateAnimation.setDuration(800);
                rotateAnimation.setInterpolator(new AnticipateOvershootInterpolator());
                ivDirection.startAnimation(rotateAnimation);
                break;
            case 1:
                dlvRollDice.setDuration(800);
                dlvRollDice.setRepeatCount(0);
                break;
            case 2:
                ivCoin.startAnimation(coinAnimation);
                break;
            default:
                break;
        }


    }

    public void initView(@NonNull @NotNull View view) {
        randomDirectionCardView = view.findViewById(R.id.cv_random_direction);
        rollDiceCardView = view.findViewById(R.id.cv_roll_dice);
        flipCoinCardView = view.findViewById(R.id.cv_flip_coin);
        luckyWheelCardView = view.findViewById(R.id.cv_lucky_wheel);
        randomNumberCardView = view.findViewById(R.id.cv_random_number);
        randomListCardView = view.findViewById(R.id.cv_random_list);
        chooserCardView = view.findViewById(R.id.cv_chooser);
        raffleCardView = view.findViewById(R.id.cv_raffle);
        divideTeamCardView = view.findViewById(R.id.cv_divide_team);
        flipCardCardView = view.findViewById(R.id.cv_flip_card);
        ivDirection = view.findViewById(R.id.ic_random_direction);
        dlvRollDice = view.findViewById(R.id.ic_roll_dice);
        dlvRollDice.setDuration(0);
        dlvRollDice.setRepeatCount(0);
        ivCoin = view.findViewById(R.id.ic_flip_coin);

        initCoinAnimation();
        rotateAnimation = new RotateAnimation(0f, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    }

    public void setListenerForView() {
        randomDirectionCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRandomDirection = new Intent(getContext(), RandomDirectionActivity.class);
                startActivity(intentToRandomDirection);
            }
        });

        rollDiceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRollDice = new Intent(getContext(), RollDiceActivity.class);
                startActivity(intentToRollDice);
            }
        });

        randomNumberCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRandomNumber = new Intent(getContext(), RandomIntegerActivity.class);
                startActivity(intentToRandomNumber);
            }
        });

        luckyWheelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToLuckyWheel = new Intent(getContext(), LuckyWheelActivity.class);
                startActivity(intentToLuckyWheel);
            }
        });

        flipCoinCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToFlipCoin = new Intent(getContext(), FlipCoinActivity.class);
                startActivity(intentToFlipCoin);
            }
        });

        chooserCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToChooser = new Intent(getContext(), ChooserActivity.class);
                startActivity(intentToChooser);
            }
        });
        flipCardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToFlipCard = new Intent(getContext(), FlipCardMenuActivity.class);
                startActivity(intentToFlipCard);
            }
        });


        raffleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRaffle = new Intent(getContext(), RaffleActivity.class);
                startActivity(intentToRaffle);
            }
        });

        divideTeamCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToDivideTeam = new Intent(getContext(), DivideTeamActivity.class);
                startActivity(intentToDivideTeam);
            }
        });

    }

    public void initCoinAnimation() {
        coinAnimation = new ScaleAnimation(
                1, 0,
                1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        coinAnimation.setDuration(100);
        coinAnimation.setInterpolator(new FastOutLinearInInterpolator());
        coinAnimation.setRepeatCount(5);
        coinAnimation.setRepeatMode(Animation.REVERSE);

        coinAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (flipPivot % 2 == 0) {
                    if (coin.isHead) {
                        ivCoin.setImageResource(coin.getDrawableTail());
                        coin.isHead = false;
                    } else {
                        ivCoin.setImageResource(coin.getDrawableHead());
                        coin.isHead = true;
                    }
                    flipPivot = 0;
                }
                flipPivot++;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random, container, false);
    }
}
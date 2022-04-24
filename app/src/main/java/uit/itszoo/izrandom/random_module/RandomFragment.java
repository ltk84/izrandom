package uit.itszoo.izrandom.random_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.LuckyWheelActivity;
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
    CardView flipCardCardView;
    ImageView imageView;
    Animation rotateAnimation;

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
        rotateAnimation.setDuration(800);
        rotateAnimation.setInterpolator(new AnticipateOvershootInterpolator());

        imageView.startAnimation(rotateAnimation);
    }

    public void initView(@NonNull @NotNull View view) {
        randomDirectionCardView = view.findViewById(R.id.cv_random_direction);
        rollDiceCardView = view.findViewById(R.id.cv_roll_dice);
        flipCoinCardView = view.findViewById(R.id.cv_flip_coin);
        luckyWheelCardView = view.findViewById(R.id.cv_lucky_wheel);
        randomNumberCardView = view.findViewById(R.id.cv_random_number);
        randomListCardView = view.findViewById(R.id.cv_random_list);
        flipCardCardView = view.findViewById(R.id.cv_flip_card);
        imageView = view.findViewById(R.id.imageView);
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

        flipCardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToFlipCard = new Intent(getContext(), FlipCardMenuActivity.class);
                startActivity(intentToFlipCard);
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
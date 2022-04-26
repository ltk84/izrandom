package uit.itszoo.izrandom.random_module.divide_team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;
import com.iigo.library.DiceLoadingView;
import com.jama.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.divide_team.divide_team_guide.DivideTeamGuideDialog;
import uit.itszoo.izrandom.utilities.InputFilterMinMax;
import uit.itszoo.izrandom.utilities.VerticalImageSpan;

public class DivideTeamActivity extends AppCompatActivity {

    ViewGroup mainLayout;
    ViewGroup divideTeamPlaceholder;
    ViewGroup resultPlaceholder;
    MaterialButton startDivideTeamButton;
    MaterialButton backFromDivideTeamButton;
    TextInputEditText participantEditText;
    DiceLoadingView diceLoadingView;

    CardView teamCountCardView;
    CardView personPerTeamCountCardView;
    ImageView teamCountCheck;
    ImageView personPerTeamCountCheck;
    EditText teamCountEditText;
    EditText personPerTeamCountEditText;
    int memberCount = -1;
    int teamCount = -1;

    ImageButton backButton;
    ImageButton guideButton;

    TextView tvGuide;

    private int participantFieldSpan = 0, participantFieldLength = 0;
    int participantLastSpan;

    List<String> participants = new ArrayList<>();
    List<Integer> teamMemberCount = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide_team);
        initView(this);
    }

    private void initView(Context context) {

        tvGuide = findViewById(R.id.txt_guide);
        mainLayout = findViewById(R.id.layout_divide_team);
        divideTeamPlaceholder = findViewById(R.id.divide_team_placeholder);
        startDivideTeamButton = findViewById(R.id.start_button);
        backFromDivideTeamButton = findViewById(R.id.back_button);
        participantEditText = findViewById(R.id.participant_edit_text);
        participantEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        participantEditText.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        participantEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        teamCountCheck = findViewById(R.id.check_team_count);
        personPerTeamCountCheck = findViewById(R.id.check_person_per_team_count);
        teamCountEditText = findViewById(R.id.team_count_value);
        personPerTeamCountEditText = findViewById(R.id.person_per_team_count_value);
        teamCountEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "10000", tvGuide)});
        personPerTeamCountEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "10000", tvGuide)});
        teamCountCardView = findViewById(R.id.card_view_team_count);
        personPerTeamCountCardView = findViewById(R.id.card_view_person_per_team_count);

        backButton = findViewById(R.id.bb_divide_team);
        guideButton = findViewById(R.id.guide_button);

        backButton.setOnClickListener(view -> {
            onBackPressed();
        });

        guideButton.setOnClickListener(view -> {
            DivideTeamGuideDialog divideTeamGuideDialog = new DivideTeamGuideDialog(DivideTeamActivity.this);
            divideTeamGuideDialog.show();
        });

        startDivideTeamButton.setOnClickListener(view -> {
            tvGuide.setVisibility(View.INVISIBLE);
            buildResult(context);
        });

        backFromDivideTeamButton.setOnClickListener(view -> buildMain());

        teamCountCardView.setOnClickListener(view -> {
            teamCountCheck.setVisibility(View.VISIBLE);
            teamCountEditText.setEnabled(true);
            personPerTeamCountCheck.setVisibility(View.INVISIBLE);
            personPerTeamCountEditText.setEnabled(false);
            validateBeforeStart();
        });

        personPerTeamCountCardView.setOnClickListener(view -> {
            personPerTeamCountCheck.setVisibility(View.VISIBLE);
            personPerTeamCountEditText.setEnabled(true);
            teamCountCheck.setVisibility(View.INVISIBLE);
            teamCountEditText.setEnabled(false);
            validateBeforeStart();
        });

        participantEditText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (participantFieldLength - participantFieldSpan != 0) {
                    participants.add(participantEditText.getEditableText().subSequence(participantFieldSpan, participantFieldLength).toString());
                }
                participantFieldSpan = addChip(context, participantEditText, participantFieldSpan, participantFieldLength);

                validateBeforeStart();
                return true;
            }
            return false;
        });
        participantEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                participantFieldLength = charSequence.length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (participantFieldLength - participantFieldSpan > 25) {
                    showLengthHint();
                    editable.delete(editable.length() - 2, editable.length() - 1);
                }

                if (editable.length() < participantFieldSpan)
                {
                    if (participants.size() > 0) {
                        if (participantLastSpan == 0) {
                            participantLastSpan = participantFieldSpan - participants.get(participants.size() - 1).length();
                        }
                        if (editable.length() > participantLastSpan) {
                            editable.delete(participantLastSpan, editable.length());
                        }

                        if (editable.length()  == participantLastSpan) {
                            if (participants.size() > 0) {
                                participants.remove(participants.size()  - 1);
                                participantFieldSpan = editable.length();
                                if (participants.size() != 0) {
                                    participantLastSpan = participantFieldSpan - participants.get(participants.size() - 1).length();
                                } else {
                                    participantLastSpan = 0;
                                    participantFieldSpan = 0;
                                }
                            }
                            validateBeforeStart();
                        }
                    }
                }
            }
        });

        teamCountEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateBeforeStart();
            }
        });

        personPerTeamCountEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateBeforeStart();
            }
        });
    }

    private void showStartHint() {
        tvGuide.setText(R.string.start_divide_team_hint);
        tvGuide.setVisibility(View.VISIBLE);
        tvGuide.postDelayed(() -> tvGuide.setVisibility(View.INVISIBLE), 5000);
    }

    private void showLengthHint() {
        tvGuide.setText(R.string.length_hint);
        tvGuide.setVisibility(View.VISIBLE);
        tvGuide.postDelayed(() -> tvGuide.setVisibility(View.INVISIBLE), 5000);
    }

    private boolean isInputValid() {
        memberCount = -1;
        teamCount = -1;
        if (personPerTeamCountCheck.getVisibility() == View.VISIBLE) {
            String value = personPerTeamCountEditText.getText().toString();
            if (value.isEmpty()) {
                return false;
            }
            int count = Integer.parseInt(value);
            if (participants.size() < count || count == 0) {
                return false;
            } else {
                memberCount = count;
                return true;
            }
        } else if (teamCountCheck.getVisibility() == View.VISIBLE){
            String value = teamCountEditText.getText().toString();
            if (value.isEmpty()) {
                return false;
            }
            int count = Integer.parseInt(value);
            if (participants.size() < count || count == 0) {
                return false;
            } else {
                teamCount = count;
                return true;
            }
        }
        return true;
    }

    private int addChip(Context context, TextInputEditText editText, int span, int length) {
        Editable editable =
                editText.getEditableText();
        ChipDrawable chip = ChipDrawable.createFromResource(context, R.xml.standalone_chip);
        VerticalImageSpan imageSpan;
        chip.setText(editable.subSequence(span, length));
        chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
        imageSpan = new VerticalImageSpan(chip);
        editable.setSpan(imageSpan, span, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span = length;
        return span;
    }

    private void validateBeforeStart() {
        if (isInputValid()) {
            startDivideTeamButton.setEnabled(true);
        } else {
            showStartHint();
            startDivideTeamButton.setEnabled(false);
        }
    }

    private void buildResult(Context context) {

        startDivideTeamButton.setVisibility(View.INVISIBLE);
        backFromDivideTeamButton.setVisibility(View.VISIBLE);
        teamCountCardView.setVisibility(View.GONE);
        personPerTeamCountCardView.setVisibility(View.GONE);

        resultPlaceholder = (LinearLayout) getLayoutInflater().inflate(R.layout.linear_layout_divide_team_result, null);


//        // Set 2D Rotate Forever Animation
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
//                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setRepeatCount(Animation.INFINITE);
//        rotateAnimation.setDuration(300);
//        rotateAnimation.setInterpolator(new LinearInterpolator());
//        rotateAnimation.setFillAfter(true);
//
//
//        diceLoadingView = resultPlaceholder.findViewById(R.id.raffle_loading);
//        diceLoadingView.setInterpolator(new LinearInterpolator());
//        diceLoadingView.setRepeatCount(Animation.INFINITE);
//        diceLoadingView.setDuration(200);
//
//        diceLoadingView.startAnimation(rotateAnimation);

//        Handler loadingHandler = new Handler();
//        loadingHandler.postDelayed(() -> {
//            //diceLoadingView.clearAnimation();
////            TableRow loadingPlaceholder = resultPlaceholder.findViewById(R.id.loading_placeholder);
////            loadingPlaceholder.setVisibility(View.GONE);
//
//            ChipGroup chipGroup = resultPlaceholder.findViewById(R.id.person_chip_holder);
//            for (int i = 0; i < participants.size(); i++) {
//
//                Chip participantChip = new Chip(context);
//                participantChip.setText(participants.get(i));
//                TableRow.LayoutParams lpParticipantChip = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
//                lpParticipantChip.gravity = Gravity.CENTER_HORIZONTAL;
//                participantChip.setLayoutParams(lpParticipantChip);
//
//                chipGroup.addView(participantChip);
//            }
//        }, 800);

        generateTeam();
        CarouselView carouselView = resultPlaceholder.findViewById(R.id.carouselView);
        setupResultCarouselView(context, carouselView);

        // Replace RESULT HOLDER LAYOUT for DIVIDE TEAM HOLDER
        int index = mainLayout.indexOfChild(divideTeamPlaceholder);
        mainLayout.removeView(divideTeamPlaceholder);
        mainLayout.addView(resultPlaceholder, index);

        // Set constraints for RESULT HOLDER LAYOUT
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) mainLayout);
        constraintSet.connect(resultPlaceholder.getId(),ConstraintSet.END,mainLayout.getId(),ConstraintSet.END,0);
        constraintSet.connect(resultPlaceholder.getId(),ConstraintSet.START,mainLayout.getId(),ConstraintSet.START,0);
        constraintSet.connect(resultPlaceholder.getId(),ConstraintSet.TOP,R.id.toolbar,ConstraintSet.BOTTOM,0);
        constraintSet.connect(resultPlaceholder.getId(),ConstraintSet.BOTTOM, backFromDivideTeamButton.getId(),ConstraintSet.TOP,0);
        constraintSet.connect(backFromDivideTeamButton.getId(),ConstraintSet.TOP,resultPlaceholder.getId(),ConstraintSet.BOTTOM,0);
        constraintSet.applyTo((ConstraintLayout) mainLayout);
    }

    private void buildMain() {

        backFromDivideTeamButton.setVisibility(View.INVISIBLE);
        startDivideTeamButton.setVisibility(View.VISIBLE);
        teamCountCardView.setVisibility(View.VISIBLE);
        personPerTeamCountCardView.setVisibility(View.VISIBLE);


        // Replace RAFFLE HOLDER for RESULT HOLDER LAYOUT
        int index = mainLayout.indexOfChild(resultPlaceholder);
        mainLayout.removeView(resultPlaceholder);
        resultPlaceholder = null;
        mainLayout.addView(divideTeamPlaceholder, index);

        float marginPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                24,
                getResources().getDisplayMetrics()
        );

        // Set constraints for RESULT HOLDER LAYOUT
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) mainLayout);
        constraintSet.connect(divideTeamPlaceholder.getId(),ConstraintSet.END,mainLayout.getId(),ConstraintSet.END,(int) marginPx);
        constraintSet.connect(divideTeamPlaceholder.getId(),ConstraintSet.START,mainLayout.getId(),ConstraintSet.START,(int) marginPx);
        constraintSet.connect(divideTeamPlaceholder.getId(),ConstraintSet.TOP,R.id.toolbar,ConstraintSet.BOTTOM,0);
        constraintSet.connect(divideTeamPlaceholder.getId(),ConstraintSet.BOTTOM, startDivideTeamButton.getId(),ConstraintSet.TOP,0);
        constraintSet.connect(startDivideTeamButton.getId(),ConstraintSet.TOP,divideTeamPlaceholder.getId(),ConstraintSet.BOTTOM,0);
        constraintSet.applyTo((ConstraintLayout) mainLayout);

        isInputValid();
    }

    private void generateTeam() {
        teamMemberCount.clear();
        int personCount = participants.size();
        int surplus = -1;

        if (memberCount == -1) {
            memberCount = personCount / teamCount;
            surplus = personCount % teamCount;

        }

        if (teamCount == -1) {
            teamCount = personCount / memberCount;
            surplus = personCount % memberCount;
        }

//        if (surplus < teamCount && surplus >= memberCount) {
//            teamCount += surplus / memberCount;
//            surplus = surplus % memberCount;
//        } else
        if (surplus >= teamCount && surplus < memberCount - 1) {
            memberCount += surplus / teamCount;
            surplus = surplus % teamCount;
        }
        System.out.println(teamCount + " " + memberCount + " ");

        if (surplus <= teamCount) { // && surplus < memberCount) {
            int index = 0;
            while (index < teamCount) {
                if (surplus != 0) {
                    if (surplus == memberCount - 1) {
                        teamMemberCount.add(surplus);
                        teamCount++;
                        surplus = 0;
                    } else {
                        teamMemberCount.add(memberCount + 1);
                        surplus--;
                    }
                } else {
                    teamMemberCount.add(memberCount);
                }
                index++;
            }
        }
    }

    private void setupResultCarouselView(Context context, CarouselView carouselView) {
        Collections.shuffle(participants);
        if (teamMemberCount.size() > 0) {
            //AtomicInteger iCurrentTeam = new AtomicInteger();
            //AtomicInteger sizeTeam = new AtomicInteger();
            //AtomicBoolean hasInit = new AtomicBoolean(false);

            carouselView.setSize(teamMemberCount.size());
            carouselView.setResource(R.layout.divide_team_carousel_item);
            carouselView.setCarouselViewListener((view, position) -> {
                View carouselItemView = view.findViewById(R.id.divide_team_carousel_item);
                TextView tvTeamLabel = carouselItemView.findViewById(R.id.team_label);

                String teamLabel = "Nhóm " + (position + 1);

                tvTeamLabel.setText(teamLabel);

                //if (!hasInit.get()) {

                    FlexboxLayout chipHolder = carouselItemView.findViewById(R.id.person_chip_holder);
                    chipHolder.removeAllViews();

                //    sizeTeam.addAndGet(teamMemberCount.get(position));
                    int iCurrentTeam = getTeamStartIndex(position);
                    int sizeTeam = getTeamEndIndex(position);
                if (chipHolder.getChildCount() < teamMemberCount.get(position)) {
                    for (int i = iCurrentTeam; i < sizeTeam; i++) {
                        Chip participantChip = new Chip(context);
                        participantChip.setText(participants.get(i));
                        chipHolder.addView(participantChip);
                    }
                }
                    //iCurrentTeam.addAndGet(teamMemberCount.get(position));

                    //if (sizeTeam.get() == participants.size()) {
                    //    hasInit.set(true);
                    //}
                //}
            });

            carouselView.show();
        }
    }

    private int getTeamStartIndex(int position) {
        if (position == 0) {
            return 0;
        } else {
            return getTeamEndIndex(position - 1);
        }
    }

    private int getTeamEndIndex(int position) {
        return getTeamStartIndex(position) + teamMemberCount.get(position);
    }

}
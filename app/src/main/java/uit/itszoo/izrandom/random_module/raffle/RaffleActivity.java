package uit.itszoo.izrandom.random_module.raffle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;
import com.iigo.library.DiceLoadingView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.raffle.raffle_guide.RaffleGuideDialog;
import uit.itszoo.izrandom.utilities.VerticalImageSpan;

public class RaffleActivity extends AppCompatActivity {

    ViewGroup mainLayout;
    ViewGroup rafflePlaceholder;
    TableLayout resultPlaceholder;
    MaterialButton startRaffleButton;
    MaterialButton backFromRaffleButton;
    TextInputEditText participantEditText;
    TextInputEditText awardEditText;
    DiceLoadingView diceLoadingView;

    ImageButton backButton;
    ImageButton guideButton;

    TextView tvGuide;

    private int participantFieldSpan = 0, participantFieldLength = 0;
    private int awardFieldSpan = 0, awardFieldLength = 0;
    int participantLastSpan;
    int awardLastSpan;

    List<String> participants = new ArrayList<>();
    List<String> awards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raffle);
        initView(this);
    }

    private void initView(Context context) {
        mainLayout = findViewById(R.id.layout_raffle);
        rafflePlaceholder = findViewById(R.id.raffle_placeholder);
        startRaffleButton = findViewById(R.id.start_button);
        backFromRaffleButton = findViewById(R.id.back_button);
        participantEditText = findViewById(R.id.participant_edit_text);
        participantEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        participantEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);


        awardEditText = findViewById(R.id.award_edit_text);
        awardEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        awardEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);

        tvGuide = findViewById(R.id.txt_guide);

        backButton = findViewById(R.id.bb_raffle);
        guideButton = findViewById(R.id.guide_button);

        backButton.setOnClickListener(view -> onBackPressed());

        guideButton.setOnClickListener(view -> {
            RaffleGuideDialog raffleGuideDialog = new RaffleGuideDialog(RaffleActivity.this);
            raffleGuideDialog.show();
        });

        startRaffleButton.setOnClickListener(view -> {
            tvGuide.setVisibility(View.INVISIBLE);
            buildResult(context);
        });

        backFromRaffleButton.setOnClickListener(view -> buildMain());

        participantEditText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (participantFieldLength - participantFieldSpan != 0) {
                    participants.add(participantEditText.getEditableText().subSequence(participantFieldSpan, participantFieldLength).toString());
                }
                participantFieldSpan = addChip(context, participantEditText, participantFieldSpan, participantFieldLength);

                if (isInputValid()) {
                    startRaffleButton.setEnabled(true);
                } else {
                    showStartHint();
                    startRaffleButton.setEnabled(false);
                }
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
                    System.out.println("Alo");
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
                            if (isInputValid()) {
                                startRaffleButton.setEnabled(true);
                            } else {
                                showStartHint();
                                startRaffleButton.setEnabled(false);
                            }
                        }
                    }
                }
            }
        });
        awardEditText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (awardFieldLength - awardFieldSpan != 0) {
                    awards.add(awardEditText.getEditableText().subSequence(awardFieldSpan, awardFieldLength).toString());
                }
                awardFieldSpan = addChip(context, awardEditText, awardFieldSpan, awardFieldLength);

                if (isInputValid()) {
                    startRaffleButton.setEnabled(true);
                } else {
                    showStartHint();
                    startRaffleButton.setEnabled(false);
                }
                return true;
            }
            return false;
        });
        awardEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                awardFieldLength = charSequence.length();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (awardFieldLength - awardFieldSpan > 25) {
                    showLengthHint();
                    editable.delete(editable.length() - 2, editable.length() - 1);
                }

                if (editable.length() < awardFieldSpan)
                {
                    if (awards.size() > 0) {
                        if (awardLastSpan == 0) {
                            awardLastSpan = awardFieldSpan - awards.get(awards.size() - 1).length();
                        }
                        if (editable.length() > awardLastSpan) {
                            editable.delete(awardLastSpan, editable.length());
                        }

                        if (editable.length()  == awardLastSpan) {
                            if (awards.size() > 0) {
                                awards.remove(awards.size()  - 1);
                                awardFieldSpan = editable.length();
                                if (awards.size() != 0) {
                                    awardLastSpan = awardFieldSpan - awards.get(awards.size() - 1).length();
                                } else {
                                    awardLastSpan = 0;
                                    awardFieldSpan = 0;
                                }
                            }
                            if (isInputValid()) {
                                startRaffleButton.setEnabled(true);
                            } else {
                                showStartHint();
                                startRaffleButton.setEnabled(false);
                            }
                        }
                    }
                }
            }
        });
    }

    private void showStartHint() {
        tvGuide.setText(R.string.start_hint);
        tvGuide.setVisibility(View.VISIBLE);
        tvGuide.postDelayed(() -> tvGuide.setVisibility(View.INVISIBLE), 5000);
    }

    private void showLengthHint() {
        tvGuide.setText(R.string.length_hint);
        tvGuide.setVisibility(View.VISIBLE);
        tvGuide.postDelayed(() -> tvGuide.setVisibility(View.INVISIBLE), 5000);
    }

    private boolean isInputValid() {
        if (participants.size() == 0 || awards.size() == 0) {
            return false;
        }
        return participants.size() >= awards.size();
    }

    private void buildResult(Context context) {
        shuffleResultList();

        startRaffleButton.setVisibility(View.INVISIBLE);
        backFromRaffleButton.setVisibility(View.VISIBLE);

        resultPlaceholder = (TableLayout) getLayoutInflater().inflate(R.layout.table_layout_raffle_result, null);


        // Set 2D Rotate Forever Animation
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(300);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setFillAfter(true);


        diceLoadingView = resultPlaceholder.findViewById(R.id.raffle_loading);
        diceLoadingView.setInterpolator(new LinearInterpolator());
        diceLoadingView.setRepeatCount(Animation.INFINITE);
        diceLoadingView.setDuration(200);

        diceLoadingView.startAnimation(rotateAnimation);

        Handler loadingHandler = new Handler();
        loadingHandler.postDelayed(() -> {
            diceLoadingView.clearAnimation();
            TableRow loadingPlaceholder = resultPlaceholder.findViewById(R.id.loading_placeholder);
            loadingPlaceholder.setVisibility(View.GONE);

            for (int i = 0; i < awards.size(); i++) {
                TableRow tr = new TableRow(context);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.setGravity(Gravity.CENTER);

                Chip participantChip = new Chip(context);
                participantChip.setText(participants.get(i));
                TableRow.LayoutParams lpParticipantChip = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                lpParticipantChip.gravity = Gravity.END;
                participantChip.setLayoutParams(lpParticipantChip);

                ImageView ivConnect = new ImageView(context);
                ivConnect.setBackground(null);
                ivConnect.setScaleX(0.75f);
                ivConnect.setScaleY(0.75f);
                ivConnect.setImageDrawable(getDrawable(R.drawable.ic_check_button));
                ivConnect.setColorFilter(getColor(R.color.colorGreen));
                TableRow.LayoutParams lpConnect = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                ivConnect.setLayoutParams(lpConnect);

                Chip awardChip = new Chip(context);
                awardChip.setText(awards.get(i));
                TableRow.LayoutParams lpAwardChip = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                lpAwardChip.gravity = Gravity.START;
                awardChip.setLayoutParams(lpAwardChip);

                tr.addView(participantChip);
                tr.addView(ivConnect);
                tr.addView(awardChip);

                resultPlaceholder.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }, 800);

        // Replace RESULT HOLDER LAYOUT for RAFFLE HOLDER
        int index = mainLayout.indexOfChild(rafflePlaceholder);
        mainLayout.removeView(rafflePlaceholder);
        mainLayout.addView(resultPlaceholder, index);

        // Set constraints for RESULT HOLDER LAYOUT
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) mainLayout);
        constraintSet.connect(resultPlaceholder.getId(),ConstraintSet.END,mainLayout.getId(),ConstraintSet.END,0);
        constraintSet.connect(resultPlaceholder.getId(),ConstraintSet.START,mainLayout.getId(),ConstraintSet.START,0);
        constraintSet.connect(resultPlaceholder.getId(),ConstraintSet.TOP,R.id.toolbar,ConstraintSet.BOTTOM,0);
        constraintSet.connect(resultPlaceholder.getId(),ConstraintSet.BOTTOM, backFromRaffleButton.getId(),ConstraintSet.TOP,0);
        constraintSet.connect(backFromRaffleButton.getId(),ConstraintSet.TOP,resultPlaceholder.getId(),ConstraintSet.BOTTOM,0);
        constraintSet.applyTo((ConstraintLayout) mainLayout);
    }

    private void shuffleResultList() {
        Collections.shuffle(participants);
        Collections.shuffle(awards);
    }

    private void buildMain() {
        backFromRaffleButton.setVisibility(View.INVISIBLE);
        startRaffleButton.setVisibility(View.VISIBLE);


        // Replace RAFFLE HOLDER for RESULT HOLDER LAYOUT
        int index = mainLayout.indexOfChild(resultPlaceholder);
        mainLayout.removeView(resultPlaceholder);
        resultPlaceholder = null;
        mainLayout.addView(rafflePlaceholder, index);

        float marginPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                24,
                getResources().getDisplayMetrics()
        );

        // Set constraints for RESULT HOLDER LAYOUT
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) mainLayout);
        constraintSet.connect(rafflePlaceholder.getId(),ConstraintSet.END,mainLayout.getId(),ConstraintSet.END,(int) marginPx);
        constraintSet.connect(rafflePlaceholder.getId(),ConstraintSet.START,mainLayout.getId(),ConstraintSet.START,(int) marginPx);
        constraintSet.connect(rafflePlaceholder.getId(),ConstraintSet.TOP,R.id.toolbar,ConstraintSet.BOTTOM,0);
        constraintSet.connect(rafflePlaceholder.getId(),ConstraintSet.BOTTOM, startRaffleButton.getId(),ConstraintSet.TOP,0);
        constraintSet.connect(startRaffleButton.getId(),ConstraintSet.TOP,rafflePlaceholder.getId(),ConstraintSet.BOTTOM,0);
        constraintSet.applyTo((ConstraintLayout) mainLayout);
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
}
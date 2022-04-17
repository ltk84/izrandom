package uit.itszoo.izrandom.random_module.raffle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;

public class RaffleActivity extends AppCompatActivity {

    ViewGroup mainLayout;
    ViewGroup rafflePlaceholder;
    TableLayout resultPlaceholder;
    MaterialButton startButton;
    MaterialButton backButton;
    TextInputEditText participantEditText;
    TextInputEditText awardEditText;

    TextView tvGuide;

    private int participantFieldSpan = 0, participantFieldLength = 0;
    private int awardFieldSpan = 0, awardFieldLength = 0;

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
        startButton = findViewById(R.id.start_button);
        backButton = findViewById(R.id.back_button);
        participantEditText = findViewById(R.id.participant_edit_text);
        awardEditText = findViewById(R.id.award_edit_text);

        tvGuide = findViewById(R.id.txt_guide);

        startButton.setOnClickListener(view -> {
            tvGuide.setVisibility(View.INVISIBLE);
            buildResult(context);
        });

        backButton.setOnClickListener(view -> buildMain());

        participantEditText.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
            {
                switch (keyCode)
                {
                    case KeyEvent.KEYCODE_SEMICOLON:
                    case KeyEvent.KEYCODE_COMMA:
                    case KeyEvent.KEYCODE_ENTER:
                        if (participantFieldLength - participantFieldSpan != 0) {
                            participants.add(participantEditText.getEditableText().subSequence(participantFieldSpan, participantFieldLength).toString());
                        }
                        participantFieldSpan = addChip(context, participantEditText, participantFieldSpan, participantFieldLength);

                        if (isInputValid()) {
                            startButton.setEnabled(true);
                        } else {
                            showStartHint();
                            startButton.setEnabled(false);
                        }

                        return true;
                    default:
                        break;
                }

                if (participantFieldLength - participantFieldSpan > 25 && keyCode != KeyEvent.KEYCODE_DEL) {
                    showLengthHint();
                    return true;
                }
            }
            return false;
        });
        participantEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int charLength = charSequence.length();

                if (charLength < participantFieldSpan)
                {
                    participants.remove(participants.size() - 1);
                    participantFieldSpan = charLength;
                }
                participantFieldLength = charLength;
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        awardEditText.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
            {
                switch (keyCode)
                {
                    case KeyEvent.KEYCODE_SEMICOLON:
                    case KeyEvent.KEYCODE_COMMA:
                    case KeyEvent.KEYCODE_ENTER:
                        if (awardFieldLength - awardFieldSpan != 0) {
                            awards.add(awardEditText.getEditableText().subSequence(awardFieldSpan, awardFieldLength).toString());
                        }
                        awardFieldSpan = addChip(context, awardEditText, awardFieldSpan, awardFieldLength);

                        if (isInputValid()) {
                            startButton.setEnabled(true);
                        } else {
                            showStartHint();
                            startButton.setEnabled(false);
                        }
                        return true;
                    default:
                        break;
                }
                if (awardFieldLength - awardFieldSpan > 25 && keyCode != KeyEvent.KEYCODE_DEL) {
                    showLengthHint();
                    return true;
                }
            }
            return false;
        });
        awardEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int charLength = charSequence.length();

                if (charLength < awardFieldSpan)
                {
                    awards.remove(awards.size() - 1);
                    awardFieldSpan = charLength;
                    if (isInputValid()) {
                        startButton.setEnabled(true);
                    } else {
                        showStartHint();
                        startButton.setEnabled(false);
                    }
                }
                awardFieldLength = charLength;
            }

            @Override
            public void afterTextChanged(Editable editable) {
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

        startButton.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.VISIBLE);

        resultPlaceholder = (TableLayout) getLayoutInflater().inflate(R.layout.table_layout_raffle_result, null);

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
        constraintSet.connect(resultPlaceholder.getId(),ConstraintSet.BOTTOM,backButton.getId(),ConstraintSet.TOP,0);
        constraintSet.connect(backButton.getId(),ConstraintSet.TOP,resultPlaceholder.getId(),ConstraintSet.BOTTOM,0);
        constraintSet.applyTo((ConstraintLayout) mainLayout);
    }

    private void shuffleResultList() {
        Collections.shuffle(participants);
        Collections.shuffle(awards);
    }

    private void buildMain() {
        backButton.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);


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
        constraintSet.connect(rafflePlaceholder.getId(),ConstraintSet.BOTTOM,startButton.getId(),ConstraintSet.TOP,0);
        constraintSet.connect(startButton.getId(),ConstraintSet.TOP,rafflePlaceholder.getId(),ConstraintSet.BOTTOM,0);
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
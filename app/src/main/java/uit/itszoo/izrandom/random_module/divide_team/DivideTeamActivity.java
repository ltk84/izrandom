package uit.itszoo.izrandom.random_module.divide_team;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;
import com.iigo.library.DiceLoadingView;

import java.util.ArrayList;
import java.util.List;

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

    ImageButton backButton;
    ImageButton guideButton;

    TextView tvGuide;

    private int participantFieldSpan = 0, participantFieldLength = 0;
    int participantLastSpan;

    List<String> participants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide_team);
        initView(this);
    }

    private void initView(Context context) {

        tvGuide = findViewById(R.id.txt_guide);
        mainLayout = findViewById(R.id.layout_raffle);
        divideTeamPlaceholder = findViewById(R.id.divide_team_placeholder);
        startDivideTeamButton = findViewById(R.id.start_button);
        backFromDivideTeamButton = findViewById(R.id.back_button);
        participantEditText = findViewById(R.id.participant_edit_text);
        participantEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        participantEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
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
            //buildResult(context);
        });

//        backFromDivideTeamButton.setOnClickListener(view -> buildMain());

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
        if (personPerTeamCountCheck.getVisibility() == View.VISIBLE) {
            String value = personPerTeamCountEditText.getText().toString();
            if (value.isEmpty()) {
                return false;
            }
            int count = Integer.parseInt(value);
            return participants.size() >= count && count != 0;
        } else if (teamCountCheck.getVisibility() == View.VISIBLE){
            String value = teamCountEditText.getText().toString();
            if (value.isEmpty()) {
                return false;
            }
            int count = Integer.parseInt(value);
            return participants.size() >= count && count != 0;
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
}
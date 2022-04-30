package uit.itszoo.izrandom.random_module.tournament_bracket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telecom.InCallService;
import android.text.Editable;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ventura.bracketslib.BracketsView;
import com.ventura.bracketslib.model.ColomnData;
import com.ventura.bracketslib.model.CompetitorData;
import com.ventura.bracketslib.model.MatchData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.tournament_bracket.result.TournamentBracketResult;
import uit.itszoo.izrandom.utilities.VerticalImageSpan;

public class TournamentBracketActivity extends AppCompatActivity implements TournamentBracketContract.View {
    public static final String LIST_PARTICIPANT = "LIST_PARTICIPANT";
    MaterialButton startDivideTournament;
    TextInputEditText participantEditText;
    TextInputLayout textInputLayout;
    ImageButton backButton;
    ViewGroup mainLayout;

    private int participantFieldSpan = 0, participantFieldLength = 0;
    int participantLastSpan;
    List<String> participants = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_bracket);
        initView();
        setListener();
    }

    void initView()
    {
        startDivideTournament = findViewById(R.id.start_button);
        participantEditText = findViewById(R.id.participant_edit_text);
        participantEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        participantEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        textInputLayout = findViewById(R.id.participant_text_input);
        mainLayout = findViewById(R.id.main_tournament_layout);
        backButton = findViewById(R.id.bb_tb);
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

    void setListener()
    {
        participantEditText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (participantFieldLength - participantFieldSpan != 0) {
                    participants.add(participantEditText.getEditableText().subSequence(participantFieldSpan, participantFieldLength).toString());
                }
                participantFieldSpan = addChip(this, participantEditText, participantFieldSpan, participantFieldLength);

                startDivideTournament.setEnabled(true);
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
                    //showLengthHint();
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
                        }
                    }
                }
            }
        });
        startDivideTournament.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentToCustom = new Intent(getApplicationContext(), TournamentBracketResult.class);
                        intentToCustom.putExtra(TournamentBracketActivity.LIST_PARTICIPANT, (Serializable) participants);
                        startActivity(intentToCustom);
                    }
                }
        );
        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                }
        );
    }
    @Override
    public void setPresenter(TournamentBracketContract.Presenter presenter) {
    }
}

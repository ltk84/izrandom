package uit.itszoo.izrandom.random_module.raffle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;

import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;

import uit.itszoo.izrandom.R;

public class RaffleActivity extends AppCompatActivity {
    private int SpannedLength = 0,chipLength = 4;

    TextInputEditText participantEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raffle);
        initView(this);
    }

    private void initView(Context context) {
        participantEditText = findViewById(R.id.participant_edit_text);
        participantEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == SpannedLength - chipLength)
                {
                    SpannedLength = charSequence.length();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() - SpannedLength == chipLength) {
                    ChipDrawable chip = ChipDrawable.createFromResource(context, R.xml.standalone_chip);
                    chip.setText(editable.subSequence(SpannedLength,editable.length()));
                    chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(chip);
                    editable.setSpan(span, SpannedLength, editable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    SpannedLength = editable.length();
                }
            }
        });
    }
}
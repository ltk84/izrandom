package uit.itszoo.izrandom.play_module.truth_dare.models;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import org.jetbrains.annotations.NotNull;

import uit.itszoo.izrandom.R;

public class TruthDareCardView extends CardView {
    final int MAXIMUM_CARD_COUNT = 10;
    final int MINIMUM_CARD_COUNT = 1;

    ImageButton removeCardButton;
    ImageButton increaseButton;
    ImageButton decreaseButton;
    EditText cardLabelEditText;
    TextView cardAmountText;
    Drawable defaultBackgroundDecreaseButton;
    Drawable defaultBackgroundIncreaseButton;

    int cardCount = MINIMUM_CARD_COUNT;
    String cardID;

    public TruthDareCardView(@NonNull @NotNull Context context) {
        super(context);
        initView();
    }

    public TruthDareCardView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TruthDareCardView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setData(TruthDareCard card) {
        this.cardID = card.getId();
        cardLabelEditText.setText(card.getContent());
        cardAmountText.setText(String.valueOf(card.getCount()));
    }

    public TruthDareCard getCardInfo() {
        return new TruthDareCard(cardID, cardLabelEditText.getText().toString(), Integer.parseInt(cardAmountText.getText().toString()));
    }

    private void initView() {
        inflate(getContext(), R.layout.truth_dare_card_view, this);
        removeCardButton = this.findViewById(R.id.remove_card);
        increaseButton = this.findViewById(R.id.btn_increase_card);
        defaultBackgroundIncreaseButton = increaseButton.getBackground();

        decreaseButton = this.findViewById(R.id.btn_decrease_card);
        defaultBackgroundDecreaseButton = decreaseButton.getBackground();
        decreaseButton.setImageAlpha(75);
        decreaseButton.setBackground(null);

        cardLabelEditText = this.findViewById(R.id.card_edit_text);
        cardAmountText = this.findViewById(R.id.text_card_amount);
        cardAmountText.setText(String.valueOf(cardCount));
        setListeners();
    }

    private void setListeners() {
        removeCardButton.setOnClickListener(view -> {
            ((ViewGroup) this.getParent()).removeView(this);
        });

        increaseButton.setOnClickListener(view -> {
            if (cardCount < MAXIMUM_CARD_COUNT) {
                cardCount++;
                cardAmountText.setText(String.valueOf(cardCount));
            }
        });

        decreaseButton.setOnClickListener(view -> {
            if (cardCount > MINIMUM_CARD_COUNT) {
                cardCount--;
                cardAmountText.setText(String.valueOf(cardCount));
            }
        });

        cardAmountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().compareTo(String.valueOf(MINIMUM_CARD_COUNT)) == 0) {
                    decreaseButton.setImageAlpha(75);
                    decreaseButton.setBackground(null);
                } else if (charSequence.toString().compareTo(String.valueOf(MAXIMUM_CARD_COUNT)) == 0) {
                    increaseButton.setImageAlpha(75);
                    increaseButton.setBackground(null);
                } else {
                    decreaseButton.setImageAlpha(255);
                    decreaseButton.setBackground(defaultBackgroundDecreaseButton);
                    increaseButton.setImageAlpha(255);
                    increaseButton.setBackground(defaultBackgroundIncreaseButton);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}

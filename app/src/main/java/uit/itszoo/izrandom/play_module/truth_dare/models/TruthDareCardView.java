package uit.itszoo.izrandom.play_module.truth_dare.models;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import org.jetbrains.annotations.NotNull;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.flip_card.CardContentDialog;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuActivity;

public class TruthDareCardView extends CardView {
    final int MAXIMUM_CARD_COUNT = 10;
    final int MINIMUM_CARD_COUNT = 1;
    final String DEFAULT_CARD_CONTENT = "Tên của thẻ bài";

    ImageButton removeCardButton;
    ImageButton increaseButton;
    ImageButton decreaseButton;
    EditText cardLabelEditText;
    TextView cardAmountText;
    Drawable defaultBackgroundDecreaseButton;
    Drawable defaultBackgroundIncreaseButton;

    int cardCount = MINIMUM_CARD_COUNT;
    String cardContent = DEFAULT_CARD_CONTENT;
    String cardID;

    boolean isInit = false;

    public interface MyCallBackInterface {
        void editCard(TruthDareCard editedCard);

        void deleteCard(String id);
    }

    MyCallBackInterface callBack;

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
        isInit = true;
        this.cardID = card.getId();
        this.cardContent = card.getContent();
        this.cardCount = card.getCount();

        cardLabelEditText.setText(cardContent);
        cardAmountText.setText(String.valueOf(cardCount));
        isInit = false;
    }

    public void setCallBack(MyCallBackInterface callBack) {
        this.callBack = callBack;
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
        cardLabelEditText.setText(cardContent);
        cardAmountText = this.findViewById(R.id.text_card_amount);
        cardAmountText.setText(String.valueOf(cardCount));
        setListeners();
    }

    private void setListeners() {
        removeCardButton.setOnClickListener(view -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Hộp thư xác nhận")
                    .setMessage("Bạn có chắc muốn xóa thẻ \"" + cardContent + "\"?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            callBack.deleteCard(cardID);
                            Toast.makeText(getContext(), "Đã xóa thẻ", Toast.LENGTH_SHORT).show();
                        }})
                    .setNegativeButton("Hủy", null).show();
        });


        increaseButton.setOnClickListener(view -> {
            if (cardCount < MAXIMUM_CARD_COUNT) {
                cardCount++;
                cardAmountText.setText(String.valueOf(cardCount));
                callBack.editCard(getCardInfo());
            }
        });

        decreaseButton.setOnClickListener(view -> {
            if (cardCount > MINIMUM_CARD_COUNT) {
                cardCount--;
                cardAmountText.setText(String.valueOf(cardCount));
                callBack.editCard(getCardInfo());
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

        cardLabelEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    callBack.editCard(getCardInfo());
                }
            }
        });

        cardLabelEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    cardLabelEditText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    //Find the currently focused view, so we can grab the correct window token from it.

                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });
    }
}

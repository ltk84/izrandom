package uit.itszoo.izrandom.random_module.flip_card.flip_card_add;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import uit.itszoo.izrandom.R;

public class CardAddContentDialog extends Dialog {
    public EditText editTextCardContent;
    public String cardContent = "";

    public Button confirmButton;
    public Button cancelButton;

    public CardAddContentDialog(Activity activity) {
        super(activity);
    }

    public CardAddContentDialog(Activity activity, String cardContent) {
        super(activity);
        this.cardContent = cardContent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.card_view_add_content_dialog);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        this.setCanceledOnTouchOutside(true);
        this.getWindow().setAttributes(params);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initView();
        setListenerForView();

    }

    private void initView() {
        editTextCardContent = (EditText) findViewById(R.id.edit_text_add_card_content);
        confirmButton = findViewById(R.id.confirm_add_card_button);
        cancelButton = findViewById(R.id.cancel_add_card_button);

        editTextCardContent.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextCardContent.setRawInputType(InputType.TYPE_CLASS_TEXT);

        if (!cardContent.equals("")) {
            editTextCardContent.setText(cardContent);
        }

    }


    public void setListenerForView() {
//        confirmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                dismiss();
//            }
//        });

//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dismiss();
//            }
//        });

        editTextCardContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    editTextCardContent.clearFocus();
                }
                return false;
            }
        });
    }

}

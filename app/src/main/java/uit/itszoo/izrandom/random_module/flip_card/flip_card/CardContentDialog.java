package uit.itszoo.izrandom.random_module.flip_card.flip_card;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import uit.itszoo.izrandom.R;

public class CardContentDialog extends Dialog{
    public TextView tvCardContent;
    public String cardContent;

    public CardContentDialog(Activity activity, String cardContent) {
        super(activity);
        this.cardContent = cardContent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.card_view_content_dialog);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        this.setCanceledOnTouchOutside(true);
        this.getWindow().setAttributes(params);

        tvCardContent = findViewById(R.id.txt_flip_card_content);

        tvCardContent.setText(cardContent);
    }

}
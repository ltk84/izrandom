package uit.itszoo.izrandom.random_module.raffle.raffle_guide;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import uit.itszoo.izrandom.R;

public class RaffleGuideDialog extends Dialog implements View.OnClickListener {
    public Activity child;
    public Button closeButton;

    public TextView tvStart;
    public TextView tvModify;
    public TextView tvNote;

    public RaffleGuideDialog(Activity activity) {
        super(activity);
        this.child = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_raffle_guide);
        closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(this);

        tvStart = findViewById(R.id.tv_start_guide);
        tvModify = findViewById(R.id.tv_modify_guide);
        tvNote = findViewById(R.id.tv_note_guide);

        tvStart.setText(Html.fromHtml("Nhấn nút <b><font color='#" + String.format("%X", this.getContext().getColor(R.color.colorGreen)).substring(2) + "'>Bắt đầu</font></b> để thực hiện ngẫu nhiên trúng thưởng.", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        tvModify.setText(Html.fromHtml("Chạm vào 2 khung <b><font color='#" + String.format("%X", this.getContext().getColor(R.color.colorBlue)).substring(2) + "'>Người tham gia</font></b> và <b><font color='#" + String.format("%X", this.getContext().getColor(R.color.colorRed)).substring(2) + "'>Giải thưởng</font></b> để thêm người tham gia và giải thưởng.", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        tvNote.setText(Html.fromHtml("<b>Lưu ý:</b> Hãy đảm bảo số người tham gia lớn hơn hoặc bằng số giải thưởng để bắt đầu.", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}

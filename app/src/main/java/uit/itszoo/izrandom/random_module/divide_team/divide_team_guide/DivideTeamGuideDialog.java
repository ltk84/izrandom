package uit.itszoo.izrandom.random_module.divide_team.divide_team_guide;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import uit.itszoo.izrandom.R;

public class DivideTeamGuideDialog extends Dialog implements View.OnClickListener {
    public Activity child;
    public Button closeButton;

    public TextView tvStart;
    public TextView tvModify;
    public TextView tvNote1;
    public TextView tvNote2;

    public DivideTeamGuideDialog(Activity activity) {
        super(activity);
        this.child = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_divide_team_guide);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        this.setCanceledOnTouchOutside(true);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        this.getWindow().setAttributes(params);

        closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(this);

        tvModify = findViewById(R.id.tv_modify_guide);
        tvStart = findViewById(R.id.tv_start_guide);
        tvNote1 = findViewById(R.id.tv_note_guide_1);
        tvNote2 = findViewById(R.id.tv_note_guide_2);

        tvStart.setText(Html.fromHtml("Nhấn nút <b><font color='#" + String.format("%X", this.getContext().getColor(R.color.colorGreen)).substring(2) + "'>Bắt đầu</font></b> để thực hiện tạo kết quả.", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        tvModify.setText(Html.fromHtml("Chạm vào khung <b><font color='#" + String.format("%X", this.getContext().getColor(R.color.colorBlue)).substring(2) + "'>Người tham gia</font></b> để thêm người vào chia đội nhóm.", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        tvNote1.setText(Html.fromHtml("<b>Lưu ý:</b> Hãy đảm bảo số người lớn hơn hoặc bằng số người mỗi nhóm (số nhóm, số người mỗi nhóm phải lớn hơn 0) để tạo kết quả.", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
        tvNote2.setText(Html.fromHtml("<b>Ghi chú:</b> Kết quả được tạo ra dựa trên tiêu chí đảm bảo sự chênh lệch số thành viên của mỗi đội là nhỏ nhất. Cho nên kết quả đôi khi sẽ không hoàn toàn trùng với ràng buộc về số nhóm hoặc số người mỗi nhóm.", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}

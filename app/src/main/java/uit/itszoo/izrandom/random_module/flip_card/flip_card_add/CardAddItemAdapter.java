package uit.itszoo.izrandom.random_module.flip_card.flip_card_add;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

import uit.itszoo.izrandom.R;

public class CardAddItemAdapter extends BaseAdapter {
    Context context;
    Activity activity;
    private ArrayList<String> cardContextArrayList;
    private LayoutInflater mLayoutInflater;
    private OnCardItemClick mCallBack;

    public CardAddItemAdapter(@NonNull Context context, Activity activity, ArrayList<String> cardContentArrayList, OnCardItemClick mCallBack) {
        this.context = context;
        this.activity = activity;
        this.cardContextArrayList = cardContentArrayList;
        this.mCallBack = mCallBack;
    }

    @Override
    public int getCount() {
        // quy định số lượng hiển thị
        return cardContextArrayList == null ? 0 : cardContextArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardContextArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String cardContent = cardContextArrayList.get(position);


        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView =
                    mLayoutInflater.inflate(R.layout.card_view_custom_grid_item, parent, false);
        }

        CardView cardView;
        TextView textViewCardContent;
        ImageButton deleteButton;

        cardView = convertView.findViewById(R.id.card_view_custom_grid_item);
        textViewCardContent = convertView.findViewById(R.id.tvCardViewAddContent);
        deleteButton = convertView.findViewById(R.id.btn_delete_card_view_add_item);


        textViewCardContent.setText(cardContent);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardAddContentDialog cardAddContentDialog = new CardAddContentDialog(activity, cardContent);
                cardAddContentDialog.show();

                cardAddContentDialog.confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newCardContent = cardAddContentDialog.editTextCardContent.getText().toString();
                        if (newCardContent == null || newCardContent.equals("")) {
                            Toast.makeText(context,
                                    "Vui lòng nhập nội dung thẻ bài", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mCallBack.onClickConfirmButton(position, newCardContent);
                            cardAddContentDialog.dismiss();
                        }
                    }
                });

                cardAddContentDialog.cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cardAddContentDialog.dismiss();
                    }
                });
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Hộp thư xác nhận")
                        .setMessage("Bạn có chắc muốn xóa thẻ bài \"" + cardContent + "\"?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                mCallBack.onClickDeleteButton(position);
                                Toast.makeText(context, "Đã xóa thẻ bài", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton("Hủy", null).show();
            }
        });

        return convertView;
    }
}

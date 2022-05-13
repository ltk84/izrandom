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
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

public class CardAddItemAdapter extends BaseAdapter {
    Context context;
    Activity activity;
    private List<CardModel> listCardModels;
    private LayoutInflater mLayoutInflater;
    private OnCardItemClick mCallBack;

    public CardAddItemAdapter(@NonNull Context context, Activity activity, List<CardModel> listCardModels, OnCardItemClick mCallBack) {
        this.context = context;
        this.activity = activity;
        this.listCardModels = listCardModels;
        this.mCallBack = mCallBack;
    }

    @Override
    public int getCount() {
        // quy định số lượng hiển thị
        return listCardModels == null ? 0 : listCardModels.size();
    }

    @Override
    public Object getItem(int position) {
        return listCardModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String cardContent = listCardModels.get(position).getCardContent();


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

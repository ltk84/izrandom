package uit.itszoo.izrandom.random_module.flip_card.flip_card;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

import uit.itszoo.izrandom.R;

public class CardItemAdapter extends BaseAdapter {
    Context context;
    Activity activity;
    private ArrayList<String> cardContextArrayList;
    private LayoutInflater mLayoutInflater;
    private int cardWidth;
    private float textSize;

    public CardItemAdapter(@NonNull Context context, Activity activity, ArrayList<String> cardContentArrayList, int columnWidth, float textSize) {
        this.context = context;
        this.activity = activity;
        this.cardContextArrayList = cardContentArrayList;
        this.cardWidth = columnWidth;
        this.textSize = textSize;
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
                    mLayoutInflater.inflate(R.layout.card_view_grid_item, parent, false);
        }

        EasyFlipView easyFlipView;
        TextView textViewCardContent;
        EasyFlipView flip_card_cardView;

        easyFlipView = (EasyFlipView) convertView.findViewById(R.id.flip_card_item_cardview);
        flip_card_cardView = convertView.findViewById(R.id.flip_card_item_cardview);
        textViewCardContent = convertView.findViewById(R.id.txt_card_item);

        easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView flipView, EasyFlipView.FlipState newCurrentSide)
            {
                if (easyFlipView.getCurrentFlipState().toString().equals("BACK_SIDE")) {
                    CardContentDialog cardContentDialog = new CardContentDialog(activity, cardContent);
                    cardContentDialog.show();
                }
            }
        });

        // for some reason, the setLayoutParams does not show view in the right size as dp so I have to multiply by 2.5
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) Math.round(cardWidth * 0.1 * 2.5),(int) Math.round(cardWidth * 0.1 * 2.5),(int) Math.round(cardWidth * 0.1 * 2.5),(int) Math.round(cardWidth * 0.1 * 2.5));
        textViewCardContent.setLayoutParams(params);
        textViewCardContent.setTextSize(textSize);

        flip_card_cardView.setLayoutParams(new CardView.LayoutParams((int) Math.round(cardWidth * 2.5), (int) Math.round(cardWidth / 13.0 * 17.0 * 2.5)));
        textViewCardContent.setText(cardContent);

        return convertView;
    }

}

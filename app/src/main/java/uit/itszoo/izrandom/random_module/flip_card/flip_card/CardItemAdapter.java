package uit.itszoo.izrandom.random_module.flip_card.flip_card;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

public class CardItemAdapter extends BaseAdapter {
    public static boolean isCardFlipping = false;

    Context context;
    Activity activity;
    private List<CardModel> listCardModels;
    private LayoutInflater mLayoutInflater;
    private int cardWidth;
    private float textSize;

    public CardItemAdapter(@NonNull Context context, Activity activity, List<CardModel> listCardModels, int columnWidth, float textSize) {
        this.context = context;
        this.activity = activity;
        this.listCardModels = listCardModels;
        this.cardWidth = columnWidth;
        this.textSize = textSize;
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

//        if (convertView == null) {
//            convertView =
//                    mLayoutInflater.inflate(R.layout.card_view_grid_item, parent, false);
////            WindowManager.LayoutParams params = activity.getWindow().getAttributes();
////            convertView.setLayoutParams(params);
//        }
        convertView =
                mLayoutInflater.inflate(R.layout.card_view_grid_item, parent, false);

        convertView.setBackgroundResource(0);

        EasyFlipView easyFlipView;
        TextView textViewCardContent;

        easyFlipView = (EasyFlipView) convertView.findViewById(R.id.flip_card_item_cardview);
        textViewCardContent = convertView.findViewById(R.id.txt_card_item);

        easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                if (easyFlipView.getCurrentFlipState().toString().equals("BACK_SIDE")) {
                    CardContentDialog cardContentDialog = new CardContentDialog(activity, cardContent);
                    cardContentDialog.show();
                    cardContentDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            isCardFlipping = false;
                        }
                    });
                }
                else if (easyFlipView.getCurrentFlipState().toString().equals("FRONT_SIDE")) {
                    isCardFlipping = false;
                }
            }
        });

        easyFlipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCardFlipping) {
                    isCardFlipping = true;
                    easyFlipView.flipTheView();
                }
            }
        });

        easyFlipView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (easyFlipView.getCurrentFlipState().toString().equals("BACK_SIDE")) {
                    if (!isCardFlipping) {
                        isCardFlipping = true;
                        CardContentDialog cardContentDialog = new CardContentDialog(activity, cardContent);
                        cardContentDialog.show();
                        cardContentDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                isCardFlipping = false;
                            }
                        });
                        return true;
                    }
                }
                else if (easyFlipView.getCurrentFlipState().toString().equals("FRONT_SIDE")) {
                    easyFlipView.performClick();
                    return true;
                }
                return false;
            }
        });

        final float scale = context.getResources().getDisplayMetrics().density;
        int pixels = (int) (cardWidth * scale + 0.5f);

        Log.i("CardItemAdapter", String.valueOf(cardWidth) + "dp to pixels: " + String.valueOf(pixels));

        // for some reason, the setLayoutParams does not show view in the right size as dp so I have to multiply by 2.5
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) Math.round(pixels * 0.1),(int) Math.round(pixels * 0.1),(int) Math.round(pixels * 0.1),(int) Math.round(pixels * 0.1));
        textViewCardContent.setLayoutParams(params);
        textViewCardContent.setTextSize(textSize);

        easyFlipView.setLayoutParams(new CardView.LayoutParams(pixels, (int) Math.round(pixels / 13.0 * 17.0)));
        textViewCardContent.setText(cardContent);

        return convertView;
    }

}

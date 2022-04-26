package uit.itszoo.izrandom.random_module.flip_card.flip_card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uit.itszoo.izrandom.R;

public class CardItemAdapter extends BaseAdapter {
    Context context;
    private ArrayList<String> cardContextArrayList;
    private LayoutInflater mLayoutInflater;

    public CardItemAdapter(@NonNull Context context, ArrayList<String> cardContentArrayList) {
        this.context = context;
        this.cardContextArrayList = cardContentArrayList;
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
        return Integer.parseInt(cardContextArrayList.get(position));
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

        TextView textViewCardContent;

        textViewCardContent = convertView.findViewById(R.id.txt_card_item);


        textViewCardContent.setText(cardContent);

        return convertView;



//        String cardContent = cardContextArrayList.get(position);
//        TextView textViewCardContent;
//        if (convertView == null) {
//            convertView =
//                    LayoutInflater.from(context).inflate(R.layout.card_view_grid_item, parent, false);
//            textViewCardContent = (TextView) convertView.findViewById(R.id.txt_card_item);
//        } else {
//            textViewCardContent = (TextView) convertView;
//        }
//        textViewCardContent.setText(cardContent);
//        return textViewCardContent;
    }


//    public CardItemAdapter(@NonNull Context context, ArrayList<String> courseModelArrayList) {
//        super(context, 0, courseModelArrayList);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View listItemView = convertView;
//        if (listItemView == null) {
//            // Layout Inflater inflates each item to be displayed in GridView.
//            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.card_view_grid_item, parent, false);
//        }
//        String cardContent = getItem(position);
//        TextView textViewCardContent = listItemView.findViewById(R.id.txt);
//        textViewCardContent.setText(cardContent);
//        return listItemView;
//    }
}

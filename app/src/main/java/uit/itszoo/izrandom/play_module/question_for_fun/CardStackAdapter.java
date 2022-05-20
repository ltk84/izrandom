package uit.itszoo.izrandom.play_module.question_for_fun;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCardContent;
        public EasyFlipView easyFlipView;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCardContent = itemView.findViewById(R.id.tv_card_stack_item_back);
            easyFlipView = itemView.findViewById(R.id.card_stack_item);

        }

    }

    Context context;
    private List<String> listCardContents;
    private LayoutInflater mLayoutInflater;
//    public List<Integer> listIdEasyFlipView = new ArrayList<>();

    public CardStackAdapter(@NonNull Context context, List<String> listCardContents) {
        this.context = context;
        this.listCardContents = listCardContents;

        Log.i("CardStackAdapter", "listCardContents size: " + String.valueOf(listCardContents.size()));

//        for (int i = 0; i < listCardContents.size(); i++) {
//            Integer id = View.generateViewId();
//            listIdEasyFlipView.add(id);
//        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_stack_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String cardContent = listCardContents.get(position);
        holder.tvCardContent.setText(cardContent);

//        holder.easyFlipView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("CardStackAdapter", "viewHolder easyFlipView onClick called");
//            }
//        });

        holder.easyFlipView.setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public void onClick() {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                holder.easyFlipView.flipTheView();
            }

            @Override
            public void onSwipeTop() {
                Toast.makeText(context, "top", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight() {
                Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeLeft() {
                Toast.makeText(context, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeBottom() {
                Toast.makeText(context, "bottom", Toast.LENGTH_SHORT).show();
            }
        });

//        holder.easyFlipView.setId(listIdEasyFlipView.get(position));



    }

    @Override
    public int getItemCount() {
        return listCardContents == null ? 0 : listCardContents.size();
    }








//    @Override
//    public int getCount() {
//        // quy định số lượng hiển thị
//        return listCardContents == null ? 0 : listCardContents.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return listCardContents.get(position);
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        String cardContent = listCardContents.get(position);
//
//        if (mLayoutInflater == null) {
//            mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        convertView =
//                mLayoutInflater.inflate(R.layout.card_stack_item, parent, false);
//
////        EasyFlipView easyFlipView;
//        TextView textView;
//
////        easyFlipView = convertView.findViewById(R.id.card_stack_item);
//        textView = convertView.findViewById(R.id.tv_card_stack_item_back);
//
//        textView.setText(listCardContents.get(position).toString());
//
//        return convertView;
//
//    }
}

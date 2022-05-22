package uit.itszoo.izrandom.play_module.question_for_fun;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

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

    boolean isToFlipCard = false;

    public CardStackAdapter(@NonNull Context context, List<String> listCardContents, boolean isToFlipCard) {
        this.context = context;
        this.listCardContents = listCardContents;
        this.isToFlipCard = isToFlipCard;

        Log.i("CardStackAdapter", "listCardContents size: " + String.valueOf(listCardContents.size()));

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

        if (listCardContents.size() == 3) {
            if (position == 1 && isToFlipCard) {
                holder.easyFlipView.flipTheView();
            }
        }
        else if (listCardContents.size() == 2) {
            if (position == 0 && isToFlipCard) {
                holder.easyFlipView.flipTheView();
            }
        }


    }

    @Override
    public int getItemCount() {
        return listCardContents == null ? 0 : listCardContents.size();
    }
}

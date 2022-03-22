package uit.itszoo.izrandom.random_module.random_integer;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
    private List<Integer> listNum = new ArrayList<Integer>();
    private static float textSize = 1000;
    private final int[] listColor = {
            R.color.colorPrimary,
            R.color.colorRed,
            R.color.colorGreenDark,
            R.color.colorSecondary,
    };
    public  RecyclerviewAdapter(List<Integer> listNum)
    {
        this.listNum = listNum;
    }
    public void setList(List<Integer> listNum)
    {
        this.listNum = listNum;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_view_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(listNum.get(position).toString());
        holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), listColor[position]));
        float size = holder.textView.getTextSize();
    }

    @Override
    public int getItemCount() {
        return listNum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.number_view_card);
        }
    }
}

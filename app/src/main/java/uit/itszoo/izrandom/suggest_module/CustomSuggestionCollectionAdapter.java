package uit.itszoo.izrandom.suggest_module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.suggest_module.models.SuggestionCollection;
import uit.itszoo.izrandom.suggest_module.source.SuggestionSource;

public class CustomSuggestionCollectionAdapter extends ArrayAdapter {
    private ArrayList<SuggestionCollection> suggestionCollectionList = new ArrayList<>();
    private Context mContext;
    public CustomSuggestionCollectionAdapter(@NonNull Context context, ArrayList<SuggestionCollection> suggestionCollectionList) {
        super(context, R.layout.item_drop_menu);
        this.suggestionCollectionList.addAll(suggestionCollectionList);
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return suggestionCollectionList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_drop_menu, parent, false);
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.image.setImageResource(suggestionCollectionList.get(position).icon);
        mViewHolder.title.setText(suggestionCollectionList.get(position).title);
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position,convertView,parent);
    }

    private static class ViewHolder {
        ImageView image;
        TextView title;
    }
}

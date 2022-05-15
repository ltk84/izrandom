package uit.itszoo.izrandom.play_module.truth_dare.models;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import org.jetbrains.annotations.NotNull;

import uit.itszoo.izrandom.R;

public class TruthDareCardView extends CardView {

    public TruthDareCardView(@NonNull @NotNull Context context) {
        super(context);
        initView();
    }

    public TruthDareCardView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TruthDareCardView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.truth_dare_card_view, this);
    }
}

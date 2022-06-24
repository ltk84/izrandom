package uit.itszoo.izrandom.utilities;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import uit.itszoo.izrandom.R;

public class InputFilterMinMax implements InputFilter {

    private int min, max;
    private TextView textView;

    public InputFilterMinMax(int min, int max, TextView view) {
        this.min = min;
        this.max = max;
        textView = view;
    }

    public InputFilterMinMax(String min, String max, TextView view) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);;
        textView = view;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
            else
                handleOutOfRange(textView);
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

    private void handleOutOfRange(TextView view) {
        view.setText(R.string.count_hint);
        view.setVisibility(View.VISIBLE);
        view.postDelayed(() -> view.setVisibility(View.INVISIBLE), 5000);
    }
}

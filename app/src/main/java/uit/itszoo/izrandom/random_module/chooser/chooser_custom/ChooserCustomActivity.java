package uit.itszoo.izrandom.random_module.chooser.chooser_custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.util.ArrayList;
import java.util.Collections;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.chooser.ChooserActivity;
import uit.itszoo.izrandom.random_module.chooser.model.ChooserTheme;
import uit.itszoo.izrandom.random_module.chooser.source.ChooserSource;

public class ChooserCustomActivity extends AppCompatActivity {
    public static String SELECTED_THEME = "SELECTED_THEME";

    private ArrayList<ChooserTheme> themes = (ArrayList<ChooserTheme>) ChooserSource.themes.clone();
    CarouselView carouselView;
    RelativeLayout chooserHolderLayout;
    ViewGroup layout;
    ImageButton backButton;
    ImageButton confirmButton;

    int screenWidth;
    int screenHeight;

    ChooserTheme initialTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser_custom);
        initialTheme = (ChooserTheme) getIntent().getSerializableExtra(ChooserActivity.CURRENT_THEME);
        swapInitialThemeToLead();
        initView();
        setupCarouselView();
    }

    void swapInitialThemeToLead() {
        int initialThemeIndex = themes.indexOf(initialTheme);
        Collections.swap(themes, 0, initialThemeIndex);
    }

    private void initView() {
        layout = findViewById(R.id.layout_custom_chooser);
        backButton = findViewById(R.id.bb_chooser_custom);
        confirmButton = findViewById(R.id.confirm_button_chooser);

        backButton.setOnClickListener(view -> onBackPressed());
        confirmButton.setOnClickListener(view -> {
            Intent intentBack = new Intent();
            intentBack.putExtra(ChooserCustomActivity.SELECTED_THEME, themes.get(carouselView.getCurrentItem()));
            setResult(Activity.RESULT_OK, intentBack);
            finish();
        });


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    private void setupCarouselView() {
        carouselView = findViewById(R.id.carouselView);

        carouselView.setSize(themes.size());
        carouselView.setResource(R.layout.relative_layout_chooser);
        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                chooserHolderLayout = view.findViewById(R.id.chooser_holder_layout);
                chooserHolderLayout.setBackground(getDrawable(themes.get(position).getThemeValue()));

                chooserHolderLayout.getLayoutParams().width = screenWidth;
                chooserHolderLayout.getLayoutParams().height = screenHeight;
            }
        });

        carouselView.show();
    }
}
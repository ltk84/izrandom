package uit.itszoo.izrandom.random_module.chooser.chooser_custom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jama.carouselview.CarouselScrollListener;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.chooser.source.ChooserSource;
import uit.itszoo.izrandom.random_module.flip_coin.FlipCoinActivity;
import uit.itszoo.izrandom.random_module.flip_coin.flip_coin_custom.FlipCoinCustomActivity;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;
import uit.itszoo.izrandom.random_module.flip_coin.source.CoinSource;
import uit.itszoo.izrandom.random_module.random_direction.model.Arrow;
import uit.itszoo.izrandom.random_module.random_direction.source.ArrowSource;

public class ChooserCustomActivity extends AppCompatActivity {
    private ArrayList<Integer> themes = (ArrayList<Integer>) ChooserSource.themes.clone();
    CarouselView carouselView;
    RelativeLayout chooserHolderLayout;
    ViewGroup layout;

    int screenWidth;
    int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser_custom);
        initView();
        setupCarouselView();
    }
    private void initView() {
        layout = findViewById(R.id.layout_custom_chooser);

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
                chooserHolderLayout.setBackground(getDrawable(themes.get(position)));

                chooserHolderLayout.getLayoutParams().width = screenWidth;
                chooserHolderLayout.getLayoutParams().height = screenHeight;
            }
        });

        carouselView.show();
    }
}
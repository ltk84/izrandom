package uit.itszoo.izrandom.random_module.flip_card.flip_card;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.jama.carouselview.CarouselViewListener;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuContract;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuPresenter;

public class FlipCardActivity extends AppCompatActivity implements FlipCardContract.View {

    FlipCardContract.Presenter presenter;
    ImageButton backButton;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_card);

        initView();
        setListenerForView();
        setUpGridView();

        presenter = new FlipCardPresenter(getApplicationContext(), this);
        setPresenter(presenter);

    }

    private void initView() {
        backButton = findViewById(R.id.bb_flip_card);
        gridView = findViewById(R.id.flip_card_grid_view);
    }


    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void setPresenter(FlipCardContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void setUpGridView() {
//        String[] listString = new String[10];
//
//        for (int i = 0; i < 10; i++) {
//            listString[i] = "card " + i;
//        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.card_view_grid_item, listString);
//        gridView.setAdapter(adapter);

    }

}

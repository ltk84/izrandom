package uit.itszoo.izrandom.random_module.flip_card;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import uit.itszoo.izrandom.R;

public class FlipCardActivity extends AppCompatActivity implements FlipCardContract.View{

    FlipCardContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_card);

        initView();
        setListenerForView();

        presenter = new FlipCardPresenter(getApplicationContext(), this);
        setPresenter(presenter);

    }

    private void initView() {

    }


    public void setListenerForView() {
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//        toCustomScreenButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intentToCustom = new Intent(getApplicationContext(), RandomDirectionCustomActivity.class);
//                intentToCustom.putExtra(RandomDirectionActivity.CURRENT_ARROW, presenter.getCurrentArrow());
//                intentLauncher.launch(intentToCustom);
//            }
//        });

    }

    @Override
    public void setPresenter(FlipCardContract.Presenter presenter) {
        this.presenter = presenter;
    }
}

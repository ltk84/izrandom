package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;
import com.jama.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.LuckyWheelActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.adapter.SliceToWheelItem;
import uit.itszoo.izrandom.random_module.lucky_wheel.add_lucky_wheel.AddNewLuckyWheelActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.edit_lucky_wheel.EditLuckyWheelActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class LuckyWheelCustomActivity extends AppCompatActivity implements LuckyWheelCustomContract.View {
    public static final String CURRENT_WHEEL = "CURRENT_WHEEL";

    LuckyWheelCustomContract.Presenter presenter;

    LuckyWheel luckyWheel;
    List<LuckyWheelData> wheelList;
    String currentWheelID;

    CarouselView carouselView;
    ImageButton backButton;
    ImageButton okeButton;
    TextView textView;
    ImageButton addButton;
    Button editButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_custom);

        presenter = new LuckyWheelCustomPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        currentWheelID = getIntent().getStringExtra(LuckyWheelActivity.CURRENT_WHEEL);

        presenter.getAllWheelList().observe(this, luckyWheelData -> {
            wheelList = luckyWheelData;
            setupCarouselView();
        });

        presenter.getAllSliceList().observe(this, luckyWheelSlices -> {
            setupCarouselView();
        });

        initView();
        setListenerForView();
    }

    private void swapInitialWheelToLead() {
        int initialWheelIndex = 0;
        for (LuckyWheelData wheelData : wheelList) {
            if (wheelData.getId().equals(currentWheelID)) {
                initialWheelIndex = wheelList.indexOf(wheelData);
                break;
            }
        }
        Collections.swap(wheelList, 0, initialWheelIndex);
    }

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
            });

    void initView() {
        backButton = findViewById(R.id.bb_rand_wheel);
        editButton = findViewById(R.id.bt_edit);
        addButton = findViewById(R.id.add_button_wheel);
        okeButton = findViewById(R.id.oke_button);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    void setListenerForView() {
        backButton.setOnClickListener(
                view -> {
                    onBackPressed();
                }
        );

        editButton.setOnClickListener(view -> {
            Intent intentToEdit = new Intent(getApplicationContext(), EditLuckyWheelActivity.class);
            intentToEdit.putExtra(LuckyWheelCustomActivity.CURRENT_WHEEL, presenter.getAllWheelList().getValue().get(carouselView.getCurrentItem()));
            intentLauncher.launch(intentToEdit);
        });

        addButton.setOnClickListener(
                view -> {
                    Intent intentToCustom = new Intent(getApplicationContext(), AddNewLuckyWheelActivity.class);
                    intentLauncher.launch(intentToCustom);
                }
        );

        okeButton.setOnClickListener(
                view -> {
                    int currentIndex = carouselView.getCurrentItem();
                    LuckyWheelData currentWheel = wheelList.get(currentIndex);
                    presenter.changeCurrentWheel(currentWheel.getId());
                    finish();
                }
        );
    }

    private void setupCarouselView() {
        swapInitialWheelToLead();

        carouselView = findViewById(R.id.carouselView);
        carouselView.setSize(wheelList.size());
        carouselView.setResource(R.layout.lucky_wheel_carousel);

        carouselView.setCarouselViewListener((view, position) -> {
            // Example here is setting up a full image carousel
            textView = view.findViewById(R.id.textTitle);
            textView.setText(wheelList.get(position).getTitle());
            luckyWheel = view.findViewById(R.id.wheel_carousel);
            luckyWheel.setTarget(1);

            LuckyWheelData currentWheel = wheelList.get(position);

            luckyWheel.setTextSize(currentWheel.getTextSize());
            int repeat = currentWheel.getSliceRepeat();
            List<LuckyWheelSlice> slicesOfWheel = presenter.getSlicesByWheelID(currentWheel.getId());

            ArrayList<LuckyWheelSlice> slicesOfWheelRepeatTime = new ArrayList<>();
            for (int i = 0; i < repeat; i++) {
                slicesOfWheelRepeatTime.addAll(slicesOfWheel);
            }

            ArrayList<WheelItem> wheelItems = SliceToWheelItem.convertSlicesToWheelItems(
                    getResources(), slicesOfWheelRepeatTime);
            luckyWheel.addWheelItems(wheelItems);
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();
    }

    @Override
    public void setPresenter(LuckyWheelCustomContract.Presenter presenter) {
        this.presenter = presenter;
    }
}

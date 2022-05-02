package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
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
import com.jama.carouselview.CarouselViewListener;

import java.util.ArrayList;
import java.util.Collections;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.LuckyWheelActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.adapter.SliceToWheelItem;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;

public class LuckyWheelCustomActivity extends AppCompatActivity {
    public static final String FAIR_MODE = "FAIR_MODE";
    public static final String SPIN_TIME = "SPIN_TIME";
    public static final String TEXT_SIZE = "TEXT_SIZE";
    public static final String SLICE_REPEAT = "SLICE_REPEAT";
    public static final String CURRENT_WHEEL = "CURRENT_WHEEL";
    public static String SELECTED_WHEEL = "SELECTED_WHEEL";

    LuckyWheel luckyWheel;
    ArrayList<LuckyWheelData> wheelList;
    String currentWheelID;

//    ArrayList<ArrayList<String>> listMixedContent = LuckyWheelSource.mixedContentItem;
//    ArrayList<ArrayList<String>> listContent = LuckyWheelSource.listContent;
//    ArrayList<String> listTitle = new ArrayList<String>();
//    List<List<WheelItem>> listWheelItems = new ArrayList<>();

    CarouselView carouselView;
    ImageButton backButton;
    ImageButton okeButton;
    TextView textView;
    ImageButton addButton;
    Button editButton;
//    int indexWheelInList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_custom);
        wheelList = (ArrayList<LuckyWheelData>) LuckyWheelSource.luckyWheelList.clone();
        initView();
        setupCarouselView();
        currentWheelID = getIntent().getStringExtra(LuckyWheelActivity.CURRENT_WHEEL);
        swapInitialWheelToLead();
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
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    try {
                        recreate();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }

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
            Intent intentToCustom = new Intent(getApplicationContext(), EditLuckyWheelActivity.class);
            intentToCustom.putExtra(LuckyWheelCustomActivity.CURRENT_WHEEL, carouselView.getCurrentItem());
            intentLauncher.launch(intentToCustom);
        });

        addButton.setOnClickListener(
                view -> {
                    Intent intentToCustom = new Intent(getApplicationContext(), AddNewLuckyWheelActivity.class);
                    intentLauncher.launch(intentToCustom);
                }
        );

        okeButton.setOnClickListener(
                view -> {
                    Intent intentBack = new Intent();
                    // tìm lucky wheel data từ trong db voi index rồi trả về cho screen chính
                    // tạm thời tìm lucky wheel data từ trong list source
                    int currentIndex = carouselView.getCurrentItem();
                    LuckyWheelData currentWheel = wheelList.get(currentIndex);
                    intentBack.putExtra(LuckyWheelCustomActivity.SELECTED_WHEEL, currentWheel);
                    setResult(Activity.RESULT_OK, intentBack);
                    finish();
                }
        );
    }

    private void setupCarouselView() {
        carouselView = findViewById(R.id.carouselView);
        carouselView.setSize(wheelList.size());
        carouselView.setResource(R.layout.lucky_wheel_carousel);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                textView = view.findViewById(R.id.textTitle);
                textView.setText(wheelList.get(position).getTitle());
                luckyWheel = view.findViewById(R.id.wheel_carousel);
                luckyWheel.setTarget(1);

                ArrayList<WheelItem> wheelItems = SliceToWheelItem.convertSlicesToWheelItems(
                        getResources(), wheelList.get(position).getSlicesWithRepeat());
                luckyWheel.addWheelItems(wheelItems);
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();
    }

}

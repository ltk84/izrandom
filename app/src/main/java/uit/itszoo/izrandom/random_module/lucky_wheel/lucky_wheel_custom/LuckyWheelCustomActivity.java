package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;
import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.LuckyWheelActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;
import uit.itszoo.izrandom.random_module.random_direction.random_direction_custom.RandomDirectionCustomActivity;

public class LuckyWheelCustomActivity extends AppCompatActivity {
    public static final String FAIR_MODE = "FAIR_MODE";
    public static final String SPIN_TIME = "SPIN_TIME";
    public static final String TEXT_SIZE = "TEXT_SIZE";
    public static final String SLICE_REPEAT = "SLICE_REPEAT";
    public static final String CURRENT_WHEEL = "CURRENT_WHEEL";
    public static String SELECTED_WHEEL = "SELECTED_WHEEL";
    LuckyWheel luckyWheel;
    ArrayList<ArrayList<String>> listContent = LuckyWheelSource.listContent;
    ArrayList<String> listTitle = new  ArrayList<String>();
    List<List<WheelItem>> listWheelItems = new ArrayList<>();
    CarouselView carouselView;
    ImageButton backButton;
    ImageButton okeButton;
    TextView textView;
    ImageButton addButton;
    Button editButton;
    int indexWheelInList;
    int textSize;
    int repeate;
    int spinTime;
    boolean fairMode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_custom);
        indexWheelInList = (int) getIntent().getSerializableExtra(LuckyWheelActivity.CURRENT_WHEEL);
        generateWheelItems();
        initView();
        setupCarouselView();
        setListener();
    }
    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                    }
                }
            });
    void initView()
    {
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

    void setListener()
    {
        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                }
        );
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCustom = new Intent(getApplicationContext(), EditLuckyWheelActivity.class);
                intentToCustom.putExtra(LuckyWheelCustomActivity.CURRENT_WHEEL, indexWheelInList);
                intentToCustom.putExtra(LuckyWheelCustomActivity.FAIR_MODE, fairMode);
                intentToCustom.putExtra(LuckyWheelCustomActivity.SPIN_TIME, spinTime);
                intentToCustom.putExtra(LuckyWheelCustomActivity.TEXT_SIZE, textSize);
                intentToCustom.putExtra(LuckyWheelCustomActivity.SLICE_REPEAT, repeate);
                intentLauncher.launch(intentToCustom);
            }
        });
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentToCustom = new Intent(getApplicationContext(), AddNewLuckyWheelActivity.class);
                        intentLauncher.launch(intentToCustom);
                    }
                }
        );
        okeButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentBack = new Intent();
                        intentBack.putExtra(LuckyWheelCustomActivity.SELECTED_WHEEL, carouselView.getCurrentItem());
                        setResult(Activity.RESULT_OK, intentBack);
                        finish();
                    }
                }
        );
    }
    private void generateWheelItems() {
        listTitle = LuckyWheelSource.listTitle;
        for(int i = 0 ; i < listContent.size(); i++)
        {
            List<WheelItem>  wheelItems = new ArrayList<>();
            List<String> content= new ArrayList<>();
            List<String> color= new ArrayList<>();
            content = LuckyWheelSource.listContent.get(i);
            color = LuckyWheelSource.listColor.get(i);
            for(int j = 0 ; j < content.size();j++)
            {
                wheelItems.add(new WheelItem(Color.parseColor(color.get(j)), BitmapFactory.decodeResource(getResources(),
                        R.drawable.small_nails_icons) , content.get(j)));
            }

            listWheelItems.add(wheelItems);
        }
    }
    private void setupCarouselView() {
        carouselView = findViewById(R.id.carouselView);
        //textView = findViewById(R.id.textTitle);

        carouselView.setSize(listTitle.size());
        carouselView.setResource(R.layout.lucky_wheel_carousel);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
               // textView.setText(LuckyWheelSource.listTitle.get(position));
                luckyWheel = view.findViewById(R.id.wheel_carousel);
                luckyWheel.setTarget(1);
                luckyWheel.addWheelItems(listWheelItems.get(position));
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();
    }

}

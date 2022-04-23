package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.LuckyWheelActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;

public class LuckyWheelCustomActivity extends AppCompatActivity {
    public static final String FAIR_MODE = "FAIR_MODE";
    public static final String SPIN_TIME = "SPIN_TIME";
    public static final String TEXT_SIZE = "TEXT_SIZE";
    public static final String SLICE_REPEAT = "SLICE_REPEAT";
    public static final String CURRENT_WHEEL = "CURRENT_WHEEL";
    public static String SELECTED_WHEEL = "SELECTED_WHEEL";
    LuckyWheel luckyWheel;
    ArrayList<ArrayList<String>> listMixedContent = LuckyWheelSource.mixedContentItem;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_custom);
        indexWheelInList = (int) getIntent().getSerializableExtra(LuckyWheelActivity.CURRENT_WHEEL);
        initView();
        setupCarouselView();
        carouselView.setCurrentItem(indexWheelInList);
        setListener();
    }
    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result){
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        try{
                            recreate();
                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
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
                        Intent intentBack = new Intent();
                        intentBack.putExtra(LuckyWheelCustomActivity.SELECTED_WHEEL,indexWheelInList);
                        setResult(Activity.RESULT_OK, intentBack);
                        finish();
                    }
                }
        );
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToCustom = new Intent(getApplicationContext(), EditLuckyWheelActivity.class);
                intentToCustom.putExtra(LuckyWheelCustomActivity.CURRENT_WHEEL, carouselView.getCurrentItem());
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
        for(int i = 0; i < listMixedContent.size(); i++)
        {
            List<WheelItem>  wheelItems = new ArrayList<>();
            List<String> content= new ArrayList<>();
            List<String> color= new ArrayList<>();
            content = listMixedContent.get(i);
            color = LuckyWheelSource.listColor.get(i);
            for(int j = 0 ; j < content.size();j++)
            {
                int indexColor = listContent.get(i).indexOf(content.get(j));
                wheelItems.add(new WheelItem(Color.parseColor(color.get(indexColor)), BitmapFactory.decodeResource(getResources(),
                        R.drawable.small_nails_icons) , content.get(j)));
            }
            listWheelItems.add(wheelItems);
        }
    }
    private void setupCarouselView() {
        generateWheelItems();
        carouselView = findViewById(R.id.carouselView);

        carouselView.setSize(listTitle.size());
        carouselView.setResource(R.layout.lucky_wheel_carousel);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                textView = view.findViewById(R.id.textTitle);
                textView.setText(LuckyWheelSource.listTitle.get(position));
                luckyWheel = view.findViewById(R.id.wheel_carousel);
                luckyWheel.setTarget(1);
                luckyWheel.addWheelItems(listWheelItems.get(position));
            }
        });

        // After you finish setting up, show the CarouselView
        carouselView.show();
    }

}

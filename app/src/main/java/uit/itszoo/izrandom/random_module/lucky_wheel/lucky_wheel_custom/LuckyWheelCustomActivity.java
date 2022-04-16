package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.LuckyWheelActivity;

public class LuckyWheelCustomActivity extends AppCompatActivity {
    public static final String FAIR_MODE = "FAIR_MODE";
    public static final String SPIN_TIME = "SPIN_TIME";
    public static final String TEXT_SIZE = "TEXT_SIZE";
    public static final String SLICE_REPEAT = "SLICE_REPEAT";
    public static final String CURRENT_WHEEL = "CURRENT_WHEEL";
    public static String WHEEL_EDITED = "WHEEL_EDITED";
    LuckyWheel luckyWheel;
    List<WheelItem> wheelItems = new ArrayList<>();
    ImageButton backButton;
    Button editButton;
    int textSize;
    int repeate;
    int spinTime;
    boolean fairMode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_custom);
        wheelItems = (List<WheelItem>) getIntent().getSerializableExtra(LuckyWheelActivity.CURRENT_WHEEL);
        textSize = (int) getIntent().getSerializableExtra(LuckyWheelActivity.TEXT_SIZE);
        repeate = (int) getIntent().getSerializableExtra(LuckyWheelActivity.SLICE_REPEAT);
        spinTime = (int) getIntent().getSerializableExtra(LuckyWheelActivity.SPIN_TIME);
        fairMode = (boolean) getIntent().getSerializableExtra(LuckyWheelActivity.FAIR_MODE);
        System.out.println(wheelItems.size());
        luckyWheel = findViewById(R.id.lwv);
        luckyWheel.setTarget(1);
        luckyWheel.addWheelItems(wheelItems);
        initView();
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
                intentToCustom.putExtra(LuckyWheelCustomActivity.CURRENT_WHEEL, (Serializable) wheelItems);
                intentToCustom.putExtra(LuckyWheelCustomActivity.FAIR_MODE, fairMode);
                intentToCustom.putExtra(LuckyWheelCustomActivity.SPIN_TIME, spinTime);
                intentToCustom.putExtra(LuckyWheelCustomActivity.TEXT_SIZE, textSize);
                intentToCustom.putExtra(LuckyWheelCustomActivity.SLICE_REPEAT, repeate);
                intentLauncher.launch(intentToCustom);
            }
        });
    }
}

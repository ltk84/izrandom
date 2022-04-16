package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.LuckyWheelActivity;

public class LuckyWheelCustomActivity extends AppCompatActivity {
    LuckyWheel luckyWheel;
    List<WheelItem> wheelItems = new ArrayList<>();
    ImageButton backButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_custom);
        wheelItems = (List<WheelItem>) getIntent().getSerializableExtra(LuckyWheelActivity.CURRENT_WHEEL);
        System.out.println(wheelItems.size());
        luckyWheel = findViewById(R.id.lwv);
        luckyWheel.setTarget(1);
        luckyWheel.addWheelItems(wheelItems);
        initView();
        setListener();
    }
    void initView()
    {
        backButton = findViewById(R.id.bb_rand_wheel);
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
    }
}

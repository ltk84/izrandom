package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;

public class LuckyWheelCustomActivity extends AppCompatActivity {
    LuckyWheel luckyWheel;
    List<WheelItem> wheelItems = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_custom);
        luckyWheel = findViewById(R.id.lwv);
        generateWheelItems();
        luckyWheel.setTarget(1);
        luckyWheel.addWheelItems(wheelItems);
    }
    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#ffe05f"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons) , "Ngủ"));
        wheelItems.add(new WheelItem(Color.parseColor("#ffaa64"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons) , "Chơi"));
        wheelItems.add(new WheelItem(Color.parseColor("#ff534a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Học"));
        wheelItems.add(new WheelItem(Color.parseColor("#aadb6b"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Ăn"));
        wheelItems.add(new WheelItem(Color.parseColor("#ffe05f"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Nhậu"));
        wheelItems.add(new WheelItem(Color.parseColor("#ffaa64"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Golf"));
    }
}

package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;

public class LuckyWheelActivity extends AppCompatActivity {
    LuckyWheel lkWheel;
    List <WheelItem> wheelItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel);
        lkWheel = findViewById(R.id.lwv);
        generateWheelItems();
        lkWheel.addWheelItems(wheelItems);
        lkWheel.setTarget(1);
        lkWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                Toast.makeText(LuckyWheelActivity.this, "Target Reached", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#fc6c6c"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons) , "100 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons) , "0 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#F00E6F"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "30 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "6000 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#fc6c6c"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "9 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "20 $"));
    }
}
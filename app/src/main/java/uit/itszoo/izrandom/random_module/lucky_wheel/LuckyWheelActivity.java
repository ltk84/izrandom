package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.compose.ConstrainedLayoutReference;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uit.itszoo.izrandom.R;

public class LuckyWheelActivity extends AppCompatActivity {
    LuckyWheel lkWheel;
    List <WheelItem> wheelItems = new ArrayList<>();
    TextView randomResult;
    ConstraintLayout constrainedLayout;
    private  String result = "";
    final int SWIPE_DISTANCE_THRESHOLD = 100;
    float x1, x2, y1, y2, dx, dy;
    int  selectedIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel);
        constrainedLayout = findViewById(R.id.layout);
        randomResult = findViewById(R.id.random_result);
        initLuckyWheel();
    }
    private void initLuckyWheel()
    {
        lkWheel = findViewById(R.id.lwv);
        generateWheelItems();
        lkWheel.addWheelItems(wheelItems);
        lkWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                Toast.makeText(LuckyWheelActivity.this, "Target Reached", Toast.LENGTH_LONG).show();
                randomResult.setText(result);
                constrainedLayout.setBackgroundColor(wheelItems.get(selectedIndex ).color);
            }
        });
        lkWheel.setOnTouchListener(
                new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case (MotionEvent.ACTION_DOWN):
                                x1 = event.getX();
                                y1 = event.getY();
                                break;
                            case MotionEvent.ACTION_UP:
                                x2 = event.getX();
                                y2 = event.getY();
                                dx = x2 - x1;
                                dy = y2 - y1;
                                if ( Math.abs(dx) > Math.abs(dy) ) {
                                    if ( dx < 0 && Math.abs(dx) > SWIPE_DISTANCE_THRESHOLD )
                                    {
                                        Random random = new Random();
                                        selectedIndex = random.nextInt(wheelItems.size()-1);
                                        lkWheel.setTarget(selectedIndex+1);
                                        result = wheelItems.get(selectedIndex).text;
                                        lkWheel.rotateWheelTo(selectedIndex+1);
                                    }
                                } else {
                                    if ( dy > 0 && Math.abs(dy) > SWIPE_DISTANCE_THRESHOLD )
                                    {
                                        Random random = new Random();
                                        selectedIndex = random.nextInt(wheelItems.size()-1);
                                        lkWheel.setTarget(selectedIndex+1);
                                        result = wheelItems.get(selectedIndex).text;
                                        lkWheel.rotateWheelTo(selectedIndex+1);
                                    }
                                }
                                break;
                            default:
                                return true;
                        }
                        return true;
                    }
                }
        );
    }
    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#ffe05f"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons) , "100 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#ffaa64"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons) , "0 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#ff534a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "30 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#aadb6b"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "6000 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#ffe05f"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "9 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#ffaa64"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "20 $"));
    }
}
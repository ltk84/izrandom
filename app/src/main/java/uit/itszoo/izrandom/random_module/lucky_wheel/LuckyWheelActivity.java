package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
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
import java.util.Timer;
import java.util.TimerTask;

import uit.itszoo.izrandom.R;

public class LuckyWheelActivity extends AppCompatActivity {
    LuckyWheel lkWheel;
    List <WheelItem> wheelItems = new ArrayList<>();
    TextView randomResult;
    TextView description;
    ConstraintLayout constrainedLayout;
    ImageButton backButton ;
    ImageButton customButton;
    private int spinTime = 9000;
    private  String result = "";
    private final int SWIPE_DISTANCE_THRESHOLD = 100;
    private float x1, x2, y1, y2, dx, dy;
    private int  selectedIndex;
    private boolean spin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel);
        constrainedLayout = findViewById(R.id.layout);
        randomResult = findViewById(R.id.random_result);
        backButton = findViewById(R.id.bb_back);
        customButton = findViewById(R.id.bt_cus);
        description = findViewById(R.id.description);
        initLuckyWheel();
       setListenerForView();
    }
    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spin == true)
                {
                    Toast.makeText(LuckyWheelActivity.this, "During a spindling", Toast.LENGTH_LONG).show();
                }
                else
                    onBackPressed();
            }
        });

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                if ( Math.abs(dx) > Math.abs(dy) && spin == false ) {
                                    if ( dx < 0 && Math.abs(dx) > SWIPE_DISTANCE_THRESHOLD )
                                    {
                                        spin = true;
                                        Random random = new Random();
                                        selectedIndex = random.nextInt(wheelItems.size());
                                        lkWheel.setTarget(selectedIndex+1);
                                        result = wheelItems.get(selectedIndex).text;
                                        lkWheel.rotateWheelTo(selectedIndex+1);
                                        description.setVisibility(View.INVISIBLE);
                                    }
                                } else {
                                    if ( dy > 0 && Math.abs(dy) > SWIPE_DISTANCE_THRESHOLD && spin == false)
                                    {
                                        spin = true;
                                        Random random = new Random();
                                        selectedIndex = random.nextInt(wheelItems.size());
                                        lkWheel.setTarget(selectedIndex+1);
                                        result = wheelItems.get(selectedIndex).text;
                                        lkWheel.rotateWheelTo(selectedIndex+1);
                                        description.setVisibility(View.INVISIBLE);
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
                backButton.setBackgroundColor(wheelItems.get(selectedIndex ).color);
                customButton.setBackgroundColor(wheelItems.get(selectedIndex ).color);
                description.setVisibility(View.VISIBLE);
                spin = false;
            }
        });
    }
    private  void setAnimationForBackground(int spinTime)
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, spinTime/wheelItems.size());
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
package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.compose.ConstrainedLayoutReference;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom.LuckyWheelCustomActivity;
import uit.itszoo.izrandom.random_module.random_direction.RandomDirectionActivity;
import uit.itszoo.izrandom.random_module.random_direction.RandomDirectionPresenter;
import uit.itszoo.izrandom.random_module.random_direction.model.Arrow;
import uit.itszoo.izrandom.random_module.random_direction.random_direction_custom.RandomDirectionCustomActivity;

public class LuckyWheelActivity extends AppCompatActivity implements LuckyWheelContract.View{
    LuckyWheel lkWheel;
    List <WheelItem> wheelItems = new ArrayList<>();
    TextSwitcher randomResult;
    TextView description;
    ConstraintLayout constrainedLayout;
    ImageButton backButton ;
    ImageButton customButton;
    LuckyWheelContract.Presenter presenter;
    TextView textView;
    private  int spinTime = 9000;
    private  String result = "";
    private final int SWIPE_DISTANCE_THRESHOLD = 100;
    private float x1, x2, y1, y2, dx, dy;
    private int  selectedIndex;
    private boolean spin = false;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel);
        constrainedLayout = findViewById(R.id.layout);
        randomResult = findViewById(R.id.random_result);
        randomResult.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                textView
                        = new TextView(
                        LuckyWheelActivity.this);
                textView.setTextSize(20);
                textView.setGravity(
                        Gravity.CENTER_HORIZONTAL);
                return textView;
            }
        });
        presenter = new LuckWheelPresenter(getApplicationContext(), this);
        setPresenter(presenter);
        randomResult.setText("Làm gì bây giờ?");
        backButton = findViewById(R.id.bb_back);
        customButton = findViewById(R.id.bt_cus);
        description = findViewById(R.id.description);
        initLuckyWheel();
        setListenerForView();
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
    @SuppressLint("ClickableViewAccessibility")
    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spin)
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
                Intent intentToCustom = new Intent(getApplicationContext(), LuckyWheelCustomActivity.class);
                intentLauncher.launch(intentToCustom);
            }
        });
        lkWheel.setOnTouchListener(
                new View.OnTouchListener() {
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
                                if ( Math.abs(dx) > Math.abs(dy) && !spin) {
                                    if ( dx < 0 && Math.abs(dx) > SWIPE_DISTANCE_THRESHOLD )
                                    {
                                        spin = true;
                                        Random random = new Random();
                                        selectedIndex = random.nextInt(wheelItems.size());
                                        lkWheel.setTarget(selectedIndex+1);
                                        result = wheelItems.get(selectedIndex).text;
                                        lkWheel.rotateWheelTo(selectedIndex+1);
                                        description.setVisibility(View.INVISIBLE);
                                        setAnimationForBackground(spinTime);
                                    }
                                } else {
                                    if ( dy > 0 && Math.abs(dy) > SWIPE_DISTANCE_THRESHOLD && !spin)
                                    {
                                        spin = true;
                                        Random random = new Random();
                                        selectedIndex = random.nextInt(wheelItems.size());
                                        lkWheel.setTarget(selectedIndex+1);
                                        result = wheelItems.get(selectedIndex).text;
                                        lkWheel.rotateWheelTo(selectedIndex+1);
                                        description.setVisibility(View.INVISIBLE);
                                        setAnimationForBackground(spinTime);
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
        presenter.initListWheelItems(wheelItems);
        lkWheel.addWheelItems(presenter.getWheelItems());
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
    int time = 0;
    private  void setAnimationForBackground(int spinTime)
    {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                randomResult.setText(wheelItems.get(index).text);
                index += 1;
                time += 1;
                if(index == wheelItems.size())
                {
                    index = 0;
                }
                if(time == 20)
                {
                    cancel();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, spinTime/wheelItems.size());
        timer.cancel();
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

    @Override
    public void setPresenter(LuckyWheelContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
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

import java.io.IOException;
import java.io.Serializable;
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
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;
import uit.itszoo.izrandom.random_module.random_direction.RandomDirectionActivity;
import uit.itszoo.izrandom.random_module.random_direction.RandomDirectionPresenter;
import uit.itszoo.izrandom.random_module.random_direction.model.Arrow;
import uit.itszoo.izrandom.random_module.random_direction.random_direction_custom.RandomDirectionCustomActivity;

public class LuckyWheelActivity extends AppCompatActivity implements LuckyWheelContract.View{
    public static final String CURRENT_WHEEL = "CURRENT_WHEEL";

    LuckyWheel lkWheel;
    List <WheelItem> wheelItems = new ArrayList<WheelItem>();
    TextSwitcher randomResult;
    TextView description;
    ConstraintLayout constrainedLayout;
    ImageButton backButton ;
    ImageButton customButton;
    LuckyWheelContract.Presenter presenter;
    TextView textView;
    int indexCurrentWheel = 0; /// index của wheelContent trong source
    private  int spinTime ;
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
        backButton = findViewById(R.id.bb_back);
        customButton = findViewById(R.id.bt_cus);
        description = findViewById(R.id.description);
        spinTime = presenter.getSpinTime();
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
                        indexCurrentWheel = (int) data.getSerializableExtra(LuckyWheelCustomActivity.SELECTED_WHEEL);
                        initLuckyWheel();
                        presenter.setIndexOfWheelInList(indexCurrentWheel);
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
            public void onClick(View view){
                Intent intentToCustom = new Intent(getApplicationContext(), LuckyWheelCustomActivity.class);
                intentToCustom.putExtra(LuckyWheelActivity.CURRENT_WHEEL, presenter.getIndexOfWheelInList());
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
                                    if ( dx < 0 && Math.abs(dx) > SWIPE_DISTANCE_THRESHOLD ) {
                                        spin = true;
                                        Random random = new Random();
                                        lkWheel.setSpinTime(spinTime);
                                        selectedIndex = random.nextInt(wheelItems.size());
                                        lkWheel.setTarget(selectedIndex + 1);
                                        result = wheelItems.get(selectedIndex).text;
                                        lkWheel.rotateWheelTo(selectedIndex + 1);
                                        description.setVisibility(View.INVISIBLE);
                                    }
                                } else {
                                    if ( dy > 0 && Math.abs(dy) > SWIPE_DISTANCE_THRESHOLD && !spin)
                                    {
                                        spin = true;
                                        Random random = new Random();
                                        lkWheel.setSpinTime(spinTime);
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
        randomResult.setText(LuckyWheelSource.listTitle.get(indexCurrentWheel));
        generateWheelItems();
        presenter.initListWheelItems(wheelItems);
        presenter.setFairMode(LuckyWheelSource.fairMode.get(indexCurrentWheel));
        presenter.setSpinTime(LuckyWheelSource.spinTime.get(indexCurrentWheel));
        presenter.setRepeat(LuckyWheelSource.repeat.get(indexCurrentWheel));
        presenter.setTextSize(LuckyWheelSource.textSize.get(indexCurrentWheel));
        randomResult.setText(LuckyWheelSource.listTitle.get(indexCurrentWheel));
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

    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        List<String> content= new ArrayList<>();
        List<String> color= new ArrayList<>();
        content = LuckyWheelSource.listContent.get(indexCurrentWheel);
        color = LuckyWheelSource.listColor.get(indexCurrentWheel);
        for(int i = 0 ; i < content.size();i++)
        {
            wheelItems.add(new WheelItem(Color.parseColor(color.get(i)), BitmapFactory.decodeResource(getResources(),
                    R.drawable.small_nails_icons) , content.get(i)));
        }
    }

    @Override
    public void setPresenter(LuckyWheelContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
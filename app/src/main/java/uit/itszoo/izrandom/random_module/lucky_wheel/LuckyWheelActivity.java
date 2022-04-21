package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
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
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom.LuckyWheelCustomActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;

public class LuckyWheelActivity extends AppCompatActivity implements LuckyWheelContract.View{
    public static final String CURRENT_WHEEL = "CURRENT_WHEEL";

    LuckyWheel lkWheel;
    List <WheelItem> mixedWheelItems = new ArrayList<WheelItem>();
    TextSwitcher randomResult;
    TextView description;
    ConstraintLayout constrainedLayout;
    ImageButton backButton ;
    ImageButton customButton;
    LuckyWheelContract.Presenter presenter;
    TextView textView;
    int indexCurrentWheel = 0; /// index của wheelContent trong source
    private  String result = "";
    private final int SWIPE_DISTANCE_THRESHOLD = 100;
    private float x1, x2, y1, y2, dx, dy;
    private int  selectedIndex;
    private boolean spin = false;
    private  String randomContent  = "";
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
    public void randomIndex()
    {
        Random random = new Random();
        selectedIndex = random.nextInt(mixedWheelItems.size());

    }
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
                                        randomIndex();
                                        lkWheel.setSpinTime(LuckyWheelSource.spinTime.get(indexCurrentWheel));
                                        if(LuckyWheelSource.fairMode.get(indexCurrentWheel) == true)
                                        {
                                            while (mixedWheelItems.get(selectedIndex).text == randomContent)
                                            {
                                                randomIndex();
                                            }
                                        }
                                        lkWheel.setTarget(selectedIndex + 1);
                                        result = mixedWheelItems.get(selectedIndex).text;
                                        lkWheel.rotateWheelTo(selectedIndex + 1);
                                        description.setVisibility(View.INVISIBLE);
                                    }
                                } else {
                                    if ( dy > 0 && Math.abs(dy) > SWIPE_DISTANCE_THRESHOLD && !spin)
                                    {
                                        spin = true;
                                        randomIndex();
                                        lkWheel.setSpinTime(LuckyWheelSource.spinTime.get(indexCurrentWheel));
                                        if(LuckyWheelSource.fairMode.get(indexCurrentWheel) == true)
                                        {
                                            while (mixedWheelItems.get(selectedIndex).text == randomContent)
                                            {
                                                randomIndex();
                                            }
                                        }
                                        lkWheel.setTarget(selectedIndex + 1);
                                        result = mixedWheelItems.get(selectedIndex).text;
                                        lkWheel.rotateWheelTo(selectedIndex + 1);
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
        presenter.initListWheelItems(mixedWheelItems);
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
                if(LuckyWheelSource.fairMode.get(indexCurrentWheel) == true)
                {
                    randomContent = result;
                }
                constrainedLayout.setBackgroundColor(mixedWheelItems.get(selectedIndex ).color);
                backButton.setBackgroundColor(mixedWheelItems.get(selectedIndex ).color);
                customButton.setBackgroundColor(mixedWheelItems.get(selectedIndex ).color);
                description.setVisibility(View.VISIBLE);
                spin = false;
            }
        });
    }

    private void generateWheelItems() {
        mixedWheelItems = new ArrayList<>();
        List<String> mixedContent= new ArrayList<>();
        List<String> contents= new ArrayList<>();
        List<String> color= new ArrayList<>();
        mixedContent = LuckyWheelSource.mixedContentItem.get(indexCurrentWheel);
        color = LuckyWheelSource.listColor.get(indexCurrentWheel);
        contents = LuckyWheelSource.listContent.get(indexCurrentWheel);
        for(int i = 0 ; i < mixedContent.size();i++)
        {
            int indexColor = contents.indexOf(mixedContent.get(i));
            mixedWheelItems.add(new WheelItem(Color.parseColor(color.get(indexColor)), BitmapFactory.decodeResource(getResources(),
                    R.drawable.small_nails_icons) , mixedContent.get(i)));
        }
    }

    @Override
    public void setPresenter(LuckyWheelContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
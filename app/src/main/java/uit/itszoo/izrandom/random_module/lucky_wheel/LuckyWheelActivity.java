package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.adapter.SliceToWheelItem;
import uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom.LuckyWheelCustomActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;

public class LuckyWheelActivity extends AppCompatActivity implements LuckyWheelContract.View {
    public static final String CURRENT_WHEEL = "CURRENT_WHEEL";

    LuckyWheel lkWheel;
    List<WheelItem> wheelItems = new ArrayList<WheelItem>();
    TextSwitcher titleTextView;
    TextView description;
    TextView description2;
    ConstraintLayout constrainedLayout;
    ImageButton backButton;
    ImageButton customButton;
    LuckyWheelContract.Presenter presenter;
    TextView textView;

    Random random = new Random();
    private String result = "";
    private final int SWIPE_DISTANCE_THRESHOLD = 100;
    private float x1, x2, y1, y2, dx, dy;
    private int selectedSliceIndex;
    private boolean spin = false;
    private String lastResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel);

        constrainedLayout = findViewById(R.id.layout);
        backButton = findViewById(R.id.bb_back);
        customButton = findViewById(R.id.bt_cus);
        description = findViewById(R.id.description);
        description2 = findViewById(R.id.description2);
        titleTextView = findViewById(R.id.random_result);
        titleTextView.setFactory(() -> {
            textView = new TextView(
                    LuckyWheelActivity.this);
            textView.setTextSize(30);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            textView.setGravity(
                    Gravity.CENTER_HORIZONTAL);
            return textView;
        });

        presenter = new LuckyWheelPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        // lấy wheel hiện tại từ db
        // tạm thời lấy wheel đầu tiên trong danh sách để test
        initLuckyWheel(LuckyWheelSource.luckyWheelList.get(0));
        setListenerForView();
    }

    ActivityResultLauncher<Intent> intentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    LuckyWheelData currentWheel = (LuckyWheelData) data.getSerializableExtra(LuckyWheelCustomActivity.SELECTED_WHEEL);
                    initLuckyWheel(currentWheel);
                }
            });

    public void executeRandom() {
        selectedSliceIndex = random.nextInt(wheelItems.size());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (spin) {
            return super.onTouchEvent(event);
        }
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                return true;
            case (MotionEvent.ACTION_UP):
                handleSpinTheWheel();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void handleSpinTheWheel() {
        spin = true;
        executeRandom();
        lkWheel.setSpinTime(presenter.getWheelData().getSpinTime());
        if (presenter.getWheelData().isFairMode()) {
            while (wheelItems.get(selectedSliceIndex).text.equals(lastResult)) {
                executeRandom();
            }
        }
        lkWheel.setTarget(selectedSliceIndex + 1);
        result = wheelItems.get(selectedSliceIndex).text;
        lkWheel.rotateWheelTo(selectedSliceIndex + 1);
        description.setVisibility(View.INVISIBLE);
        description2.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setListenerForView() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spin) {
                    Toast.makeText(LuckyWheelActivity.this, "During a spindling", Toast.LENGTH_LONG).show();
                } else {
                    onBackPressed();
                }
            }
        });

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spin) {
                    Toast.makeText(LuckyWheelActivity.this, "During a spindling", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intentToCustom = new Intent(getApplicationContext(), LuckyWheelCustomActivity.class);
//                intentToCustom.putExtra(LuckyWheelActivity.CURRENT_WHEEL, presenter.getIndexOfWheelInList());
                intentLauncher.launch(intentToCustom);
            }
        });

        lkWheel.setOnTouchListener(
                (v, event) -> {
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
                            if (Math.abs(dx) > Math.abs(dy) && !spin) {
                                if (dx < 0 && Math.abs(dx) > SWIPE_DISTANCE_THRESHOLD) {
                                    handleSpinTheWheel();
                                }
                            } else {
                                if (dy > 0 && Math.abs(dy) > SWIPE_DISTANCE_THRESHOLD && !spin) {
                                    handleSpinTheWheel();
                                }
                            }
                            break;
                        default:
                            return true;
                    }
                    return true;
                }
        );

    }

    private void initLuckyWheel(LuckyWheelData luckyWheelData) {
        lkWheel = findViewById(R.id.lwv);

        // TODO: đổi lại thành lấy từ db
        presenter.setWheelData(luckyWheelData);

        generateWheelItems();

        titleTextView.setText(presenter.getWheelData().getTitle());

        lkWheel.addWheelItems(wheelItems);
        lkWheel.setLuckyWheelReachTheTarget(() -> {
            Toast.makeText(LuckyWheelActivity.this, "Target Reached", Toast.LENGTH_LONG).show();
            titleTextView.setText(result);
            if (presenter.getWheelData().isFairMode()) {
                lastResult = result;
            }
            constrainedLayout.setBackgroundColor(wheelItems.get(selectedSliceIndex).color);
            backButton.setBackgroundColor(wheelItems.get(selectedSliceIndex).color);
            customButton.setBackgroundColor(wheelItems.get(selectedSliceIndex).color);
            description.setVisibility(View.VISIBLE);
            description2.setVisibility(View.VISIBLE);
            spin = false;
        });
    }

    private void generateWheelItems() {
        wheelItems = SliceToWheelItem.convertSlicesToWheelItems(
                getResources(), presenter.getWheelData().getSlicesWithRepeat());
    }

    @Override
    public void setPresenter(LuckyWheelContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
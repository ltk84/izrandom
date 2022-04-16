package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;
import com.google.android.material.slider.Slider;

import java.util.List;

import uit.itszoo.izrandom.R;

public class EditLuckyWheelActivity  extends AppCompatActivity {
    LuckyWheel luckyWheel;
    List<WheelItem> wheelItems;
    ImageButton backButton;
    ImageButton deleteButton;
    ImageButton checkButton;
    Slider textSizeSlider;
    Slider sliceRepeatSlider;
    Slider spinTimeSlider;
    Switch fairModeSwitch;
    TextView textSizeView;
    TextView sliceRepeatView;
    TextView spinTimeView;

    int textSize=1;
    int repeat =1;
    int spinTime = 9000;
    boolean fairMode = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_edit);
        wheelItems = (List<WheelItem>) getIntent().getSerializableExtra(LuckyWheelCustomActivity.CURRENT_WHEEL);
        textSize = (int) getIntent().getSerializableExtra(LuckyWheelCustomActivity.TEXT_SIZE);
        repeat = (int) getIntent().getSerializableExtra(LuckyWheelCustomActivity.SLICE_REPEAT);
        spinTime = (int) getIntent().getSerializableExtra(LuckyWheelCustomActivity.SPIN_TIME);
        fairMode = (boolean) getIntent().getSerializableExtra(LuckyWheelCustomActivity.FAIR_MODE);
        initView();
        setOnlistener();
    }
    void initView()
    {
        luckyWheel = findViewById(R.id.edit_lucky_wheel);
        luckyWheel.setTarget(1);
        luckyWheel.addWheelItems(wheelItems);
        backButton = findViewById(R.id.bb_lucky_wheel_edit);
        deleteButton = findViewById(R.id.bt_delete_lucky_wheel_edit);
        checkButton = findViewById(R.id.bt_check_lucky_wheel_edit);
        textSizeView = findViewById(R.id.text_size_edit_lucky_wheel);
        sliceRepeatView = findViewById(R.id.slide_repeat);
        spinTimeView = findViewById(R.id.spin_time);
        textSizeSlider = findViewById(R.id.text_size_slider);
        spinTimeSlider = findViewById(R.id.spin_time_slide);
        sliceRepeatSlider = findViewById(R.id.slice_repeat_slider);
        fairModeSwitch = findViewById(R.id.fair_mode);
        textSizeView.setText(String.valueOf(textSize));
        sliceRepeatView.setText(String.valueOf(repeat));
        spinTimeView.setText(String.valueOf(spinTime/1000));
        fairModeSwitch.setChecked(fairMode);
        sliceRepeatSlider.setValue(repeat);
        spinTimeSlider.setValue(spinTime/1000);
        textSizeSlider.setValue(textSize);
    }
    void setOnlistener()
    {
        backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                }
        );
        sliceRepeatSlider.addOnChangeListener(
                new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        repeat = (int) sliceRepeatSlider.getValue();
                        sliceRepeatView.setText(String.valueOf(repeat));
                    }
                }
        );
        textSizeSlider.addOnChangeListener(
                new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        textSize = (int) textSizeSlider.getValue();
                        textSizeView.setText(String.valueOf(textSize));
                    }
                }
        );
        spinTimeSlider.addOnChangeListener(
                new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        spinTime = (int) spinTimeSlider.getValue()*1000;
                        spinTimeView.setText(String.valueOf(spinTime/1000));
                    }
                }
        );
        fairModeSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        fairMode = fairModeSwitch.isChecked();
                    }
                }
        );
    }
}

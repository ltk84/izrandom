package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;

public class EditLuckyWheelActivity  extends AppCompatActivity {
    LuckyWheel luckyWheel;
    List<WheelItem> wheelItems = new ArrayList<>();
    CardView listCardView[]= new CardView[12];
    TextView listTextInCard[] = new TextView[12];
    ImageButton backButton;
    ImageButton deleteButton;
    ImageButton checkButton;
    ImageButton mixButton;
    Slider textSizeSlider;
    Slider sliceRepeatSlider;
    Slider spinTimeSlider;
    Switch fairModeSwitch;
    TextView textSizeView;
    TextView sliceRepeatView;
    TextView spinTimeView;
    int indexCurrentWheel;
    int textSize=1;
    int repeat =1;
    int spinTime;
    boolean fairMode = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_edit);
        indexCurrentWheel = (int) getIntent().getSerializableExtra(LuckyWheelCustomActivity.CURRENT_WHEEL);
        textSize = LuckyWheelSource.textSize.get(indexCurrentWheel);
        repeat = LuckyWheelSource.repeat.get(indexCurrentWheel);
        spinTime = LuckyWheelSource.spinTime.get(indexCurrentWheel);
        fairMode = LuckyWheelSource.fairMode.get(indexCurrentWheel);
        generateWheelItems();
        initView();
        setOnlistener();
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
        spinTimeView.setText(String.valueOf(spinTime));
        fairModeSwitch.setChecked(fairMode);
        sliceRepeatSlider.setValue(repeat);
        spinTimeSlider.setValue(spinTime);
        textSizeSlider.setValue(textSize);
        mixButton = findViewById(R.id.mix_button);
        listCardView[0] = findViewById(R.id.cardView1);
        listCardView[1] = findViewById(R.id.cardView2);
        listCardView[2] = findViewById(R.id.cardView3);
        listCardView[3] = findViewById(R.id.cardView4);
        listCardView[4] = findViewById(R.id.cardView5);
        listCardView[5] = findViewById(R.id.cardView6);
        listCardView[6] = findViewById(R.id.cardView7);
        listCardView[7] = findViewById(R.id.cardView8);
        listCardView[8] = findViewById(R.id.cardView9);
        listCardView[9] = findViewById(R.id.cardView10);
        listCardView[10] = findViewById(R.id.cardView11);
        listCardView[11] = findViewById(R.id.cardView12);
        listTextInCard[0] = findViewById(R.id.textcard1);
        listTextInCard[1] = findViewById(R.id.textcard2);
        listTextInCard[2] = findViewById(R.id.textcard3);
        listTextInCard[3] = findViewById(R.id.textcard4);
        listTextInCard[4] = findViewById(R.id.textcard5);
        listTextInCard[5] = findViewById(R.id.textcard6);
        listTextInCard[6] = findViewById(R.id.textcard7);
        listTextInCard[7] = findViewById(R.id.textcard8);
        listTextInCard[8] = findViewById(R.id.textcard9);
        listTextInCard[9] = findViewById(R.id.textcard10);
        listTextInCard[10] = findViewById(R.id.textcard11);
        listTextInCard[11] = findViewById(R.id.textcard12);
        createSliceCard();
    }
    void createSliceCard()
    {
        for(int i = wheelItems.size() ; i < listCardView.length ; i++)
        {
            listCardView[i].setVisibility(View.INVISIBLE);
            listTextInCard[i].setVisibility(View.INVISIBLE);
        }
        for(int i = 0; i <wheelItems.size(); i++)
        {
            listCardView[i].setBackgroundColor(wheelItems.get(i).color);
            listTextInCard[i].setText(wheelItems.get(i).text);
        }
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
                        spinTime = (int) spinTimeSlider.getValue();
                        spinTimeView.setText(String.valueOf(spinTime));
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
        mixButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Collections.shuffle(wheelItems);
                        //generateWheelItems();
                        luckyWheel.addWheelItems(wheelItems);
                        createSliceCard();
                    }
                }
        );
    }
}

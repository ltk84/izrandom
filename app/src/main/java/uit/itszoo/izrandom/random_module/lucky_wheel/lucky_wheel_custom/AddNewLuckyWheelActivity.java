package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class AddNewLuckyWheelActivity  extends AppCompatActivity {
    LuckyWheel luckyWheel;
    List<WheelItem> wheelItems = new ArrayList<>();
    CardView listCardView[]= new CardView[9];
    TextView listTextInCard[] = new TextView[9];
    ImageButton backButton;
    ImageButton checkButton;
    ImageButton mixButton;
    Slider textSizeSlider;
    Slider sliceRepeatSlider;
    Slider spinTimeSlider;
    Switch fairModeSwitch;
    TextView textSizeView;
    TextView sliceRepeatView;
    TextView spinTimeView;
    LinearLayout sliceList;
    int textSize=16;
    int repeat =1;
    int spinTime = 9000;
    boolean fairMode = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_lucky_wheel);
       initView();
       setOnListener();
    }
    void initView()
    {
        generateWheelItems();
        luckyWheel = findViewById(R.id.lw_add_wheel);
        luckyWheel.setTarget(1);
        luckyWheel.addWheelItems(wheelItems);
        backButton = findViewById(R.id.bb_add_wheel);
        textSizeView = findViewById(R.id.text_size_add_lucky_wheel);
        sliceRepeatView = findViewById(R.id.slide_repeat_add_lucky_wheel);
        spinTimeView = findViewById(R.id.spin_time_add_lucky_wheel);
        textSizeSlider = findViewById(R.id.text_size_slider_add_lucky_wheel);
        spinTimeSlider = findViewById(R.id.spin_time_slide_add_lucky_wheel);
        sliceRepeatSlider = findViewById(R.id.slice_repeat_slider_add_lucky_wheel);
        fairModeSwitch = findViewById(R.id.fair_mode_add_lucky_wheel);
        textSizeView.setText(String.valueOf(textSize));
        sliceRepeatView.setText(String.valueOf(repeat));
        spinTimeView.setText(String.valueOf(spinTime/1000));
        fairModeSwitch.setChecked(fairMode);
        sliceRepeatSlider.setValue(repeat);
        spinTimeSlider.setValue(spinTime/1000);
        textSizeSlider.setValue(textSize);
        mixButton = findViewById(R.id.add_wheel_mix_button);
        listCardView[0] = findViewById(R.id.add_wheel_cardView1);
        listCardView[1] = findViewById(R.id.add_wheel_cardView2);
        listCardView[2] = findViewById(R.id.add_wheel_cardView3);
        listCardView[3] = findViewById(R.id.add_wheel_cardView4);
        listCardView[4] = findViewById(R.id.add_wheel_cardView5);
        listCardView[5] = findViewById(R.id.add_wheel_cardView6);
        listCardView[6] = findViewById(R.id.add_wheel_cardView7);
        listCardView[7] = findViewById(R.id.add_wheel_cardView8);
        listCardView[8] = findViewById(R.id.add_wheel_cardView9);
        listTextInCard[0] = findViewById(R.id.add_wheel_textcard1);
        listTextInCard[1] = findViewById(R.id.add_wheel_textcard2);
        listTextInCard[2] = findViewById(R.id.add_wheel_textcard3);
        listTextInCard[3] = findViewById(R.id.add_wheel_textcard4);
        listTextInCard[4] = findViewById(R.id.add_wheel_textcard5);
        listTextInCard[5] = findViewById(R.id.add_wheel_textcard6);
        listTextInCard[6] = findViewById(R.id.add_wheel_textcard7);
        listTextInCard[7] = findViewById(R.id.add_wheel_textcard8);
        listTextInCard[8] = findViewById(R.id.add_wheel_textcard9);
        createSliceCard();
    }

    private void createSliceCard() {
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

    void setOnListener()
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
        mixButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Collections.shuffle(wheelItems);
                        luckyWheel.addWheelItems(wheelItems);
                        createSliceCard();
                    }
                }
        );
    }

    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#ffe05f"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons) , "Name 1"));
        wheelItems.add(new WheelItem(Color.parseColor("#ffaa64"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons) , "Name 2"));
        wheelItems.add(new WheelItem(Color.parseColor("#ff534a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 3"));
        wheelItems.add(new WheelItem(Color.parseColor("#aadb6b"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 4"));
        wheelItems.add(new WheelItem(Color.parseColor("#ffe05f"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 5"));
        wheelItems.add(new WheelItem(Color.parseColor("#ffaa64"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 6"));
    }
}

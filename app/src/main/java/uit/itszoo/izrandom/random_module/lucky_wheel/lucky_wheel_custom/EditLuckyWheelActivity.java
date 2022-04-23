package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import top.defaults.colorpicker.ColorPickerPopup;
import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;

public class EditLuckyWheelActivity  extends AppCompatActivity {
    LuckyWheel luckyWheel;
    List<WheelItem> wheelItems = new ArrayList<>();
    List<WheelItem> wheelShowedItems = new ArrayList<>();
    CardView listCardView[]= new CardView[12];
    TextView listTextInCard[] = new TextView[12];
    ArrayList<Integer> indexSliceCard = new ArrayList<Integer>();
    ArrayList<String> mixedContent = new ArrayList<>();
    ArrayList<String> wheelContents = new ArrayList<>();
    ArrayList<String> colors = new ArrayList<>();
    ImageButton backButton;
    ImageButton deleteButton;
    ImageButton checkButton;
    ImageButton mixButton;
    ImageButton addSliceButton;
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
    int changeColor;
    public static enum DrawablePosition { TOP, BOTTOM, LEFT, RIGHT };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_edit);
        indexCurrentWheel = (int) getIntent().getSerializableExtra(LuckyWheelCustomActivity.CURRENT_WHEEL);
        textSize = LuckyWheelSource.textSize.get(indexCurrentWheel);
        repeat = LuckyWheelSource.repeat.get(indexCurrentWheel);
        spinTime = LuckyWheelSource.spinTime.get(indexCurrentWheel);
        fairMode = LuckyWheelSource.fairMode.get(indexCurrentWheel);
        mixedContent.addAll(LuckyWheelSource.mixedContentItem.get(indexCurrentWheel));
        wheelContents.addAll(LuckyWheelSource.listContent.get(indexCurrentWheel));
        colors.addAll(LuckyWheelSource.listColor.get(indexCurrentWheel));
        generateWheelItems();
        initView();
        setOnlistener();
    }
    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        for(int i = 0 ; i < wheelContents.size();i++)
        {
            wheelItems.add(new WheelItem(Color.parseColor(colors.get(i)), BitmapFactory.decodeResource(getResources(),
                    R.drawable.small_nails_icons) , wheelContents.get(i)));
            indexSliceCard.add(i);
        }
        for(int i = 0 ; i < mixedContent.size();i++)
        {
            int indexColor = wheelContents.indexOf(mixedContent.get(i));
            wheelShowedItems.add(new WheelItem(Color.parseColor(colors.get(indexColor)), BitmapFactory.decodeResource(getResources(),
                    R.drawable.small_nails_icons) , mixedContent.get(i)));
        }
    }
    void initView()
    {
        luckyWheel = findViewById(R.id.edit_lucky_wheel);
        luckyWheel.setTarget(1);
        luckyWheel.addWheelItems(wheelShowedItems);
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
        addSliceButton = findViewById(R.id.add_slice_button);
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
        for(int index = 0; index <wheelItems.size(); index++)
        {
            listCardView[index].setBackgroundColor(wheelItems.get(index).color);
            listTextInCard[index].setText(wheelItems.get(index).text);
            int finalIndex = index;
            listCardView[index].setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openEditSliceDialog(Gravity.CENTER, indexSliceCard.get(finalIndex));
                        }
                    }
            );
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
                        ArrayList<WheelItem> cache  = new ArrayList<>();
                        for(int i = 1 ; i <= repeat ; i++)
                        {
                            cache.addAll(wheelItems);
                        }
                        wheelShowedItems = cache;
                        luckyWheel.addWheelItems(wheelShowedItems);
                        sliceRepeatView.setText(String.valueOf(repeat));
                    }
                }
        );
        textSizeSlider.addOnChangeListener(
                new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                        textSize = (int) textSizeSlider.getValue();
                        luckyWheel.setTextSize(textSize);
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
                        Collections.shuffle(wheelShowedItems);
                        luckyWheel.addWheelItems(wheelShowedItems);
                        createSliceCard();
                    }
                }
        );
        checkButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LuckyWheelSource.fairMode.set(indexCurrentWheel, fairMode);
                        LuckyWheelSource.textSize.set(indexCurrentWheel, textSize);
                        LuckyWheelSource.spinTime.set(indexCurrentWheel, spinTime);
                        LuckyWheelSource.repeat.set(indexCurrentWheel, repeat);
                        ArrayList<String> mixedContents = new ArrayList<>();
                        for(int i = 0 ; i< wheelShowedItems.size(); i++)
                        {
                            mixedContents.add(wheelShowedItems.get(i).text);
                        }
                        ArrayList<String> contents = new ArrayList<>();
                        for(int i = 0 ; i< wheelItems.size(); i++)
                        {
                            contents.add(wheelItems.get(i).text);
                        }
                        LuckyWheelSource.listColor.set(indexCurrentWheel, colors);
                        LuckyWheelSource.mixedContentItem.set(indexCurrentWheel, mixedContents);
                        LuckyWheelSource.listContent.set(indexCurrentWheel, contents);
                        Intent intentBack = new Intent();
                        setResult(Activity.RESULT_OK, intentBack);
                        finish();
                    }
                }
        );
        addSliceButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                }
        );
    }
    @SuppressLint("ClickableViewAccessibility")
    void openEditSliceDialog(int gravity, int index)
    {
        Dialog editDialog = new Dialog(this);
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editDialog.setContentView(R.layout.activity_lucky_wheel_add_slice);

        Window window = editDialog.getWindow();
        if(window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editDialog.setCancelable(false);
        WindowManager.LayoutParams params = editDialog.getWindow().getAttributes();
        params.gravity = gravity;
        window.setAttributes(params);

        editDialog.show();

        ImageButton deleteButton = editDialog.findViewById(R.id.delete_slice_button);
        ImageButton cancelButton = editDialog.findViewById(R.id.cancel_button);
        ImageButton acceptButton = editDialog.findViewById(R.id.oke_button);
        CardView cardView = editDialog.findViewById(R.id.cardView);
        TextView textInCard = editDialog.findViewById(R.id.text_in_card);
        EditText content = editDialog.findViewById(R.id.slice_content);
        EditText color = editDialog.findViewById(R.id.slice_color);
        Drawable mDrawable = getResources().getDrawable(R.drawable.ic_circle_color);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(wheelItems.get(index).color, PorterDuff.Mode.SRC_IN));
        changeColor = wheelItems.get(index).color;

        color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
        textInCard.setText(wheelItems.get(index).text);
        cardView.setBackgroundColor(wheelItems.get(index).color);
        content.setText(wheelItems.get(index).text);

        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editDialog.cancel();
                    }
                }
        );
        color.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            if(event.getRawX() >= (color.getRight() - color.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                new ColorPickerPopup
                                        .Builder(EditLuckyWheelActivity.this)
                                        .initialColor(wheelItems.get(index).color)
                                        .enableAlpha(true)
                                        .enableBrightness(true)
                                        .okTitle("Choose")
                                        .cancelTitle("Cancel")
                                        .showValue(true)
                                        .showIndicator(true)
                                        .build()
                                        .show(view, new ColorPickerPopup.ColorPickerObserver() {
                                            @Override
                                            public void onColorPicked(int cl) {
                                                cardView.setBackgroundColor(cl);
                                                Drawable mDrawable = getResources().getDrawable(R.drawable.ic_circle_color);
                                                mDrawable.setColorFilter(new
                                                        PorterDuffColorFilter(cl, PorterDuff.Mode.SRC_IN));
                                                color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
                                                changeColor= cl;
                                            }
                                        });
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );
        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0 ; i < wheelShowedItems.size();i++)
                        {
                            if(wheelShowedItems.get(i).text.equals(wheelItems.get(index).text))
                            {
                                wheelShowedItems.remove(i);
                            }
                        }
                        wheelItems.remove(index);
                        colors.remove(index);
                        createSliceCard();
                        luckyWheel.addWheelItems(wheelShowedItems);
                        editDialog.cancel();
                    }
                }
        );
        acceptButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(content.getText().toString() == null || content.getText().toString() == "")
                        {
                            return;
                        }
                        for(int i = 0 ; i< wheelShowedItems.size(); i++)
                        {
                            if(wheelShowedItems.get(i).text.equals(wheelItems.get(index).text))
                            {
                                wheelShowedItems.get(i).setColor(changeColor);
                                wheelShowedItems.get(i).setText(content.getText().toString());
                                System.out.println("change");
                            }
                        }
                        wheelItems.get(index).setColor(changeColor);
                        wheelItems.get(index).setText(content.getText().toString());
                        colors.set(index,  String.format("#%06X", (0xFFFFFF &changeColor)));

                        createSliceCard();
                        luckyWheel.addWheelItems(wheelShowedItems);
                        editDialog.cancel();
                    }
                }
        );
    }
}

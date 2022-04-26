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
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

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

import top.defaults.colorpicker.ColorPickerPopup;
import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;

public class AddNewLuckyWheelActivity  extends AppCompatActivity {
    LuckyWheel luckyWheel;
    List<WheelItem> wheelItems = new ArrayList<>();
    List<WheelItem> wheelShowedItems = new ArrayList<>();
    CardView[] listCardView = new CardView[12];
    TextView[] listTextInCard = new TextView[12];
    ImageButton backButton;
    ImageButton checkButton;
    ImageButton addButton;
    ImageButton mixButton;
    Slider textSizeSlider;
    Slider sliceRepeatSlider;
    Slider spinTimeSlider;
    Switch fairModeSwitch;
    EditText titleText;
    TextView textSizeView;
    TextView sliceRepeatView;
    TextView spinTimeView;
    ArrayList<Integer> indexList = new ArrayList<>();
    int textSize=16;
    int repeat =1;
    int spinTime = 5;
    boolean fairMode = false;
    int changeColor ;
    int defaultSLiceColor = -4955036;
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
        luckyWheel.addWheelItems(wheelShowedItems);
        backButton = findViewById(R.id.bb_add_wheel);
        textSizeView = findViewById(R.id.text_size_add_lucky_wheel);
        sliceRepeatView = findViewById(R.id.slide_repeat_add_lucky_wheel);
        spinTimeView = findViewById(R.id.spin_time_add_lucky_wheel);
        textSizeSlider = findViewById(R.id.text_size_slider_add_lucky_wheel);
        spinTimeSlider = findViewById(R.id.spin_time_slide_add_lucky_wheel);
        sliceRepeatSlider = findViewById(R.id.slice_repeat_slider_add_lucky_wheel);
        fairModeSwitch = findViewById(R.id.fair_mode_add_lucky_wheel);
        addButton = findViewById(R.id.add_wheel_add_slice_button);
        checkButton = findViewById(R.id.oke_button);
        titleText = findViewById(R.id.wheel_title);
        textSizeView.setText(String.valueOf(textSize));
        sliceRepeatView.setText(String.valueOf(repeat));
        spinTimeView.setText(String.valueOf(spinTime));
        fairModeSwitch.setChecked(fairMode);
        sliceRepeatSlider.setValue(repeat);
        spinTimeSlider.setValue(spinTime);
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
        listCardView[9] = findViewById(R.id.add_wheel_cardView10);
        listCardView[10] = findViewById(R.id.add_wheel_cardView11);
        listCardView[11] = findViewById(R.id.add_wheel_cardView12);
        listTextInCard[0] = findViewById(R.id.add_wheel_textcard1);
        listTextInCard[1] = findViewById(R.id.add_wheel_textcard2);
        listTextInCard[2] = findViewById(R.id.add_wheel_textcard3);
        listTextInCard[3] = findViewById(R.id.add_wheel_textcard4);
        listTextInCard[4] = findViewById(R.id.add_wheel_textcard5);
        listTextInCard[5] = findViewById(R.id.add_wheel_textcard6);
        listTextInCard[6] = findViewById(R.id.add_wheel_textcard7);
        listTextInCard[7] = findViewById(R.id.add_wheel_textcard8);
        listTextInCard[8] = findViewById(R.id.add_wheel_textcard9);
        listTextInCard[9] = findViewById(R.id.add_wheel_textcard10);
        listTextInCard[10] = findViewById(R.id.add_wheel_textcard11);
        listTextInCard[11] = findViewById(R.id.add_wheel_textcard12);
        createSliceCard();
    }

    private void createSliceCard() {
        for(int i = 0 ; i < listCardView.length ; i++)
        {
            listCardView[i].setVisibility(View.INVISIBLE);
            listTextInCard[i].setVisibility(View.INVISIBLE);
            indexList.add(i);
        }
        for(int i = 0; i <wheelItems.size(); i++)
        {
            listCardView[i].setVisibility(View.VISIBLE);
            listTextInCard[i].setVisibility(View.VISIBLE);
            listCardView[i].setCardBackgroundColor(wheelItems.get(i).color);
            listTextInCard[i].setText(wheelItems.get(i).text);
            final int index = i;
            listCardView[i].setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openEditSliceDialog(Gravity.CENTER, indexList.get(index));
                        }
                    }
            );
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
                        Collections.shuffle(wheelShowedItems);
                        luckyWheel.addWheelItems(wheelShowedItems);
                        createSliceCard();
                    }
                }
        );
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(wheelItems.size() <= 12)
                        {
                            openAddSliceDialog(Gravity.CENTER);
                        }
                        else {
                            Toast.makeText(AddNewLuckyWheelActivity.this,"Số lượng Slice đã lớn nhất",Toast.LENGTH_LONG).show();
                        };
                    }
                }
        );
        checkButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> contents = new ArrayList<>();
                        ArrayList<String> colors = new ArrayList<>();
                        for(int i = 0; i < wheelItems.size() ; i++)
                        {
                            contents.add(wheelItems.get(i).text);
                            colors.add(String.format("#%06X", (0xFFFFFF & wheelItems.get(i).color)));
                        }
                        ArrayList<String> mixedContents = new ArrayList<>();
                        for(int i = 0; i < wheelShowedItems.size() ; i++)
                        {
                            mixedContents.add(wheelShowedItems.get(i).text);
                        }
                        LuckyWheelSource.listTitle.add(titleText.getText().toString());
                        LuckyWheelSource.textSize.add(textSize);
                        LuckyWheelSource.fairMode.add(fairMode);
                        LuckyWheelSource.repeat.add(repeat);
                        LuckyWheelSource.spinTime.add(spinTime);
                        LuckyWheelSource.listContent.add(contents);
                        LuckyWheelSource.mixedContentItem.add(mixedContents);
                        LuckyWheelSource.listColor.add(colors);
                        Intent intentBack = new Intent();
                        setResult(Activity.RESULT_OK, intentBack);
                        finish();
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
        wheelShowedItems.addAll(wheelItems);
    }

    @SuppressLint("ClickableViewAccessibility")
    void openEditSliceDialog(int gravity, int index)
    {
        Dialog editDialog = new Dialog(this);
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editDialog.setContentView(R.layout.activity_lucky_wheel_edit_slice);

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
        cardView.setCardBackgroundColor(wheelItems.get(index).color);
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
                                        .Builder(AddNewLuckyWheelActivity.this)
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
                        if(wheelItems.size() <= 2)
                        {
                            Toast.makeText(AddNewLuckyWheelActivity.this, "Số SLice đã đạt mức nhỏ nhất", Toast.LENGTH_LONG).show();
                            return;
                        }
                        for(int i = 0 ; i < wheelShowedItems.size();i++)
                        {
                            if(wheelShowedItems.get(i).text.equals(wheelItems.get(index).text))
                            {
                                wheelShowedItems.remove(i);
                            }
                        }
                        wheelItems.remove(index);
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
                            }
                        }
                        wheelItems.get(index).setColor(changeColor);
                        wheelItems.get(index).setText(content.getText().toString());
                        createSliceCard();
                        luckyWheel.addWheelItems(wheelShowedItems);
                        editDialog.cancel();
                    }
                }
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    void openAddSliceDialog(int gravity)
    {
        Dialog addDialog = new Dialog(this);
        addDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addDialog.setContentView(R.layout.activity_lucky_wheel_add_slice);

        Window window = addDialog.getWindow();
        if(window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.setCancelable(false);
        WindowManager.LayoutParams params = addDialog.getWindow().getAttributes();
        params.gravity = gravity;
        window.setAttributes(params);

        addDialog.show();

        ImageButton cancelButton = addDialog.findViewById(R.id.cancel_button);
        ImageButton acceptButton = addDialog.findViewById(R.id.oke_button);
        CardView cardView = addDialog.findViewById(R.id.cardView);
        TextView textInCard = addDialog.findViewById(R.id.text_in_card);
        EditText content = addDialog.findViewById(R.id.slice_content);
        EditText color = addDialog.findViewById(R.id.slice_color);
        Drawable mDrawable = getResources().getDrawable(R.drawable.ic_circle_color);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(defaultSLiceColor, PorterDuff.Mode.SRC_IN));

        color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
        cardView.setCardBackgroundColor(defaultSLiceColor);
        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addDialog.cancel();
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
                                        .Builder(AddNewLuckyWheelActivity.this)
                                        .initialColor(defaultSLiceColor)
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
                                                @SuppressLint("UseCompatLoadingForDrawables")
                                                Drawable mDrawable = getResources().getDrawable(R.drawable.ic_circle_color);
                                                mDrawable.setColorFilter(new
                                                        PorterDuffColorFilter(cl, PorterDuff.Mode.SRC_IN));
                                                color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
                                                defaultSLiceColor = cl;
                                            }
                                        });
                                return true;
                            }
                        }
                        return false;
                    }
                }
        );

        acceptButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wheelItems.add(
                                new WheelItem(defaultSLiceColor, BitmapFactory.decodeResource(getResources(),
                                        R.drawable.small_nails_icons) , content.getText().toString()));
                        for(int i = 0 ; i < repeat ; i++)
                        {
                            wheelShowedItems.add(wheelItems.get(wheelItems.size()-1));
                        }
                        createSliceCard();
                        luckyWheel.addWheelItems(wheelShowedItems);
                        addDialog.cancel();
                        defaultSLiceColor = -4955036;
                    }
                }
        );
        content.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        textInCard.setText(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                }
        );
    }
}

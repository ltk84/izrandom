package uit.itszoo.izrandom.random_module.lucky_wheel.add_lucky_wheel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import top.defaults.colorpicker.ColorPickerPopup;
import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.adapter.SliceToWheelItem;
import uit.itszoo.izrandom.random_module.lucky_wheel.edit_lucky_wheel.EditLuckyWheelActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class AddNewLuckyWheelActivity extends AppCompatActivity implements AddLuckyWheelContract.View {
    final int MIN_NUMBER_SLICE = 2;
    final int DEFAULT_SELECTED_WHEEL_ITEM = 1;
    final int DEFAULT_SLICE_COLOR = -4955036;
    final int DEFAULT_TEXT_COLOR = -1;

    LuckyWheel luckyWheel;
    ArrayList<WheelItem> wheelItems = new ArrayList<>();
    ArrayList<WheelItem> originWheelItems = new ArrayList<>();

    AddLuckyWheelContract.Present present;
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
    LinearLayout sliceHolder;

    int textSize = 36;
    int repeat = 1;
    int spinTime = 5;
    String title = "Làm gì bây giờ?";
    boolean fairMode = false;

    int changeColor;
    int changeTextColor;
    int defaultSliceColor = DEFAULT_SLICE_COLOR;
    int defaultTextColor = DEFAULT_TEXT_COLOR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_lucky_wheel);

        present = new AddLuckyWheelPresenter(getApplicationContext(), this);
        setPresenter(present);

        generateWheelItems();
        initView();
        setListenerForView();
    }

    void initView() {
        luckyWheel = findViewById(R.id.lw_add_wheel);
        luckyWheel.setTarget(DEFAULT_SELECTED_WHEEL_ITEM);
        luckyWheel.addWheelItems(wheelItems);

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
        sliceHolder = findViewById(R.id.slice_holder);

        createSliceCard();
    }

    private void createSliceCard() {

        if (originWheelItems != null) {
            if (sliceHolder != null) {
                sliceHolder.removeAllViews();
            }
            Resources r = getApplicationContext().getResources();
            int marginBottomPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    7,
                    r.getDisplayMetrics()
            );
            int cvRadiusPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    5,
                    r.getDisplayMetrics()
            );

            for (int i = 0; i < originWheelItems.size(); i++) {
                CardView cardView = new CardView(getApplicationContext());
                LinearLayout.LayoutParams cvParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                cvParams.setMargins(0, 0, 0, marginBottomPx);
                cardView.setLayoutParams(cvParams);
                cardView.setRadius(cvRadiusPx);

                TextView textView = new TextView(getApplicationContext());
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                textView.setLayoutParams(tvParams);
                textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(), R.font.be_vietnam_pro));
                textView.setTextSize(16);
                textView.setPadding(36, 16,16,16);
                textView.setTextColor(originWheelItems.get(i).textColor);
                cardView.addView(textView);

                cardView.setCardBackgroundColor(originWheelItems.get(i).color);
                textView.setText(originWheelItems.get(i).text);

                final int finalIndex = i;
                final WheelItem uiSlice = originWheelItems.get(finalIndex);
                cardView.setOnClickListener(
                        view -> openEditSliceDialog(originWheelItems.get(finalIndex))
                );
                sliceHolder.addView(cardView);
            }
        }
    }

    private void setListenerForView() {
        titleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                title = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        backButton.setOnClickListener(
                view -> onBackPressed()
        );

        sliceRepeatSlider.addOnChangeListener(
                (slider, value, fromUser) -> {
                    repeat = (int) sliceRepeatSlider.getValue();
                    sliceRepeatView.setText(String.valueOf(repeat));

                    ArrayList<WheelItem> cache = new ArrayList<>();
                    for (int i = 1; i <= repeat; i++) {
                        cache.addAll(originWheelItems);
                    }

                    wheelItems = cache;
                    luckyWheel.addWheelItems(wheelItems);
                }
        );

        textSizeSlider.addOnChangeListener(
                (slider, value, fromUser) -> {
                    textSize = (int) textSizeSlider.getValue();
                    luckyWheel.setTextSize(textSize);
                    textSizeView.setText(String.valueOf(textSize));
                }
        );

        spinTimeSlider.addOnChangeListener(
                (slider, value, fromUser) -> {
                    spinTime = (int) spinTimeSlider.getValue();
                    spinTimeView.setText(String.valueOf(spinTime));
                }
        );

        fairModeSwitch.setOnCheckedChangeListener(
                (compoundButton, b) -> fairMode = fairModeSwitch.isChecked()
        );

        mixButton.setOnClickListener(
                view -> {
                    Collections.shuffle(originWheelItems);
                    wheelItems.clear();
                    for (int i = 0; i < repeat; i++) {
                        wheelItems.addAll(originWheelItems);
                    }

                    luckyWheel.addWheelItems(wheelItems);
                    createSliceCard();
                }
        );

        addButton.setOnClickListener(
                view -> {
                    openAddSliceDialog();
                }
        );

        checkButton.setOnClickListener(
                view -> {
                    ArrayList<String> ids = new ArrayList<>();
                    for (int i = 0; i < originWheelItems.size(); i++) {
                        ids.add(UUID.randomUUID().toString());
                    }

                    LuckyWheelData newWheelData = new LuckyWheelData(UUID.randomUUID().toString(),
                            titleText.getText().toString(), textSize, repeat, spinTime, fairMode);

                    present.insertWheel(newWheelData);

                    List<LuckyWheelSlice> newWheelSlices =
                            SliceToWheelItem.convertWheelItemsToSlices(ids, originWheelItems, newWheelData.getId());

                    for (LuckyWheelSlice slice : newWheelSlices) {
                        present.insertSlice(slice);
                    }

                    finish();
                }
        );
    }

    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        originWheelItems = new ArrayList<>();

        originWheelItems.add(new WheelItem(Color.parseColor("#ffe05f"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 1"));
        originWheelItems.add(new WheelItem(Color.parseColor("#ffaa64"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 2"));
        originWheelItems.add(new WheelItem(Color.parseColor("#ff534a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 3"));
        originWheelItems.add(new WheelItem(Color.parseColor("#aadb6b"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 4"));
        originWheelItems.add(new WheelItem(Color.parseColor("#ffe05f"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 5"));
        originWheelItems.add(new WheelItem(Color.parseColor("#ffaa64"), BitmapFactory.decodeResource(getResources(),
                R.drawable.small_nails_icons), "Name 6"));

        for (int i = 0; i < repeat; i++) {
            wheelItems.addAll(originWheelItems);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    void openEditSliceDialog(WheelItem uiSlice) {
        Dialog editDialog = new Dialog(this);
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editDialog.setContentView(R.layout.activity_lucky_wheel_edit_slice);

        Window window = editDialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editDialog.setCancelable(false);
        WindowManager.LayoutParams params = editDialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        editDialog.show();

        ImageButton deleteButton = editDialog.findViewById(R.id.delete_slice_button);
        ImageButton cancelButton = editDialog.findViewById(R.id.cancel_button);
        ImageButton acceptButton = editDialog.findViewById(R.id.oke_button);
        CardView cardView = editDialog.findViewById(R.id.cardView);
        TextView textInCard = editDialog.findViewById(R.id.text_in_card);
        textInCard.setPadding(36, 16,36,16);
        EditText content = editDialog.findViewById(R.id.slice_content);
        EditText color = editDialog.findViewById(R.id.slice_color);
        EditText textColor = editDialog.findViewById(R.id.text_color);
        Drawable mDrawable = getResources().getDrawable(R.drawable.ic_circle_color);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(uiSlice.color, PorterDuff.Mode.SRC_IN));
        changeColor = uiSlice.color;

        Drawable mDrawable1 = getResources().getDrawable(R.drawable.ic_circle_color_2);
        mDrawable1.setColorFilter(new
                PorterDuffColorFilter(uiSlice.textColor, PorterDuff.Mode.SRC_IN));
        changeTextColor = uiSlice.textColor;

        color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
        textColor.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable1, null);
        textInCard.setText(uiSlice.text);
        textInCard.setTextColor(uiSlice.textColor);
        cardView.setCardBackgroundColor(uiSlice.color);
        content.setText(uiSlice.text);

        cancelButton.setOnClickListener(
                view -> editDialog.cancel()
        );

        color.setOnTouchListener(
                (view, event) -> {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (color.getRight() - color.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            new ColorPickerPopup
                                    .Builder(AddNewLuckyWheelActivity.this)
                                    .initialColor(uiSlice.color)
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
                                            cardView.setCardBackgroundColor(cl);
                                            Drawable mDrawable1 = getResources().getDrawable(R.drawable.ic_circle_color);
                                            mDrawable1.setColorFilter(new
                                                    PorterDuffColorFilter(cl, PorterDuff.Mode.SRC_IN));
                                            color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable1, null);
                                            changeColor = cl;
                                        }
                                    });
                            return true;
                        }
                    }
                    return false;
                }
        );

        textColor.setOnTouchListener(
                (view, event) -> {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (textColor.getRight() - textColor.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            new ColorPickerPopup
                                    .Builder(AddNewLuckyWheelActivity.this)
                                    .initialColor(uiSlice.textColor)
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
                                            textInCard.setTextColor(cl);
                                            Drawable mDrawable1 = getResources().getDrawable(R.drawable.ic_circle_color_2);
                                            mDrawable1.setColorFilter(new
                                                    PorterDuffColorFilter(cl, PorterDuff.Mode.SRC_IN));
                                            textColor.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable1, null);
                                            changeTextColor = cl;
                                        }
                                    });
                            return true;
                        }
                    }
                    return false;
                }
        );

        deleteButton.setOnClickListener(
                view -> {
                    new AlertDialog.Builder(this)
                            .setTitle("Hộp thư xác nhận")
                            .setMessage("Bạn có chắc muốn xóa slice này không?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (wheelItems.size() <= MIN_NUMBER_SLICE) {
                                        Toast.makeText(AddNewLuckyWheelActivity.this, "Số SLice đã đạt mức nhỏ nhất", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    wheelItems.removeIf(wheelItem -> wheelItem.text.equals(uiSlice.text) && wheelItem.color == uiSlice.color);
                                    originWheelItems.removeIf(wheelItem -> wheelItem.text.equals(uiSlice.text) && wheelItem.color == uiSlice.color);

                                    createSliceCard();
                                    luckyWheel.addWheelItems(wheelItems);
                                    editDialog.cancel();
                                }})
                            .setNegativeButton("Hủy", null).show();

                }
        );

        acceptButton.setOnClickListener(
                view -> {
                    String contentValue = content.getText().toString();
                    if (contentValue.isEmpty()) {
                        return;
                    }

                    String beforeUpdateSliceText = uiSlice.text;

                    for (int i = 0; i < originWheelItems.size(); i++) {
                        if (originWheelItems.get(i).text.equals(beforeUpdateSliceText)) {
                            originWheelItems.get(i).setText(content.getText().toString());
                            originWheelItems.get(i).setColor(changeColor);
                            originWheelItems.get(i).setTextColor(changeTextColor);
                        }
                    }

                    for (int i = 0; i < wheelItems.size(); i++) {
                        if (wheelItems.get(i).text.equals(beforeUpdateSliceText)) {
                            wheelItems.get(i).setColor(changeColor);
                            wheelItems.get(i).setText(contentValue);
                            wheelItems.get(i).setTextColor(changeTextColor);
                        }
                    }

                    createSliceCard();
                    luckyWheel.addWheelItems(wheelItems);
                    editDialog.cancel();
                }
        );

        content.addTextChangedListener(new TextWatcher() {
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
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    void openAddSliceDialog() {
        Dialog addDialog = new Dialog(this);
        addDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addDialog.setContentView(R.layout.activity_lucky_wheel_add_slice);

        Window window = addDialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.setCancelable(false);
        WindowManager.LayoutParams params = addDialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        addDialog.show();

        ImageButton cancelButton = addDialog.findViewById(R.id.cancel_button);
        ImageButton acceptButton = addDialog.findViewById(R.id.oke_button);
        CardView cardView = addDialog.findViewById(R.id.cardView);
        TextView textInCard = addDialog.findViewById(R.id.text_in_card);
        textInCard.setPadding(36, 16,36,16);
        EditText content = addDialog.findViewById(R.id.slice_content);
        EditText color = addDialog.findViewById(R.id.slice_color);
        EditText textColor = addDialog.findViewById(R.id.text_color);
        Drawable mDrawable = getResources().getDrawable(R.drawable.ic_circle_color);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(defaultSliceColor, PorterDuff.Mode.SRC_IN));

        color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
        cardView.setCardBackgroundColor(defaultSliceColor);


        Drawable mDrawable1 = getResources().getDrawable(R.drawable.ic_circle_color_2);
        mDrawable1.setColorFilter(new
                PorterDuffColorFilter(defaultTextColor, PorterDuff.Mode.SRC_IN));
        textColor.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable1, null);
        textInCard.setTextColor(defaultTextColor);

        cancelButton.setOnClickListener(
                view -> addDialog.cancel()
        );

        color.setOnTouchListener(
                (view, event) -> {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (color.getRight() - color.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            new ColorPickerPopup
                                    .Builder(AddNewLuckyWheelActivity.this)
                                    .initialColor(defaultSliceColor)
                                    .enableAlpha(true)
                                    .enableBrightness(true)
                                    .okTitle("Chọn")
                                    .cancelTitle("Hủy bỏ")
                                    .showValue(true)
                                    .showIndicator(true)
                                    .build()
                                    .show(view, new ColorPickerPopup.ColorPickerObserver() {
                                        @Override
                                        public void onColorPicked(int cl) {
                                            cardView.setCardBackgroundColor(cl);
                                            @SuppressLint("UseCompatLoadingForDrawables")
                                            Drawable mDrawable1 = getResources().getDrawable(R.drawable.ic_circle_color);
                                            mDrawable1.setColorFilter(new
                                                    PorterDuffColorFilter(cl, PorterDuff.Mode.SRC_IN));
                                            color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable1, null);
                                            defaultSliceColor = cl;
                                        }
                                    });
                            return true;
                        }
                    }
                    return false;
                }
        );

        textColor.setOnTouchListener(
                (view, event) -> {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (textColor.getRight() - textColor.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            new ColorPickerPopup
                                    .Builder(AddNewLuckyWheelActivity.this)
                                    .initialColor(defaultTextColor)
                                    .enableAlpha(true)
                                    .enableBrightness(true)
                                    .okTitle("Chọn")
                                    .cancelTitle("Hủy bỏ")
                                    .showValue(true)
                                    .showIndicator(true)
                                    .build()
                                    .show(view, new ColorPickerPopup.ColorPickerObserver() {
                                        @Override
                                        public void onColorPicked(int cl) {
                                            textInCard.setTextColor(cl);
                                            @SuppressLint("UseCompatLoadingForDrawables")
                                            Drawable mDrawable1 = getResources().getDrawable(R.drawable.ic_circle_color_2);
                                            mDrawable1.setColorFilter(new
                                                    PorterDuffColorFilter(cl, PorterDuff.Mode.SRC_IN));
                                            textColor.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable1, null);
                                            defaultTextColor = cl;
                                        }
                                    });
                            return true;
                        }
                    }
                    return false;
                }
        );

        acceptButton.setOnClickListener(
                view -> {
                    WheelItem newSlice = new WheelItem(defaultSliceColor, BitmapFactory.decodeResource(getResources(),
                            R.drawable.small_nails_icons), content.getText().toString(), defaultTextColor);
                    originWheelItems.add(newSlice);

                    wheelItems.clear();
                    for (int i = 0; i < repeat; i++) {
                        wheelItems.addAll(originWheelItems);
                    }

                    createSliceCard();
                    luckyWheel.addWheelItems(wheelItems);
                    addDialog.cancel();
                    defaultSliceColor = DEFAULT_SLICE_COLOR;
                    defaultTextColor = DEFAULT_TEXT_COLOR;
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

    @Override
    public void setPresenter(AddLuckyWheelContract.Present presenter) {
        this.present = presenter;
    }
}

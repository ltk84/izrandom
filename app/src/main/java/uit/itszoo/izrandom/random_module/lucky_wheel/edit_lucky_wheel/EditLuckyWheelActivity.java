package uit.itszoo.izrandom.random_module.lucky_wheel.edit_lucky_wheel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
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
import java.util.stream.Collectors;

import top.defaults.colorpicker.ColorPickerPopup;
import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.adapter.SliceToWheelItem;
import uit.itszoo.izrandom.random_module.lucky_wheel.add_lucky_wheel.AddNewLuckyWheelActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom.LuckyWheelCustomActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class EditLuckyWheelActivity extends AppCompatActivity implements EditLuckyWheelContract.View {
    final int MIN_NUMBER_SLICE = 2;
    final int MIN_NUMBER_WHEEL = 1;
    final int DEFAULT_SELECTED_WHEEL_ITEM = 1;
    final int DEFAULT_SLICE_COLOR = -4955036;
    final int DEFAULT_TEXT_COLOR = -1;

    LuckyWheel luckyWheel;
    ArrayList<WheelItem> wheelItems = new ArrayList<>();
    ArrayList<WheelItem> originWheelItems = new ArrayList<>();

    EditLuckyWheelContract.Presenter presenter;
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
    EditText ttText;
    LinearLayout sliceHolder;

    int textSize = 1;
    int repeat = 1;
    int spinTime;
    String title = "";
    boolean fairMode = false;

    int changeColor;
    int changeTextColor;
    int defaultSliceColor = DEFAULT_SLICE_COLOR;
    int defaultTextColor = DEFAULT_TEXT_COLOR;

    LuckyWheelData currentWheelData;
    List<LuckyWheelSlice> currentWheelSlices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel_edit);

        presenter = new EditLuckyWheelPresenter(getApplicationContext(), this);
        setPresenter(presenter);

        currentWheelData = (LuckyWheelData) getIntent().getSerializableExtra(LuckyWheelCustomActivity.CURRENT_WHEEL);
        currentWheelSlices = presenter.getSlicesByWheelID(currentWheelData.getId());

        textSize = currentWheelData.getTextSize();
        repeat = currentWheelData.getSliceRepeat();
        spinTime = currentWheelData.getSpinTime();
        fairMode = currentWheelData.isFairMode();
        title = currentWheelData.getTitle();

        generateWheelItems();
        initView();
        setListenerForView();
    }


    private void generateWheelItems() {
        originWheelItems = SliceToWheelItem.convertSlicesToWheelItems(getResources(), currentWheelSlices);

        List<LuckyWheelSlice> slicesWithRepeat = new ArrayList<>();
        for (int i = 0; i < repeat; i++) {
            slicesWithRepeat.addAll(currentWheelSlices);
        }

        wheelItems = SliceToWheelItem.convertSlicesToWheelItems(getResources(), slicesWithRepeat);
    }

    void initView() {
        luckyWheel = findViewById(R.id.edit_lucky_wheel);
        luckyWheel.setTarget(DEFAULT_SELECTED_WHEEL_ITEM);
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
        ttText = findViewById(R.id.wheel_title);
        sliceHolder = findViewById(R.id.slice_holder);

        ttText.setText(title);
        textSizeView.setText(String.valueOf(currentWheelData.getTextSize()));
        sliceRepeatView.setText(String.valueOf(currentWheelData.getSliceRepeat()));
        spinTimeView.setText(String.valueOf(currentWheelData.getSpinTime()));
        fairModeSwitch.setChecked(currentWheelData.isFairMode());
        sliceRepeatSlider.setValue(currentWheelData.getSliceRepeat());
        spinTimeSlider.setValue(currentWheelData.getSpinTime());
        textSizeSlider.setValue(currentWheelData.getTextSize());
        mixButton = findViewById(R.id.mix_button);
        addSliceButton = findViewById(R.id.add_slice_button);

        createSliceCard();
    }

    void createSliceCard() {
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
                        view -> openEditSliceDialog(currentWheelSlices.get(finalIndex).getId(), uiSlice)
                );
                sliceHolder.addView(cardView);
            }
        }
    }

    void setListenerForView() {
        ttText.addTextChangedListener(new TextWatcher() {
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

        sliceRepeatSlider.addOnChangeListener((slider, value, fromUser) -> {
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
                    // Có vẻ không có tác dụng
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
                (compoundButton, b) -> {
                    fairMode = fairModeSwitch.isChecked();
                }
        );

        mixButton.setOnClickListener(
                view -> {
                    Collections.shuffle(currentWheelSlices);
                    for (int i = 0; i < currentWheelSlices.size(); i++) {
                        currentWheelSlices.get(i).setNumberOrder(i);
                    }

                    originWheelItems = SliceToWheelItem.convertSlicesToWheelItems(getResources(), currentWheelSlices);
                    wheelItems.clear();
                    for (int i = 0; i < repeat; i++) {
                        wheelItems.addAll(originWheelItems);
                    }

                    luckyWheel.addWheelItems(wheelItems);
                    createSliceCard();
                }
        );

        checkButton.setOnClickListener(
                view -> {

                    List<LuckyWheelSlice> slicesSource = presenter.getSlicesByWheelID(currentWheelData.getId());
                    List<LuckyWheelSlice> removedSlices = new ArrayList<>();

                    for (LuckyWheelSlice slice : slicesSource) {
                        if (!currentWheelSlices.contains(slice)) {
                            try {
                                LuckyWheelSlice s = (LuckyWheelSlice) slice.clone();
                                removedSlices.add(s);
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    presenter.deleteSliceByIDs(removedSlices.stream().map(slice -> slice.getId()).collect(Collectors.toList()));

                    // check xem có slice nào mới được add không
                    for (LuckyWheelSlice slice : currentWheelSlices) {
                        boolean sliceExist = presenter.checkIfSliceExist(slice.getId());
                        if (sliceExist) {
                            presenter.updateSlice(slice);
                        } else {
                            presenter.addSlice(slice);
                        }
                    }

                    currentWheelData.setFairMode(fairMode);
                    currentWheelData.setTextSize(textSize);
                    currentWheelData.setSliceRepeat(repeat);
                    currentWheelData.setTitle(title);

                    presenter.updateWheel(currentWheelData);

                    finish();
                }
        );

        addSliceButton.setOnClickListener(
                view -> {
                    openAddSliceDialog();
                }
        );

        deleteButton.setOnClickListener(
                view -> {
                    if (presenter.getNumberOfWheel() <= MIN_NUMBER_WHEEL) {
                        Toast.makeText(EditLuckyWheelActivity.this, "Đây là vòng quay duy nhất", Toast.LENGTH_LONG).show();
                        return;
                    }

                    presenter.deleteWheel(currentWheelData);

                    finish();
                }
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    void openEditSliceDialog(String sliceID, WheelItem uiSlice) {
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

        mDrawable.setColorFilter(new PorterDuffColorFilter(uiSlice.color, PorterDuff.Mode.SRC_IN));

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
                                    .Builder(EditLuckyWheelActivity.this)
                                    .initialColor(uiSlice.color)
                                    .enableAlpha(true)
                                    .enableBrightness(true)
                                    .okTitle("Chọn")
                                    .cancelTitle(" Hủy bỏ")
                                    .showValue(true)
                                    .showIndicator(true)
                                    .build()
                                    .show(view, new ColorPickerPopup.ColorPickerObserver() {
                                        @Override
                                        public void onColorPicked(int cl) {
                                            cardView.setBackgroundColor(cl);
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
                                    .Builder(EditLuckyWheelActivity.this)
                                    .initialColor(uiSlice.color)
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
                    if (wheelItems.size() <= MIN_NUMBER_SLICE) {
                        Toast.makeText(EditLuckyWheelActivity.this, "Số SLice đã đạt mức nhỏ nhất", Toast.LENGTH_LONG).show();
                        return;
                    }

                    wheelItems.removeIf(wheelItem -> wheelItem.text.equals(uiSlice.text) && wheelItem.color == uiSlice.color);
                    originWheelItems.removeIf(wheelItem -> wheelItem.text.equals(uiSlice.text) && wheelItem.color == uiSlice.color);
                    currentWheelSlices.removeIf(slice -> slice.getId().equals(sliceID));

                    createSliceCard();
                    luckyWheel.addWheelItems(wheelItems);
                    editDialog.cancel();
                }
        );

        acceptButton.setOnClickListener(
                view -> {
                    if (content.getText().toString().isEmpty()) {
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

                    currentWheelSlices.forEach(slice -> {
                        if (slice.getId().equals(sliceID)) {
                            slice.setColor(String.format("#%06X", (0xFFFFFF & changeColor)));
                            slice.setName(content.getText().toString());
                        }
                    });

                    for (int i = 0; i < wheelItems.size(); i++) {
                        if (wheelItems.get(i).text.equals(beforeUpdateSliceText)) {
                            wheelItems.get(i).setColor(changeColor);
                            wheelItems.get(i).setText(content.getText().toString());
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
        mDrawable.setColorFilter(new PorterDuffColorFilter(defaultSliceColor, PorterDuff.Mode.SRC_IN));
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
                                    .Builder(EditLuckyWheelActivity.this)
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
                                            cardView.setBackgroundColor(cl);
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
                                    .Builder(EditLuckyWheelActivity.this)
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

                    // Thêm slice vào list slices
                    LuckyWheelSlice newSliceData = SliceToWheelItem.convertWheelItemToSlice(UUID.randomUUID().toString(), newSlice, currentWheelData.getId(), currentWheelSlices.size());
                    currentWheelSlices.add(newSliceData);

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
    public void setPresenter(EditLuckyWheelContract.Presenter presenter) {
        this.presenter = presenter;
    }
}

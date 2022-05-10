package uit.itszoo.izrandom.random_module.lucky_wheel.edit_lucky_wheel;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom.LuckyWheelCustomActivity;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class EditLuckyWheelActivity extends AppCompatActivity implements EditLuckyWheelContract.View {
    final int MAX_NUMBER_SLICE = 10;
    final int MIN_NUMBER_SLICE = 2;
    final int MIN_NUMBER_WHEEL = 1;
    final int DEFAULT_SELECTED_WHEEL_ITEM = 1;
    final int DEFAULT_SLICE_COLOR = -4955036;

    LuckyWheel luckyWheel;
    ArrayList<WheelItem> wheelItems = new ArrayList<>();
    ArrayList<WheelItem> originWheelItems = new ArrayList<>();

    EditLuckyWheelContract.Presenter presenter;

    CardView[] listCardView = new CardView[MAX_NUMBER_SLICE];
    TextView[] listTextInCard = new TextView[MAX_NUMBER_SLICE];
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

    int textSize = 1;
    int repeat = 1;
    int spinTime;
    String title = "";
    boolean fairMode = false;

    int changeColor;
    int defaultSliceColor = DEFAULT_SLICE_COLOR;

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


        createSliceCard();
    }

    void createSliceCard() {
        for (int i = 0; i < listCardView.length; i++) {
            listCardView[i].setVisibility(View.INVISIBLE);
            listTextInCard[i].setVisibility(View.INVISIBLE);
        }

        for (int index = 0; index < originWheelItems.size(); index++) {
            listCardView[index].setVisibility(View.VISIBLE);
            listTextInCard[index].setVisibility(View.VISIBLE);

            listCardView[index].setCardBackgroundColor(originWheelItems.get(index).color);
            listTextInCard[index].setText(originWheelItems.get(index).text);

            final int finalIndex = index;
            final WheelItem uiSlice = originWheelItems.get(finalIndex);
            listCardView[index].setOnClickListener(
                    view -> openEditSliceDialog(currentWheelSlices.get(finalIndex).getId(), uiSlice)
            );
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
                    if (wheelItems.size() < MAX_NUMBER_SLICE) {
                        openAddSliceDialog();
                    } else {
                        Toast.makeText(EditLuckyWheelActivity.this, "Số lượng Slice đã lớn nhất", Toast.LENGTH_LONG).show();
                    }
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
        EditText content = editDialog.findViewById(R.id.slice_content);
        EditText color = editDialog.findViewById(R.id.slice_color);
        Drawable mDrawable = getResources().getDrawable(R.drawable.ic_circle_color);

        mDrawable.setColorFilter(new PorterDuffColorFilter(uiSlice.color, PorterDuff.Mode.SRC_IN));

        changeColor = uiSlice.color;

        color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
        textInCard.setText(uiSlice.text);
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
                                    .okTitle("Choose")
                                    .cancelTitle("Cancel")
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
        EditText content = addDialog.findViewById(R.id.slice_content);
        EditText color = addDialog.findViewById(R.id.slice_color);
        Drawable mDrawable = getResources().getDrawable(R.drawable.ic_circle_color);
        mDrawable.setColorFilter(new PorterDuffColorFilter(defaultSliceColor, PorterDuff.Mode.SRC_IN));
        color.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
        cardView.setCardBackgroundColor(defaultSliceColor);

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

        acceptButton.setOnClickListener(
                view -> {
                    WheelItem newSlice = new WheelItem(defaultSliceColor, BitmapFactory.decodeResource(getResources(),
                            R.drawable.small_nails_icons), content.getText().toString());
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
                    defaultSliceColor = -4955036;
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

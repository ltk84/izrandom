package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;

public class LuckWheelPresenter implements LuckyWheelContract.Presenter {
    private final LuckyWheelContract.View view;
    private List<WheelItem> currentListWheel = new ArrayList<>();
    private int indexOfWheelInList = 0;
    private int repeat = 1;
    private int textSize = 16;
    private int spinTime = 5;
    private boolean fairMode =  false;
    public LuckWheelPresenter(Context context, LuckyWheelContract.View view) {
        this.view = view;
    }

    public void changeWheelItems(List<WheelItem> newWheel)
    {
        currentListWheel = newWheel;
    }

    public List<WheelItem>   getWheelItems()
    {
        return currentListWheel;
    }
    public void initListWheelItems( List<WheelItem> listItems)
    {
        currentListWheel = listItems;
    }

    public void setFairMode(boolean fairMode) {
        this.fairMode = fairMode;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public void setSpinTime(int spinTime) {
        this.spinTime = spinTime;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setIndexOfWheelInList(int indexOfWheelInList) {
        this.indexOfWheelInList = indexOfWheelInList;
    }

    public int getRepeat() {
        return repeat;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getSpinTime() {
        return spinTime;
    }

    public boolean getFairMode(){
        return fairMode;
    }

    public int getIndexOfWheelInList() {
        return indexOfWheelInList;
    }

    @Override
    public void start() {

    }
}
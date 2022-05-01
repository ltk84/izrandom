package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.content.Context;

import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;

public class LuckyWheelPresenter implements LuckyWheelContract.Presenter {
    private final LuckyWheelContract.View view;
    private LuckyWheelData currentWheelData;

//    private List<WheelItem> currentListWheel = new ArrayList<>();
//    private int indexOfWheelInList = 0;
//    private int repeat = 1;
//    private int textSize = 16;
//    private int spinTime = 5;
//    private boolean fairMode = false;

    public LuckyWheelPresenter(Context context, LuckyWheelContract.View view) {
        this.view = view;
    }

    @Override
    public LuckyWheelData getWheelData() {
        return currentWheelData;
    }

    @Override
    public void setWheelData(LuckyWheelData lkWheelData) {
        this.currentWheelData = lkWheelData;
    }

//    @Override
//    public void changeWheelItems(List<WheelItem> newWheel) {
//        currentListWheel = newWheel;
//    }
//
//    @Override
//    public List<WheelItem> getWheelItems() {
//        return currentListWheel;
//    }
//
//    @Override
//    public void initListWheelItems(List<WheelItem> listItems) {
//        currentListWheel = listItems;
//    }
//
//    @Override
//    public void setFairMode(boolean fairMode) {
//        this.fairMode = fairMode;
//    }
//
//    @Override
//    public void setRepeat(int repeat) {
//        this.repeat = repeat;
//    }
//
//    @Override
//    public void setSpinTime(int spinTime) {
//        this.spinTime = spinTime;
//    }
//
//    @Override
//    public void setTextSize(int textSize) {
//        this.textSize = textSize;
//    }
//
//    @Override
//    public void setIndexOfWheelInList(int indexOfWheelInList) {
//        this.indexOfWheelInList = indexOfWheelInList;
//    }
//
//    @Override
//    public int getRepeat() {
//        return repeat;
//    }
//
//    @Override
//    public int getTextSize() {
//        return textSize;
//    }
//
//    @Override
//    public int getSpinTime() {
//        return spinTime;
//    }
//
//    @Override
//    public boolean getFairMode() {
//        return fairMode;
//    }
//
//    @Override
//    public int getIndexOfWheelInList() {
//        return indexOfWheelInList;
//    }

    @Override
    public void start() {

    }
}

package uit.itszoo.izrandom.random_module.lucky_wheel;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.model.LuckyWheelData;

public interface LuckyWheelContract {
    interface Presenter extends BasePresenter {
//        void initListWheelItems(List<WheelItem> listItems);

//        void changeWheelItems(List<WheelItem> newWheel);

//        List<WheelItem> getWheelItems();

//        void setFairMode(boolean fairMode);
//
//        void setRepeat(int repeat);
//
//        void setSpinTime(int spinTime);
//
//        void setTextSize(int textSize);
//
//        void setIndexOfWheelInList(int indexOfWheelInList);
//
//        int getRepeat();
//
//        int getTextSize();
//
//        int getSpinTime();
//
//        int getIndexOfWheelInList();
//
//        boolean getFairMode();

        void setWheelData(LuckyWheelData lkWheelData);

        LuckyWheelData getWheelData();
    }

    interface View extends BaseView<Presenter> {

    }
}

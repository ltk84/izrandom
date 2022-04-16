package uit.itszoo.izrandom.random_module.lucky_wheel;

import com.bluehomestudio.luckywheel.WheelItem;

import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;

public interface LuckyWheelContract {
    interface Presenter extends BasePresenter {
        void initListWheelItems (List<WheelItem> listItems);
        void changeWheelItems(List<WheelItem> newWheel);
        List<WheelItem>   getWheelItems();
        void setFairMode(boolean fairMode);
        void setRepeat(int repeat);
        void setSpinTime(int spinTime);
        void setTextSize(int textSize);
        int getRepeat();
        int getTextSize();
        int getSpinTime();
        boolean getFairMode();
    }

    interface View extends BaseView<Presenter> {

    }
}

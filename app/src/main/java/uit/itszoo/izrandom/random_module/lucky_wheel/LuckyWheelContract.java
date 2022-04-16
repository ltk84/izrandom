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
    }

    interface View extends BaseView<Presenter> {

    }
}

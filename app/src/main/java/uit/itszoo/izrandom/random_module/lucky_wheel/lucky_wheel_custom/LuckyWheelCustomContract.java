package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import androidx.lifecycle.LiveData;

import java.util.List;

import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public interface LuckyWheelCustomContract {
    interface Presenter {
        LiveData<List<LuckyWheelData>> getAllWheelList();

        List<LuckyWheelSlice> getSlicesByWheelID(String wheelID);
    }

    interface View extends BaseView<Presenter> {

    }
}

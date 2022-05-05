package uit.itszoo.izrandom.random_module.lucky_wheel;

import androidx.lifecycle.LiveData;

import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public interface LuckyWheelContract {
    interface Presenter extends BasePresenter {

        void setWheelData(String wheelID);

        LuckyWheelData getWheelData();

        List<LuckyWheelSlice> getSliceByWheelID(String wheelID);

        LiveData<UserConfiguration> getUserConfig();
    }

    interface View extends BaseView<Presenter> {

    }
}

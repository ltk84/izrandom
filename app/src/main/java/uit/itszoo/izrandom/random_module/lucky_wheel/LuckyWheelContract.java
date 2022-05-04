package uit.itszoo.izrandom.random_module.lucky_wheel;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;

public interface LuckyWheelContract {
    interface Presenter extends BasePresenter {

        void setWheelData(String wheelID);

        LuckyWheelData getWheelData();

        LiveData<UserConfiguration> getUserConfig();
    }

    interface View extends BaseView<Presenter> {

    }
}

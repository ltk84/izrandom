package uit.itszoo.izrandom.random_module.lucky_wheel.add_lucky_wheel;

import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public interface AddLuckyWheelContract {
    interface Present {
        void insertWheel(LuckyWheelData wheelData);

        void insertSlice(LuckyWheelSlice slice);
    }

    interface View extends BaseView<Present> {
    }
}

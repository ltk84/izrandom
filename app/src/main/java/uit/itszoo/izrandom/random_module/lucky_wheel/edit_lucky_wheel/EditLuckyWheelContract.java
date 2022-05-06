package uit.itszoo.izrandom.random_module.lucky_wheel.edit_lucky_wheel;

import java.util.List;

import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public interface EditLuckyWheelContract {
    interface Presenter {
        List<LuckyWheelSlice> getSlicesByWheelID(String wheelID);

        void deleteSliceByIDs(List<String> ids);

        void updateSlice(LuckyWheelSlice slice);

        boolean checkIfSliceExist(String id);

        void addSlice(LuckyWheelSlice slice);

        void updateWheel(LuckyWheelData wheelData);

        int getNumberOfWheel();

        void deleteWheel(LuckyWheelData wheelData);
    }

    interface View extends BaseView<Presenter> {

    }
}

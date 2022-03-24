package uit.itszoo.izrandom.random_module.random_direction;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.database.UserConfiguration;

public interface RandomDirectionContract {
    interface Presenter extends BasePresenter {

        void changeArrow(int selectedArrow);

        LiveData<UserConfiguration> getUserConfig();
    }

    interface View extends BaseView<Presenter> {
        void applyChangeArrow(int arrow);
    }
}

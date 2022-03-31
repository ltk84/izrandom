package uit.itszoo.izrandom.random_module.random_direction;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.random_direction.model.Arrow;

public interface RandomDirectionContract {
    interface Presenter extends BasePresenter {

        void initArrow(Arrow arrow);

        Arrow getCurrentArrow();

        void changeArrow(Arrow arrow);

        LiveData<UserConfiguration> getUserConfig();

        void executeAnimation(boolean isHold);
    }

    interface View extends BaseView<Presenter> {
        void applyChangeArrow(int arrow);

        void executeSpin();

        void executeSpinForever();
    }
}

package uit.itszoo.izrandom.random_module.chooser;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.chooser.model.ChooserTheme;

public interface ChooserContract {

    interface Presenter extends BasePresenter {
        void initTheme(ChooserTheme theme);

        void changeTheme(ChooserTheme theme);

        ChooserTheme getCurrentTheme();

        LiveData<UserConfiguration> getUserConfig();
    }

    interface View extends BaseView<Presenter> {
        void applyChangeTheme(int themeValue);
    }

}

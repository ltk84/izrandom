package uit.itszoo.izrandom.random_module.chooser;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.chooser.model.ChooserTheme;

public interface ChooserContract {

    interface Presenter extends BasePresenter {
        void changeTheme(ChooserTheme theme);
    }

    interface View extends BaseView<Presenter> {
        void applyChangeTheme(int themeValue);
    }

}

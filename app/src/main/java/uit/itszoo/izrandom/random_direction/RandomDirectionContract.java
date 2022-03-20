package uit.itszoo.izrandom.random_direction;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;

public interface RandomDirectionContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {
        void executeSpin();
    }
}

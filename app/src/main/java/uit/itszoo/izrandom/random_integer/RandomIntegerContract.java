package uit.itszoo.izrandom.random_integer;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;

public interface RandomIntegerContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<RandomIntegerContract.Presenter> {
    }
}

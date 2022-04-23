package uit.itszoo.izrandom.random_module.flip_card;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.random_integer.RandomIntegerContract;

public interface FlipCardContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<FlipCardContract.Presenter> {

    }
}

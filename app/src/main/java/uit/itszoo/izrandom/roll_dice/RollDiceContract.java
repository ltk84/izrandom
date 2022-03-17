package uit.itszoo.izrandom.roll_dice;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;

public interface RollDiceContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<RollDiceContract.Presenter> {
    }
}

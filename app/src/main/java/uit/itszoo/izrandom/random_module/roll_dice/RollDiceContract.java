package uit.itszoo.izrandom.random_module.roll_dice;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.roll_dice.model.DiceLayout;

public interface RollDiceContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<RollDiceContract.Presenter> {
        void executeRollInOne();

        void executeMoveUpAndRoll();

        void executeMoveDown();

        void executeOnlyRoll();

        void applyTheme(DiceLayout layout);
    }
}

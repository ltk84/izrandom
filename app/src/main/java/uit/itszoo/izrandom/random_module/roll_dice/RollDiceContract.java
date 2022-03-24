package uit.itszoo.izrandom.random_module.roll_dice;

import com.iigo.library.DiceLoadingView;

import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;

public interface RollDiceContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<RollDiceContract.Presenter> {
        void executeRollInOne(List<DiceLoadingView> diceViewList);

        void executeMoveUpAndRoll(List<DiceLoadingView> diceViewList);

        void executeMoveDown(List<DiceLoadingView> diceViewList);

        void executeOnlyRoll(List<DiceLoadingView> diceViewList);
    }
}

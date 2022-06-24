package uit.itszoo.izrandom.random_module.roll_dice;

import androidx.lifecycle.LiveData;

import com.iigo.library.DiceLoadingView;

import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.roll_dice.model.Dice;

public interface RollDiceContract {
    interface Presenter extends BasePresenter {
        void initDice(Dice dice);

        void changeDice(List<DiceLoadingView> viewList, Dice dice);

        Dice getDiceApp();

        LiveData<UserConfiguration> getUserConfig();
    }

    interface View extends BaseView<RollDiceContract.Presenter> {
        void executeRollInOne(List<DiceLoadingView> diceViewList);

        void executeMoveUpAndRoll(List<DiceLoadingView> diceViewList);

        void executeMoveDown(List<DiceLoadingView> diceViewList);

        void executeOnlyRoll(List<DiceLoadingView> diceViewList);

        void applyTheme(DiceLoadingView view, Dice layout);
    }
}

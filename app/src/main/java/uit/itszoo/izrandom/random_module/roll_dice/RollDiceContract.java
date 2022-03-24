package uit.itszoo.izrandom.random_module.roll_dice;

import com.iigo.library.DiceLoadingView;

import java.util.List;
import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.roll_dice.model.Dice;

public interface RollDiceContract {
    interface Presenter extends BasePresenter {
        void initDice(Dice dice);

        void changeDice(Dice dice);

        Dice getCurrentDice();

        LiveData<UserConfiguration> getUserConfig();
    }

    interface View extends BaseView<RollDiceContract.Presenter> {
        void executeRollInOne(List<DiceLoadingView> diceViewList);

        void executeMoveUpAndRoll(List<DiceLoadingView> diceViewList);

        void executeMoveDown(List<DiceLoadingView> diceViewList);

        void executeOnlyRoll(List<DiceLoadingView> diceViewList);
        
        void applyTheme(Dice layout);
    }
}

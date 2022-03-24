package uit.itszoo.izrandom.random_module.roll_dice;

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
        void executeRollInOne();

        void executeMoveUpAndRoll();

        void executeMoveDown();

        void executeOnlyRoll();

        void applyTheme(Dice layout);
    }
}

package uit.itszoo.izrandom.random_module.roll_dice;

import android.content.Context;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.roll_dice.model.Dice;

public class RollDicePresenter implements RollDiceContract.Presenter {
    private final RollDiceContract.View view;

    private Repository repository;
    private LiveData<UserConfiguration> userConfig;
    private Dice currentDice;

    public RollDicePresenter(Context context, RollDiceContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }

    @Override
    public void initDice(Dice dice) {
        currentDice = dice;
    }

    @Override
    public void changeDice(Dice dice) {
        view.applyTheme(dice);
        repository.changeDice(dice.getId());
    }

    @Override
    public Dice getCurrentDice() {
        return currentDice;
    }

    @Override
    public LiveData<UserConfiguration> getUserConfig() {
        return userConfig;
    }

    @Override
    public void start() {

    }
}

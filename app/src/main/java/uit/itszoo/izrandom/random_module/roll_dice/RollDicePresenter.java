package uit.itszoo.izrandom.random_module.roll_dice;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.iigo.library.DiceLoadingView;

import java.util.List;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.roll_dice.model.Dice;

public class RollDicePresenter implements RollDiceContract.Presenter {
    private final RollDiceContract.View view;

    private Repository repository;
    private LiveData<UserConfiguration> userConfig;
    private Dice diceApp;

    public RollDicePresenter(Context context, RollDiceContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }

    @Override
    public void initDice(Dice dice) {
        diceApp = dice;
    }

    @Override
    public void changeDice(List<DiceLoadingView> diceViewList, Dice dice) {
        
        repository.changeDice(dice.getId());
        diceApp = dice;

        for (DiceLoadingView dView :
                diceViewList) {
            view.applyTheme(dView, dice);
        }
    }

    @Override
    public Dice getDiceApp() {
        return diceApp;
    }

    @Override
    public LiveData<UserConfiguration> getUserConfig() {
        return userConfig;
    }

    @Override
    public void start() {

    }
}

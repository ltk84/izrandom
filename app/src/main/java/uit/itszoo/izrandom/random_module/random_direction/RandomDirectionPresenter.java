package uit.itszoo.izrandom.random_module.random_direction;

import android.content.Context;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;

public class RandomDirectionPresenter implements RandomDirectionContract.Presenter {
    private final RandomDirectionContract.View view;

    private Repository repository;
    private int currentArrow;
    private LiveData<UserConfiguration> userConfig;

    public RandomDirectionPresenter(Context context, RandomDirectionContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }

    @Override
    public LiveData<UserConfiguration> getUserConfig() {
        return userConfig;
    }

    @Override
    public void changeArrow(int arrow) {
        repository.changeArrow(arrow);
        view.applyChangeArrow(arrow);
    }

    @Override
    public void start() {

    }
}

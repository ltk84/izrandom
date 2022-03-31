package uit.itszoo.izrandom.random_module.random_direction;

import android.content.Context;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.random_direction.model.Arrow;

public class RandomDirectionPresenter implements RandomDirectionContract.Presenter {
    private final RandomDirectionContract.View view;

    private Arrow arrowApp;
    private Repository repository;
    private LiveData<UserConfiguration> userConfig;

    public RandomDirectionPresenter(Context context, RandomDirectionContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }

    @Override
    public void initArrow(Arrow arrow) {
        arrowApp = arrow;
    }

    @Override
    public Arrow getArrowApp() {
        return arrowApp;
    }

    @Override
    public LiveData<UserConfiguration> getUserConfig() {
        return userConfig;
    }

    @Override
    public void changeArrow(Arrow arrow) {
        view.applyChangeArrow(arrow.getLayout());
        repository.changeArrow(arrow.getId());
    }

    @Override
    public void start() {

    }
}

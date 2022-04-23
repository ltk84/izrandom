package uit.itszoo.izrandom.random_module.flip_card;

import android.content.Context;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;

public class FlipCardPresenter implements FlipCardContract.Presenter {
    private final FlipCardContract.View view;

    private Repository repository;
    private LiveData<UserConfiguration> userConfig;

    public FlipCardPresenter(Context context, FlipCardContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }

    @Override
    public void start() {

    }

}

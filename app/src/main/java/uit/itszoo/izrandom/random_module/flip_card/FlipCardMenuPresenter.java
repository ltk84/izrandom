package uit.itszoo.izrandom.random_module.flip_card;

import android.content.Context;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;

public class FlipCardMenuPresenter implements FlipCardMenuContract.Presenter {
    private final FlipCardMenuContract.View view;

    private Repository repository;
    private LiveData<UserConfiguration> userConfig;

    public FlipCardMenuPresenter(Context context, FlipCardMenuContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }

    @Override
    public void start() {

    }

}

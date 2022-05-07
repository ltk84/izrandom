package uit.itszoo.izrandom.random_module.flip_card.flip_card_add;

import android.content.Context;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuContract;

public class FlipCardAddPresenter implements FlipCardAddContract.Presenter {
    private final FlipCardAddContract.View view;

//    private Repository repository;
//    private LiveData<UserConfiguration> userConfig;

    public FlipCardAddPresenter(Context context, FlipCardAddContract.View view) {
        this.view = view;
//        repository = Repository.getInstance(context);
//        userConfig = repository.getUserConfiguration();
    }

    @Override
    public void start() {

    }

}

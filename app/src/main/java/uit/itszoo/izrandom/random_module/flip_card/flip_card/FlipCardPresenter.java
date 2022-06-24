package uit.itszoo.izrandom.random_module.flip_card.flip_card;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

public class FlipCardPresenter implements FlipCardContract.Presenter {
    private final FlipCardContract.View view;

    private Repository repository;

    public FlipCardPresenter(Context context, FlipCardContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
    }

    @Override
    public List<CardModel> getListCardModelByCollectionId(String collectionId) {
        return repository.getCardModelsByCollectionId(collectionId);
    }

    @Override
    public void start() {

    }
}

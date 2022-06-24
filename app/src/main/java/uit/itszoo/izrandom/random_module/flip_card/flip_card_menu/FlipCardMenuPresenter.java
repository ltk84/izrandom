package uit.itszoo.izrandom.random_module.flip_card.flip_card_menu;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;

public class FlipCardMenuPresenter implements FlipCardMenuContract.Presenter {
    private final FlipCardMenuContract.View view;

    private Repository repository;

    private List<CardCollectionModel> listCardCollections;

    public FlipCardMenuPresenter(Context context, FlipCardMenuContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        listCardCollections = repository.getAllCardCollections();
    }

    @Override
    public List<CardCollectionModel> getListCardCollections() {
        return this.listCardCollections;
    }

    @Override
    public void deleteCardCollection(CardCollectionModel cardCollectionModel) {
        repository.deleteCardCollection(cardCollectionModel);
    }

    @Override
    public void deleteAllCardsInCollection(String collectionId) {
        repository.deleteAllCardsInCollection(collectionId);
    }

    @Override
    public void start() {

    }

}

package uit.itszoo.izrandom.random_module.flip_card.flip_card_add;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import uit.itszoo.izrandom.database.CardCollectionDAO;
import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.flip_card.flip_card_menu.FlipCardMenuContract;
import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

public class FlipCardAddPresenter implements FlipCardAddContract.Presenter {
    private final FlipCardAddContract.View view;

    private Repository repository;

    public FlipCardAddPresenter(Context context, FlipCardAddContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
    }

    @Override
    public List<CardModel> getListCardModelByCollectionId(String collectionId) {
        return repository.getCardModelsByCollectionId(collectionId);
    }

    @Override
    public void insertCardCollection(CardCollectionModel cardCollectionModel) {
        repository.insertCardCollection(cardCollectionModel);
    }

    @Override
    public void updateCardCollection(CardCollectionModel cardCollectionModel) {
        repository.updateCardCollection(cardCollectionModel);
    }

    @Override
    public void insertCard(CardModel cardModel) {
        repository.insertCard(cardModel);
    }

    @Override
    public void updateCard(CardModel cardModel) {
        repository.updateCard(cardModel);
    }

    @Override
    public void deleteCard(CardModel cardModel) {
        repository.deleteCard(cardModel);
    }

    @Override
    public void deleteAllCardsInCollection(String collectionId) {
        repository.deleteAllCardsInCollection(collectionId);
    }


    @Override
    public void start() {

    }

}

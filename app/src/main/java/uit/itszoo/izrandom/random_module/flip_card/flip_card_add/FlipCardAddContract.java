package uit.itszoo.izrandom.random_module.flip_card.flip_card_add;

import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

public interface FlipCardAddContract {
    interface Presenter extends BasePresenter {

        List<CardModel> getListCardModelByCollectionId(String collectionId);
        void insertCardCollection(CardCollectionModel cardCollectionModel);
        void updateCardCollection(CardCollectionModel cardCollectionModel);
        void deleteAllCardsInCollection(String collectionId);
        void insertCard(CardModel cardModel);

    }

    interface View extends BaseView<FlipCardAddContract.Presenter> {

    }
}

package uit.itszoo.izrandom.random_module.flip_card.flip_card;

import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.flip_card.model.CardModel;

public interface FlipCardContract {
    interface Presenter extends BasePresenter {

        List<CardModel> getListCardModelByCollectionId(String collectionId);

    }

    interface View extends BaseView<FlipCardContract.Presenter> {

    }
}

package uit.itszoo.izrandom.random_module.flip_card.flip_card_menu;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.flip_card.model.CardCollectionModel;

public interface FlipCardMenuContract {
    interface Presenter extends BasePresenter {

        List<CardCollectionModel> getListCardCollections();

    }

    interface View extends BaseView<FlipCardMenuContract.Presenter> {

    }
}

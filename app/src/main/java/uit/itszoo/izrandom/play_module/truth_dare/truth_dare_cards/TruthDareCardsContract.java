package uit.itszoo.izrandom.play_module.truth_dare.truth_dare_cards;

import androidx.lifecycle.LiveData;

import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.play_module.truth_dare.models.TruthDareCard;

public interface TruthDareCardsContract {
    interface Presenter extends BasePresenter {
        LiveData<List<TruthDareCard>> getCards();
    }

    interface View extends BaseView<TruthDareCardsContract.Presenter> {

    }
}

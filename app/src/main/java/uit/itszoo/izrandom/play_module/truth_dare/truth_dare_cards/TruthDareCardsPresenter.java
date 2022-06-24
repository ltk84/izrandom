package uit.itszoo.izrandom.play_module.truth_dare.truth_dare_cards;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.UUID;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.play_module.truth_dare.models.TruthDareCard;

public class TruthDareCardsPresenter implements TruthDareCardsContract.Presenter {
    private final TruthDareCardsContract.View view;
    private Repository repository;
    private LiveData<List<TruthDareCard>> cards;

    public TruthDareCardsPresenter(Context context, TruthDareCardsContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        cards = repository.getTdCardList();
    }

    @Override
    public LiveData<List<TruthDareCard>> getCards() {
        return cards;
    }

    @Override
    public void addCard() {
        TruthDareCard newCard = new TruthDareCard(UUID.randomUUID().toString(), "Tên thẻ bài", 1);
        repository.addTDCard(newCard);
    }

    @Override
    public void deleteCard(String id) {
        repository.deleteTDCard(id);
    }

    @Override
    public void editCard(TruthDareCard card) {
        repository.updateTDCard(card);
    }

    @Override
    public void start() {

    }
}

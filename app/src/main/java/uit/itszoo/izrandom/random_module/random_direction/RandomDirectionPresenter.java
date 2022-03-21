package uit.itszoo.izrandom.random_module.random_direction;

import android.content.Context;

import uit.itszoo.izrandom.database.Repository;

public class RandomDirectionPresenter implements RandomDirectionContract.Presenter {
    private final RandomDirectionContract.View view;

    private Repository repository;
    private int currentArrow;

    public RandomDirectionPresenter(Context context, RandomDirectionContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        currentArrow = repository.getUserConfiguration().arrow;
    }

    @Override
    public void changeArrowAppearance(int arrow) {
        this.currentArrow = arrow;
        repository.changeArrow(arrow);
    }

    @Override
    public int getCurrentArrow() {
        return currentArrow;
    }

    @Override
    public void start() {

    }


}

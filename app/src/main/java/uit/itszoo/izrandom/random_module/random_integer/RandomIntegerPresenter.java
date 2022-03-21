package uit.itszoo.izrandom.random_module.random_integer;

import uit.itszoo.izrandom.random_module.random_direction.RandomDirectionContract;

public class RandomIntegerPresenter implements RandomIntegerContract.Presenter {
    private final RandomDirectionContract.View view;

    public RandomIntegerPresenter(RandomDirectionContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }
}

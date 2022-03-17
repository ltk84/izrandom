package uit.itszoo.izrandom.random_direction;

public class RandomDirectionPresenter implements RandomDirectionContract.Presenter {
    private final RandomDirectionContract.View view;

    public RandomDirectionPresenter(RandomDirectionContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }
}

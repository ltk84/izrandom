package uit.itszoo.izrandom.random_module.random_integer;


public class RandomIntegerPresenter implements RandomIntegerContract.Presenter {
    private final RandomIntegerContract.View view;

    public RandomIntegerPresenter(RandomIntegerContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }
}

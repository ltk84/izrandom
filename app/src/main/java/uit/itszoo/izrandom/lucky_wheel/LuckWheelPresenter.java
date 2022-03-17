package uit.itszoo.izrandom.lucky_wheel;

public class LuckWheelPresenter implements LuckyWheelContract.Presenter {
    private final LuckyWheelContract.View view;

    public LuckWheelPresenter(LuckyWheelContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }
}

package uit.itszoo.izrandom.roll_dice;

public class RollDicePresenter implements RollDiceContract.Presenter {
    private final RollDiceContract.View view;

    public RollDicePresenter(RollDiceContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }
}

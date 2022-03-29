package uit.itszoo.izrandom.random_module.flip_coin;

import android.content.Context;

public class FlipCoinPresenter implements FlipCoinContract.Presenter{
    private final FlipCoinContract.View view;

    public FlipCoinPresenter(Context context, FlipCoinContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }
}

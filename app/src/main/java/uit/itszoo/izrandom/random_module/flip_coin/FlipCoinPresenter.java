package uit.itszoo.izrandom.random_module.flip_coin;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;

public class FlipCoinPresenter implements FlipCoinContract.Presenter {
    private final FlipCoinContract.View view;

    // Objects
    List<Coin> coinList;

    public FlipCoinPresenter(Context context, FlipCoinContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void initCoinList(List<ImageView> coinViewList) {
        coinList = new ArrayList<>();
        // mock lay data tu db
        String coinID = "1";
        for (int i = 0; i < coinViewList.size(); i++) {
            ImageView coinView = coinViewList.get(i);
            coinList.add(new Coin(coinID, coinView.getId(), R.drawable.ic_coin_1_head, R.drawable.ic_coin_1_tail, true));
        }
    }

    @Override
    public void addCoin(ImageView coinView) {
        // mock lay data tu db
        String coinID = "1";
        coinList.add(new Coin(coinID, coinView.getId(), R.drawable.ic_coin_1_head, R.drawable.ic_coin_1_tail, true));
    }

    @Override
    public void removeCoin() {
        coinList.remove(coinList.size() - 1);
    }

    @Override
    public Coin getCoinFromViewId(int viewId) {
        return coinList.stream().filter(coin -> coin.getViewID() == viewId).collect(Collectors.toList()).get(0);
    }


    @Override
    public void changeCoin(Coin coin) {
        view.applyChangeCoin(coin);
        // update du lieu trong db

        // update du lieu presenter
        for (int i = 0; i < coinList.size(); i++) {
            coinList.get(i).setId(coin.getId());
            coinList.get(i).setAppearance(coin.getDrawableHead(), coin.getDrawableTail());
        }
    }
}

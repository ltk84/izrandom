package uit.itszoo.izrandom.random_module.flip_coin;

import android.content.Context;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;

public class FlipCoinPresenter implements FlipCoinContract.Presenter{
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
        for (int i = 0; i < coinViewList.size(); i++) {
            ImageView coinView = coinViewList.get(i);
            coinList.add(new Coin(coinView.getId(), R.drawable.ic_coin_1_head, R.drawable.ic_coin_1_tail, true));
        }
    }

    @Override
    public void addCoin(ImageView coinView) {
        coinList.add(new Coin(coinView.getId(), R.drawable.ic_coin_1_head, R.drawable.ic_coin_1_tail, true));
    }

    @Override
    public void removeCoin() {
        coinList.remove(coinList.size()-1);
    }

    @Override
    public Coin getCoinFromViewId(int viewId) {
        return coinList.stream().filter(coin -> coin.getViewID() == viewId).collect(Collectors.toList()).get(0);
    }
}

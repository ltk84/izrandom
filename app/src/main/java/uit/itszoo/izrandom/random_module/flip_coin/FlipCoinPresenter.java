package uit.itszoo.izrandom.random_module.flip_coin;

import android.content.Context;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;

public class FlipCoinPresenter implements FlipCoinContract.Presenter {
    private final FlipCoinContract.View view;

    // Objects
    List<Coin> coinList;
    private Repository repository;
    private LiveData<UserConfiguration> userConfig;
    private Coin coinApp;


    public FlipCoinPresenter(Context context, FlipCoinContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }

    @Override
    public LiveData<UserConfiguration> getUserConfig() {
        return userConfig;
    }

    @Override
    public void start() {

    }

    @Override
    public void initCoinList(List<ImageView> coinViewList, Coin coin) {
        coinList = new ArrayList<>();
        for (int i = 0; i < coinViewList.size(); i++) {
            ImageView coinView = coinViewList.get(i);
            coinList.add(new Coin(coin.getId(), coinView.getId(), coin.getDrawableHead(), coin.getDrawableTail(), true));
        }
        coinApp = coin;
    }

    @Override
    public void addCoin(ImageView coinView) {
        coinList.add(new Coin(coinApp.getId(), coinView.getId(), coinApp.getDrawableHead(), coinApp.getDrawableTail(), true));
        view.applyChangeCoin(coinApp);
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
        repository.changeCoin(coin.getId());
        // update du lieu presenter
        for (int i = 0; i < coinList.size(); i++) {
            coinList.get(i).setId(coin.getId());
            coinList.get(i).setAppearance(coin.getDrawableHead(), coin.getDrawableTail());
        }
        coinApp = coin;
    }
}

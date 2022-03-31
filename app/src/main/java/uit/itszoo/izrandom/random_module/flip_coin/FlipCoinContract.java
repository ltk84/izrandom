package uit.itszoo.izrandom.random_module.flip_coin;

import android.widget.ImageView;

import androidx.lifecycle.LiveData;

import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;

public interface FlipCoinContract {

    interface Presenter extends BasePresenter {
        void initCoinList(List<ImageView> coinViewList, Coin coin);

        void addCoin(ImageView coinView);

        void removeCoin();

        Coin getCoinFromViewId(int viewId);

        void changeCoin(Coin coin);

        LiveData<UserConfiguration> getUserConfig();
    }

    interface View extends BaseView<FlipCoinContract.Presenter> {
        void applyChangeCoin(Coin coin);
    }
}

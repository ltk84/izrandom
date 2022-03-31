package uit.itszoo.izrandom.random_module.flip_coin;

import android.widget.ImageView;

import java.util.List;

import uit.itszoo.izrandom.BasePresenter;
import uit.itszoo.izrandom.BaseView;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;

public interface FlipCoinContract {

    interface Presenter extends BasePresenter {
        void initCoinList(List<ImageView> coinViewList);
        void addCoin(ImageView coinView);
        void removeCoin();
        Coin getCoinFromViewId(int viewId);
    }

    interface View extends BaseView<FlipCoinContract.Presenter> {
    }
}

package uit.itszoo.izrandom.random_module.flip_coin.source;

import java.util.ArrayList;
import java.util.Arrays;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;

public class CoinSource {

    public static ArrayList<Coin> coins = new ArrayList<Coin>(Arrays.asList(new Coin("1", 1, R.drawable.ic_coin_1_head, R.drawable.ic_coin_1_tail, false),
            new Coin("2", 1, R.drawable.ic_coin_2_head, R.drawable.ic_coin_2_tail, false),
            new Coin("3", 1, R.drawable.ic_coin_3_head, R.drawable.ic_coin_3_tail, false),
            new Coin("4", 1, R.drawable.ic_coin_4_head, R.drawable.ic_coin_4_tail, false),
            new Coin("5", 1, R.drawable.ic_coin_5_head, R.drawable.ic_coin_5_tail, false)));

    public static Coin findCoin(String id) {
        return CoinSource.coins.stream().filter(coin -> coin.getId().compareTo(id) == 0).findFirst().get();
    }
}

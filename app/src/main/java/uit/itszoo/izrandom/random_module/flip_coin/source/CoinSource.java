package uit.itszoo.izrandom.random_module.flip_coin.source;

import java.util.ArrayList;
import java.util.Arrays;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.flip_coin.model.Coin;

public class CoinSource {
    public static ArrayList<Coin> coins = new ArrayList<Coin>(Arrays.asList(new Coin("1", 1, R.drawable.ic_coin_1_head, R.drawable.ic_coin_1_tail, false),
            new Coin("2", 1, R.drawable.ic_coin_2_head, R.drawable.ic_coin_2_tail, false)));
}

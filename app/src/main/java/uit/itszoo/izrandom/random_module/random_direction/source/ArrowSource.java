package uit.itszoo.izrandom.random_module.random_direction.source;

import java.util.ArrayList;
import java.util.Arrays;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.random_direction.model.Arrow;

public class ArrowSource {
    public static ArrayList<Arrow> arrows = new ArrayList<Arrow>(Arrays.asList(
            new Arrow("1", R.drawable.ic_random_direction),
            new Arrow("2", R.drawable.ic_random_direction_2),
            new Arrow("3", R.drawable.ic_random_direction_3),
            new Arrow("4", R.drawable.ic_random_direction_4),
            new Arrow("5", R.drawable.ic_random_direction_5)
    ));
}

package uit.itszoo.izrandom.random_module.chooser.source;

import java.util.ArrayList;
import java.util.Arrays;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.chooser.model.ChooserTheme;

public class ChooserSource {

    public static ArrayList<ChooserTheme> themes = new ArrayList<ChooserTheme>(Arrays.asList(
            new ChooserTheme("1", R.drawable.coal_chooser_background_selector),
            new ChooserTheme("2", R.drawable.orange_chooser_backgroud_selector),
            new ChooserTheme("3", R.drawable.purple_chooser_background_selector),
            new ChooserTheme("4", R.drawable.red_coal_chooser_background_selector)
    ));
}

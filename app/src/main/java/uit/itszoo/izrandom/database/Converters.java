package uit.itszoo.izrandom.database;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uit.itszoo.izrandom.random_module.roll_dice.model.DiceLayout;

public class Converters {
    @TypeConverter
    public static String diceLayoutToString(DiceLayout diceLayout) {
        List<String> list = new ArrayList<String>(Arrays.asList(String.valueOf(diceLayout.getBackgroundColor()), String.valueOf(diceLayout.getBorderColor()), String.valueOf(diceLayout.getPointColor())));
        return list.get(0) + "," + list.get(1) + "," + list.get(2);
    }

    @TypeConverter
    public static DiceLayout fromStringToDiceLayout(String str) {
//        return new DiceLayout(dice.get(0), dice.get(1), dice.get(2));

        String[] list = str.split(",");
        return new DiceLayout(Integer.valueOf(list[0]), Integer.valueOf(list[1]), Integer.valueOf(list[2]));
    }


}
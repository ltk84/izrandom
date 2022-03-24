package uit.itszoo.izrandom.database;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uit.itszoo.izrandom.random_module.roll_dice.model.DiceLayout;

public class Converters {
    @TypeConverter
    public static DiceLayout fromIntArrToDiceLayout(List<Integer> dice) {
        return new DiceLayout(dice.get(0), dice.get(1), dice.get(2));
    }

    @TypeConverter
    public static List<Integer> diceLayoutToIntArr(DiceLayout diceLayout) {
        List<Integer> dice = new ArrayList<>(Arrays.asList(diceLayout.getBackgroundColor(), diceLayout.getBorderColor(), diceLayout.getPointColor()));
        return diceLayout == null ? null : dice;
    }
}
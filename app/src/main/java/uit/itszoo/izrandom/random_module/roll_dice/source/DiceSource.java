package uit.itszoo.izrandom.random_module.roll_dice.source;

import java.util.ArrayList;
import java.util.Arrays;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.roll_dice.model.Dice;

public class DiceSource {
    public static ArrayList<Dice> dices = new ArrayList<>(Arrays.asList(
            new Dice("1", R.color.regularColorDiceBackground, R.color.regularColorDiceBorder, R.color.regularColorDicePoint),
            new Dice("2", R.color.draculaColorDiceBackground, R.color.draculaColorDiceBorder, R.color.draculaColorDicePoint),
            new Dice("3", R.color.poisonColorDiceBackground, R.color.poisonColorDiceBorder, R.color.poisonColorDicePoint),
            new Dice("4", R.color.chingChongColorDiceBackground, R.color.chingChongColorDiceBorder, R.color.chingChongColorDicePoint),
            new Dice("5", R.color.softColorDiceBackground, R.color.softColorDiceBorder, R.color.softColorDicePoint),
            new Dice("6", R.color.lavenderColorDiceBackground, R.color.lavenderColorDiceBorder, R.color.lavenderColorDicePoint)
    ));
}

package uit.itszoo.izrandom.random_module.roll_dice.model;

import java.io.Serializable;

public class DiceLayout implements Serializable {
    private int backgroundColor;
    private int borderColor;
    private int pointColor;

    public DiceLayout(int backgroundColor, int borderColor, int pointColor) {
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.pointColor = pointColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getPointColor() {
        return pointColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }
}

package uit.itszoo.izrandom.random_module.roll_dice.model;

import java.io.Serializable;
import java.util.Objects;

public class Dice implements Serializable {
    private String id;
    private int backgroundColor;
    private int borderColor;
    private int pointColor;

    public Dice(String id, int backgroundColor, int borderColor, int pointColor) {
        this.id = id;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.pointColor = pointColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dice dice = (Dice) o;
        return backgroundColor == dice.backgroundColor &&
                borderColor == dice.borderColor &&
                pointColor == dice.pointColor &&
                Objects.equals(id, dice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, backgroundColor, borderColor, pointColor);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

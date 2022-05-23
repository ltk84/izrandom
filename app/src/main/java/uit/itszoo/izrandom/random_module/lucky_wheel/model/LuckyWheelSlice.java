package uit.itszoo.izrandom.random_module.lucky_wheel.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "luckyWheelSlice")
public class LuckyWheelSlice implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String color;
    private String textColor;
    private int icon;
    private String wheelID;
    private int numberOrder;

    @Ignore
    public LuckyWheelSlice(String id, String name, String color, int icon) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.icon = icon;
        wheelID = "";
        numberOrder = -1;
    }

    public LuckyWheelSlice(String id, String name, String color, String textColor, int icon, String wheelID, int numberOrder) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.wheelID = wheelID;
        this.numberOrder = numberOrder;
        this.textColor = textColor;
    }

    @NonNull
    @NotNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new LuckyWheelSlice(id, name, color, textColor, icon, wheelID, numberOrder);
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWheelID() {
        return wheelID;
    }

    public void setWheelID(String wheelID) {
        this.wheelID = wheelID;
    }

    public int getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(int numberOrder) {
        this.numberOrder = numberOrder;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    //    public boolean isBelongToWheel() {
//        return !getWheelID().equals("");
//    }

//    public boolean hasNumberOrder() {
//        return getNumberOrder() >= 0;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LuckyWheelSlice slice = (LuckyWheelSlice) o;
        return icon == slice.icon &&
                id.equals(slice.id) &&
                Objects.equals(name, slice.name) &&
                Objects.equals(color, slice.color) &&
                Objects.equals(wheelID, slice.wheelID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, color, icon, wheelID);
    }
}

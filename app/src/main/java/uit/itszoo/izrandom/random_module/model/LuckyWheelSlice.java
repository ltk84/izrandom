package uit.itszoo.izrandom.random_module.model;

import java.io.Serializable;

public class LuckyWheelSlice implements Serializable {
    private String id;
    private String name;
    private String color;
    private int icon;

    public LuckyWheelSlice(String id, String name, String color, int icon) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.icon = icon;
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
}

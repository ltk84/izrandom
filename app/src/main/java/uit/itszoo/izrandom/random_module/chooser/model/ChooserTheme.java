package uit.itszoo.izrandom.random_module.chooser.model;

import java.io.Serializable;

public class ChooserTheme implements Serializable {
    private String id;
    private int themeValue;

    public ChooserTheme(String id, int themeValue) {
        this.id = id;
        this.themeValue = themeValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getThemeValue() {
        return themeValue;
    }

    public void setThemeValue(int themeValue) {
        this.themeValue = themeValue;
    }
}

package uit.itszoo.izrandom.random_module.chooser.model;

import java.io.Serializable;
import java.util.Objects;

public class ChooserTheme implements Serializable {
    private String id;
    private int themeValue;

    public ChooserTheme(String id, int themeValue) {
        this.id = id;
        this.themeValue = themeValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChooserTheme theme = (ChooserTheme) o;
        return themeValue == theme.themeValue &&
                Objects.equals(id, theme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, themeValue);
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

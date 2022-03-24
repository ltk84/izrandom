package uit.itszoo.izrandom.random_module.random_direction.model;

import java.io.Serializable;
import java.util.Objects;

public class Arrow implements Serializable {
    private String id;
    private int layout;

    public Arrow(String id, int layout) {
        this.id = id;
        this.layout = layout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Arrow arrow = (Arrow) o;
        return layout == arrow.layout &&
                Objects.equals(id, arrow.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, layout);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }
}


package uit.itszoo.izrandom.random_module.random_direction.model;

import java.io.Serializable;

public class Arrow implements Serializable {
    private String id;
    private int layout;

    public Arrow(String id, int layout) {
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


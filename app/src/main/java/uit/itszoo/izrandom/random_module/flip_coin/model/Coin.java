package uit.itszoo.izrandom.random_module.flip_coin.model;

import java.io.Serializable;

public class Coin implements Serializable {
    private String id;
    private int viewID;
    private int drawableHead;
    private int drawableTail;
    public boolean isHead;

    public Coin(String id, int viewID, int drawableHead, int drawableTail, boolean isHead) {
        this.id = id;
        this.viewID = viewID;
        this.drawableHead = drawableHead;
        this.drawableTail = drawableTail;
        this.isHead = isHead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDrawableHead() {
        return drawableHead;
    }

    public int getDrawableTail() {
        return drawableTail;
    }

    public int getViewID() {
        return viewID;
    }

    public void setAppearance(int head, int tail) {
        this.drawableHead = head;
        this.drawableTail = tail;
    }
}

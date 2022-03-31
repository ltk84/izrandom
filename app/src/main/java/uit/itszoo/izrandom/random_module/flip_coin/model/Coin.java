package uit.itszoo.izrandom.random_module.flip_coin.model;

import android.widget.ImageView;

public class Coin {
    private int viewID;
    private int drawableHead;
    private int drawableTail;

    public boolean isHead;

    public Coin(int id, int drawableHead, int drawableTail, boolean isHead) {
        this.viewID = id;
        this.drawableHead = drawableHead;
        this.drawableTail = drawableTail;
        this.isHead = isHead;
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
}

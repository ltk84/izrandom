package uit.itszoo.izrandom.random_module.chooser.model;

import android.widget.ImageView;

public class ChooserRing {
    private int index;
    private ImageView circle;
    private float x, y;
    private float dx, dy;

    public ChooserRing(int index, ImageView circle, float x, float y, float dx, float dy) {
        this.index = index;
        this.circle = circle;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void setCircle(ImageView circle) {
        this.circle = circle;
    }

    public ImageView getCircle() {
        return circle;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

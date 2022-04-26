package uit.itszoo.izrandom.random_module.chooser.model;

import android.widget.ImageView;

public class ChooserRing {
    private int id;
    private ImageView circle;
    private float x, y;
    private float dx, dy;

    public ChooserRing(int id, ImageView circle, float x, float y, float dx, float dy) {
        this.id = id;
        this.circle = circle;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

}

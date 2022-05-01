package uit.itszoo.izrandom.random_module.model;

import java.io.Serializable;
import java.util.ArrayList;

public class LuckyWheelData implements Serializable {
    private String id;
    private String title;
    private int textSize;
    private int sliceRepeat;
    private int spinTime;
    private boolean isFairMode;
    private ArrayList<LuckyWheelSlice> slices;

    public LuckyWheelData(String id, String title, int textSize, int sliceRepeat, int spinTime, boolean isFairMode, ArrayList<LuckyWheelSlice> slices) {
        this.id = id;
        this.title = title;
        this.textSize = textSize;
        this.sliceRepeat = sliceRepeat;
        this.spinTime = spinTime;
        this.isFairMode = isFairMode;
        this.slices = slices;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getSliceRepeat() {
        return sliceRepeat;
    }

    public void setSliceRepeat(int sliceRepeat) {
        this.sliceRepeat = sliceRepeat;
    }

    public int getSpinTime() {
        return spinTime;
    }

    public void setSpinTime(int spinTime) {
        this.spinTime = spinTime;
    }

    public boolean isFairMode() {
        return isFairMode;
    }

    public void setFairMode(boolean fairMode) {
        isFairMode = fairMode;
    }

    public ArrayList<LuckyWheelSlice> getSlices() {
        return slices;
    }

    public void setSlices(ArrayList<LuckyWheelSlice> slices) {
        this.slices = slices;
    }
}

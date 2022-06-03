package uit.itszoo.izrandom.random_module.lucky_wheel.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

import uit.itszoo.izrandom.random_module.lucky_wheel.source.LuckyWheelSource;

@Entity(tableName = "luckyWheel")
public class LuckyWheelData implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private int textSize;
    private int sliceRepeat;
    private int spinTime;
    private boolean isFairMode;

    public LuckyWheelData(String id, String title, int textSize, int sliceRepeat, int spinTime, boolean isFairMode) {
        this.id = id;
        this.title = title;
        this.textSize = textSize;
        this.sliceRepeat = sliceRepeat;
        this.spinTime = spinTime;
        this.isFairMode = isFairMode;
    }

    @NotNull
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
        // TODO: Lấy từ db
        ArrayList<LuckyWheelSlice> sliceList = new ArrayList<>();
        LuckyWheelSource.slices.forEach(slice -> {
            if (slice.getWheelID().equals(getId())) {
                try {
                    LuckyWheelSlice s = (LuckyWheelSlice) slice.clone();
                    sliceList.add(s);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

            }
        });
        return sliceList;
    }
}

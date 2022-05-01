package uit.itszoo.izrandom.random_module.lucky_wheel.model;

import com.bluehomestudio.luckywheel.WheelItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomWheelItems implements Serializable {
    List<WheelItem> wheelItems;

    public CustomWheelItems(List<WheelItem> item) {
        wheelItems = new ArrayList<>();
        wheelItems = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomWheelItems that = (CustomWheelItems) o;
        return Objects.equals(wheelItems, that.wheelItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wheelItems);
    }

    public List<WheelItem> getWheelItems() {
        return wheelItems;
    }
}

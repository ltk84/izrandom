package uit.itszoo.izrandom.random_module.lucky_wheel.adapter;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class SliceToWheelItem {
    public static WheelItem convertSliceToWheelItem(Resources resources, LuckyWheelSlice slice) {
        // TODO: đổi thành lấy icon
        // Hiện tại là lấy icon default
        return new WheelItem(Color.parseColor(slice.getColor()),
                BitmapFactory.decodeResource(resources, R.drawable.small_nails_icons), slice.getName());
    }

    public static ArrayList<WheelItem> convertSlicesToWheelItems(Resources resources, ArrayList<LuckyWheelSlice> slices) {
        ArrayList<WheelItem> list = new ArrayList<>();
        slices.forEach(slice -> {
            list.add(convertSliceToWheelItem(resources, slice));
        });
        return list;
    }

    public static LuckyWheelSlice convertWheelItemToSlice(String id, WheelItem wi) {
        // TODO: Icon đang fake
        return new LuckyWheelSlice(id, wi.text, String.format("#%06X", (0xFFFFFF & wi.color)), 12);
    }

    public static ArrayList<LuckyWheelSlice> convertWheelItemsToSlices(ArrayList<String> ids, ArrayList<WheelItem> wis) {
        ArrayList<LuckyWheelSlice> list = new ArrayList<>();
        for (int i = 0; i < wis.size(); i++) {
            list.add(convertWheelItemToSlice(ids.get(i), wis.get(i)));
        }
        return list;
    }


}

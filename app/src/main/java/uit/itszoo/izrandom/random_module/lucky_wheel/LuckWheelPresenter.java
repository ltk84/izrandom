package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;

import uit.itszoo.izrandom.R;

public class LuckWheelPresenter implements LuckyWheelContract.Presenter {
    private final LuckyWheelContract.View view;
    private List<WheelItem> currentListWheel = new ArrayList<>();
    public LuckWheelPresenter(Context context, LuckyWheelContract.View view) {
        this.view = view;
    }
    public void changeWheelItems(List<WheelItem> newWheel)
    {
        currentListWheel = newWheel;
    }

    public List<WheelItem>   getWheelItems()
    {
        return currentListWheel;
    }
    public void initListWheelItems( List<WheelItem> listItems)
    {
        currentListWheel = listItems;
    }

    @Override
    public void start() {

    }
}

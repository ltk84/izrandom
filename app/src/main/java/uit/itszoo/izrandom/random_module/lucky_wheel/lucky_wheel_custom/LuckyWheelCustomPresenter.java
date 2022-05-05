package uit.itszoo.izrandom.random_module.lucky_wheel.lucky_wheel_custom;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class LuckyWheelCustomPresenter implements LuckyWheelCustomContract.Presenter {
    private final LuckyWheelCustomContract.View view;

    private Repository repository;

    private LiveData<List<LuckyWheelData>> allWheelList;

    public LuckyWheelCustomPresenter(Context context, LuckyWheelCustomContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        allWheelList = repository.getAllWheels();
    }

    @Override
    public LiveData<List<LuckyWheelData>> getAllWheelList() {
        return allWheelList;
    }

    @Override
    public List<LuckyWheelSlice> getSlicesByWheelID(String wheelID) {
        return repository.getSlicesByWheelID(wheelID);
    }

    @Override
    public void changeCurrentWheel(String wheelID) {
        repository.changeChosenWheel(wheelID);
    }
}

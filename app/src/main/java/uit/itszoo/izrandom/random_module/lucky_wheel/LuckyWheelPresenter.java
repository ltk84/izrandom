package uit.itszoo.izrandom.random_module.lucky_wheel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class LuckyWheelPresenter implements LuckyWheelContract.Presenter {
    private final LuckyWheelContract.View view;

    private Repository repository;

    private LiveData<UserConfiguration> userConfig;

    private LuckyWheelData currentWheelData;

    public LuckyWheelPresenter(Context context, LuckyWheelContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }

    @Override
    public LiveData<UserConfiguration> getUserConfig() {
        return userConfig;
    }

    @Override
    public LuckyWheelData getWheelData() {
        return currentWheelData;
    }

    @Override
    public void setWheelData(String wheelID) {
        this.currentWheelData = repository.getWheelByID(wheelID);
        view.initLuckyWheel(currentWheelData);
    }

    @Override
    public List<LuckyWheelSlice> getSliceByWheelID(String wheelID) {
        return repository.getSlicesByWheelID(wheelID);
    }

    @Override
    public void start() {

    }
}

package uit.itszoo.izrandom.random_module.lucky_wheel.add_lucky_wheel;

import android.content.Context;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class AddLuckyWheelPresenter implements AddLuckyWheelContract.Present {
    private final AddLuckyWheelContract.View view;

    private Repository repository;

    AddLuckyWheelPresenter(Context context, AddLuckyWheelContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
    }

    @Override
    public void insertWheel(LuckyWheelData wheelData) {
        repository.addWheel(wheelData);
    }

    @Override
    public void insertSlice(LuckyWheelSlice slice) {
        repository.addSlice(slice);
    }
}

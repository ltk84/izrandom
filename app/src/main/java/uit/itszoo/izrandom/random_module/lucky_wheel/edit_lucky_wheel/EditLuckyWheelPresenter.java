package uit.itszoo.izrandom.random_module.lucky_wheel.edit_lucky_wheel;

import android.content.Context;

import java.util.List;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelData;
import uit.itszoo.izrandom.random_module.lucky_wheel.model.LuckyWheelSlice;

public class EditLuckyWheelPresenter implements EditLuckyWheelContract.Presenter {
    private final EditLuckyWheelContract.View view;

    Repository repository;

    EditLuckyWheelPresenter(Context context, EditLuckyWheelContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
    }

    @Override
    public List<LuckyWheelSlice> getSlicesByWheelID(String wheelID) {
        return repository.getSlicesByWheelID(wheelID);
    }

    @Override
    public void deleteSliceByIDs(List<String> ids) {
        repository.deleteSlicesByIDs(ids);
    }

    @Override
    public void updateSlice(LuckyWheelSlice slice) {
        repository.updateSlice(slice);
    }

    @Override
    public boolean checkIfSliceExist(String id) {
        return repository.checkIfSliceExist(id);
    }

    @Override
    public void addSlice(LuckyWheelSlice slice) {
        repository.addSlice(slice);
    }

    @Override
    public void updateWheel(LuckyWheelData wheelData) {
        repository.updateWheel(wheelData);
    }

    @Override
    public int getNumberOfWheel() {
        return repository.countWheel();
    }

    @Override
    public void deleteWheel(LuckyWheelData wheelData) {
        repository.deleteWheel(wheelData);
    }
}

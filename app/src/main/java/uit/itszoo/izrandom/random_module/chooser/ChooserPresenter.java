package uit.itszoo.izrandom.random_module.chooser;

import android.content.Context;

import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.random_module.chooser.model.ChooserTheme;

public class ChooserPresenter implements ChooserContract.Presenter {
    private final ChooserContract.View view;
    private ChooserTheme currentTheme;
    private Repository repository;
    private LiveData<UserConfiguration> userConfig;

    @Override
    public void start() {

    }

    @Override
    public LiveData<UserConfiguration> getUserConfig() {
        return userConfig;
    }

    @Override
    public void initTheme(ChooserTheme newTheme) {
        currentTheme = newTheme;
    }

    @Override
    public ChooserTheme getCurrentTheme() {
        return currentTheme;
    }

    @Override
    public void changeTheme(ChooserTheme theme) {
//        view.applyChangeTheme(theme.getThemeValue());
        repository.changeChooserTheme(theme.getId());
    }

    public ChooserPresenter(Context context, ChooserContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfig = repository.getUserConfiguration();
    }
}

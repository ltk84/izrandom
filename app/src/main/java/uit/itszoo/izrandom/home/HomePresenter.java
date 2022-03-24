package uit.itszoo.izrandom.home;

import android.content.Context;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.database.Repository;
import uit.itszoo.izrandom.database.UserConfiguration;
import uit.itszoo.izrandom.play_module.PlayFragment;
import uit.itszoo.izrandom.random_module.RandomFragment;
import uit.itszoo.izrandom.setting_module.SettingFragment;
import uit.itszoo.izrandom.suggest_module.SuggestFragment;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    private Repository repository;

    private LiveData<UserConfiguration> userConfiguration;

    public HomePresenter(Context context, HomeContract.View view) {
        this.view = view;
        repository = Repository.getInstance(context);
        userConfiguration = repository.getUserConfiguration();
    }

    public void showUserConfiguration() {
        System.out.println(userConfiguration.getValue().dice);
    }

    @Override
    public void start() {

    }

    public boolean onFragmentChanged(MenuItem item, FragmentManager fragmentManager) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.navigation_random:
                fragment = new RandomFragment();
                loadFragment(fragmentManager, fragment);
                showUserConfiguration();
                return true;
            case R.id.navigation_suggest:
                fragment = new SuggestFragment();
                loadFragment(fragmentManager, fragment);
                return true;
            case R.id.navigation_play:
                fragment = new PlayFragment();
                loadFragment(fragmentManager, fragment);
                return true;
            case R.id.navigation_setting:
                fragment = new SettingFragment();
                loadFragment(fragmentManager, fragment);
                return true;
        }

        return false;
    }

    public void loadFragment(FragmentManager fragmentManager, Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.fragment_random_container, fragment, null);
        transaction.commit();
    }
}

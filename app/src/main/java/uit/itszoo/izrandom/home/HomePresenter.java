package uit.itszoo.izrandom.home;

import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.play.PlayFragment;
import uit.itszoo.izrandom.random.RandomFragment;
import uit.itszoo.izrandom.setting.SettingFragment;
import uit.itszoo.izrandom.suggest.SuggestFragment;

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
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

    private void loadFragment(FragmentManager fragmentManager, Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

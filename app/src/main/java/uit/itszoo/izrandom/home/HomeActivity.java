package uit.itszoo.izrandom.home;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_module.RandomFragment;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    HomeContract.Presenter presenter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomePresenter presenter = new HomePresenter(getApplicationContext(), this);
        setPresenter(presenter);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> presenter.onFragmentChanged(item, getSupportFragmentManager()));

        if (savedInstanceState == null) {
            presenter.loadFragment(getSupportFragmentManager(), new RandomFragment());
        }

        SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE);
        boolean defaultDarkModeOn = getResources().getBoolean(R.bool.defaultDarkModeOn);
        boolean darkModeOn = prefs.getBoolean("darkModeOn", defaultDarkModeOn);
        if (darkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
package uit.itszoo.izrandom.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

        presenter.sliceList.observe(this, slices -> {
            System.out.println("Fuck it:  " + slices.size());
            slices.forEach(slice -> System.out.println(slice.getName()));
        });

//        presenter.wheelList.observe(this, luckyWheelData -> {
//            System.out.println("Let fall in love for night:  " + luckyWheelData.size());
//            luckyWheelData.forEach(slice -> System.out.println(slice.getTitle()));
//        });

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
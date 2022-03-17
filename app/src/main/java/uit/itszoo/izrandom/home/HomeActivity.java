package uit.itszoo.izrandom.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import uit.itszoo.izrandom.R;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    HomeContract.Presenter presenter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomePresenter presenter = new HomePresenter(this);
        setPresenter(presenter);
    }


    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
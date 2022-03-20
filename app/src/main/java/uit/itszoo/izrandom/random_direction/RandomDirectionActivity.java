package uit.itszoo.izrandom.random_direction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import uit.itszoo.izrandom.R;
import uit.itszoo.izrandom.random_direction_custom.RandomDirectionCustomActivity;

public class RandomDirectionActivity extends AppCompatActivity {
    ImageButton customButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_direction);

        customButton = findViewById(R.id.custom_button);
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToRandomDirectionCustom = new Intent(getApplicationContext(), RandomDirectionCustomActivity.class);
                startActivity(intentToRandomDirectionCustom);
            }
        });
    }
}
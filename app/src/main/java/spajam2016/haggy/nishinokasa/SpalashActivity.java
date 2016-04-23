package spajam2016.haggy.nishinokasa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SpalashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash);

        final Intent intent = new Intent(this, HelloActivity.class);
        startActivity(intent);
        finish();
    }
}

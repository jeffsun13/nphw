package app.com.example.victoriajuan.nphshomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/**
 * A placeholder fragment containing a simple view.
 */
public class ClassActivity extends AppCompatActivity {

    public ClassActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SaveSharedPreference.getUserName(ClassActivity.this).length() == 0) {
            finish();
            startActivity(new Intent(ClassActivity.this, LoginActivity.class));
        }
        else {
            setContentView(R.layout.activity_class);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ClassActivity.this, CalendarActivity.class));
                }

            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_class, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(ClassActivity.this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.logout) {
            SaveSharedPreference.setUserName(ClassActivity.this, "");
            Intent launchNextActivity;
            launchNextActivity = new Intent(ClassActivity.this, LoginActivity.class);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(launchNextActivity);
            return true;
        }

        return onOptionsItemSelected(item);
    }




}

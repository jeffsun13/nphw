package app.com.example.victoriajuan.nphshomework;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Calendar;


/**
 * A placeholder fragment containing a simple view.
 */
public class ClassActivity extends AppCompatActivity {

    private View mProgressView;
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

            mProgressView = findViewById(R.id.fragment_progress);
            showProgress(true);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            ImageView shit = (ImageView) findViewById(R.id.expandedImage);
            shit.setImageResource(R.mipmap.gradient);


            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(ClassActivity.this, CalendarActivity.class));
                }

            });
        }

        Calendar calendar = Calendar.getInstance();
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
        int month2 = calendar.get(Calendar.MONTH);
        int year2 = calendar.get(Calendar.YEAR);
        GlobalVariables.setDate(day2 ,month2,year2);
    }



    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_class, menu);
        return true;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTitle("Assignments Due " + Integer.toString(GlobalVariables.getMonth()+1)+"/"+
                Integer.toString(GlobalVariables.getDay()+1)+"/"+
                Integer.toString(GlobalVariables.getYear()));

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

        return super.onOptionsItemSelected(item);
    }





}

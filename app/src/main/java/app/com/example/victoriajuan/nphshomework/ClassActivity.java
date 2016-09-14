package app.com.example.victoriajuan.nphshomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class ClassActivity extends AppCompatActivity {

    public ClassActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SaveSharedPreference.getUserName(ClassActivity.this).length() == 0)
        {
            finish();
            startActivity(new Intent(ClassActivity.this, LoginActivity.class));
        }
        else
        {

            setContentView(R.layout.activity_class);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            if(savedInstanceState==null)
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new PlaceholderFragment())
                        .commit();

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

        boolean bool = onOptionsItemSelected(item);
        if (bool)
            return bool;
        else {
            finish();
            return true;
        }
    }

    public static class PlaceholderFragment extends Fragment{
        private ArrayAdapter<String> mForecastAdapter;

        public PlaceholderFragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            String[] data= {
                    "English: Read pages",
                    "Math: Do Problems",
                    "History: Watch Video",
                    "Science: Do Lab",
                    "Elective: Take Pictures",
                    "Elective2: Nothing"
            };

            List<String> weekHomework=new ArrayList<String>(Arrays.asList(data));

            mForecastAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    R.layout.list_item_forecast,
                    R.id.list_item_forecast_textview,
                    weekHomework);

            View rootView=inflater.inflate(R.layout.content_class, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.listview_classes);
            listView.setAdapter(mForecastAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String forecast = mForecastAdapter.getItem(i);
                    Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .putExtra(Intent.EXTRA_TEXT, forecast);
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}

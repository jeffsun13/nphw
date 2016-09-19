package app.com.example.victoriajuan.nphshomework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClassFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    private String[] day1= {
            "English: Read pages",
            "Math: Do Problems",
            "History: Watch Video",
            "Science: Do Lab",
            "Elective: Take Pictures",
            "Elective2: None"
    };

    private String[] day2= {
            "English: Take Notes",
            "Math: Study for Test",
            "History: None ",
            "Science: Write Report",
            "Elective: None",
            "Elective2: Create Presentation"
    };

    public ClassFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.classfragment, menu);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(GlobalVariables.getDay() == 17) {
            updateClasses(day2);
        }
        else{
            updateClasses(day1);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }

         if (id == R.id.logout) {
            SaveSharedPreference.setUserName(getContext(), "");
            Intent launchNextActivity;
            launchNextActivity = new Intent(getContext(), LoginActivity.class);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(launchNextActivity);
            return true;
        }

        return onOptionsItemSelected(item);
    }

    public void updateClasses(String[] classData){
        mForecastAdapter.clear();
        for(String homework:classData){
            mForecastAdapter.add(homework);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        List<String> weekHomework;

        weekHomework = new ArrayList<String>(Arrays.asList(day1));

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                weekHomework);

        View rootView=inflater.inflate(R.layout.fragment_class, container, false);

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

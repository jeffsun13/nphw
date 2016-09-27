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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClassFragment extends Fragment {

    private CustomAdapter adapter;


    private String[] day1 = {
            "Read pages",
            "Do Problems",
            "Watch Video",
            "Do Lab",
            "Take Pictures",
            "None"
    };

    private String[] day2 = {
            "Take Notes",
            "Study for Test",
            "None ",
            "Write Report",
            "None",
            "Create Presentation"
    };

    private String[] titles = {
            "English",
            "Math",
            "History",
            "Science",
            "Elective",
            "Elective2"
    };

    Integer[] imgid={
            R.drawable.default_class_icon,
            R.drawable.default_class_icon,
            R.drawable.default_class_icon,
            R.drawable.default_class_icon,
            R.drawable.default_class_icon,
            R.drawable.default_class_icon,
            R.drawable.default_class_icon,
            R.drawable.default_class_icon,
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
        else {
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
        adapter.clear();
        for(String homework:classData){
            adapter.add(homework);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        List<String> classTitles;
        List<String> weekHomework;
        List<Integer> hwIcons;

        classTitles = new ArrayList<String>(Arrays.asList(titles));
        weekHomework = new ArrayList<String>(Arrays.asList(day1));
        hwIcons = new ArrayList<Integer>(Arrays.asList(imgid));


        View rootView=inflater.inflate(R.layout.fragment_class, container, false);


        adapter = new CustomAdapter(getActivity(), classTitles, weekHomework, hwIcons);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_classes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String forecast = adapter.getItem(i);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        return rootView;
    }


}

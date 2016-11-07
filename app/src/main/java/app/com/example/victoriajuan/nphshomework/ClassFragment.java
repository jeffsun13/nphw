package app.com.example.victoriajuan.nphshomework;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClassFragment extends Fragment {

    private CustomAdapter adapter;


    private String[] day1 = {
            "Read pages. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Do Problems. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Watch Video. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Do Lab. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Take Pictures. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "None. Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
    };

    private String[] day2 = {
            "Take Notes. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Study for Test. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "None. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Write Report. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "None. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Create Presentation. Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
    };

    private String[] titles = {
            "English",
            "Math",
            "History",
            "Science",
            "Elective",
            "Elective2"
    };

    private Integer[] imgid={
            R.mipmap.paper,
            R.mipmap.compass,
            R.mipmap.pencil,
            R.mipmap.atom,
            R.mipmap.glasses,
            R.mipmap.ruler
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
            updateClasses();
        }
        else {
            updateClasses();
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;

        }

        if (id == R.id.action_refresh) {
            updateClasses();
            return true;
        }

         if (id == R.id.logout) {
            SaveSharedPreference.setUserName(getActivity(), "");
            Intent launchNextActivity;
            launchNextActivity = new Intent(getActivity(), LoginActivity.class);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(launchNextActivity);
            return true;
        }

        return onOptionsItemSelected(item);
    }

    public void updateClasses(){
        FetchHomeworkClass weatherTask = new FetchHomeworkClass();
        weatherTask.execute();

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

                if (GlobalVariables.getDay() == 17)
                    GlobalVariables.setDetailInfo(titles[i], imgid[i], day1[i]);
                else
                    GlobalVariables.setDetailInfo(titles[i], imgid[i], day2[i]);

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        return rootView;
    }



    public class FetchHomeworkClass extends AsyncTask<String, Void, String[]> {

        private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_LIST = "list";
            final String OWM_VALUE = "value";
            final String OWM_NAME = "name";
            final String OWM_TEACHER = "teacher";
            final String OWM_SUBJECT = "subject";

            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

            String[] resultStrs = new String[3];

            for(int i = 0; i < weatherArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String name;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForecast.getJSONArray(OWM_VALUE).getJSONObject(0);
                name = weatherObject.getString(OWM_NAME);


                resultStrs[i] = name;
            }

            return resultStrs;

        }

        @Override
        protected String[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL = "http://nphw.herokuapp.com/all-classes";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            try {
                return getWeatherDataFromJson(forecastJsonStr, numDays);
            } catch (JSONException e) {
                Log.e("CLASSFRAGMENT", e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                adapter.clear();
                for (String dayForecastStr : result) {
                    adapter.add(dayForecastStr, dayForecastStr);
                }
            }
        }
    }


}

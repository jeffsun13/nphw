package app.com.example.victoriajuan.nphshomework;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
    private View mProgressView;
    private View mLoginFormView;
    private Handler mHandler = new Handler();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView listView;

    private String[] day1 = {
            "Read pages. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Do Problems. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Watch Video. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Read pages. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Do Problems. Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
            "Watch Video. Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
    };

    private String[] titles = {
            "English",
            "Math",
            "History",
            "Science",
            "Spanish",
            "Photography"
    };


    private Integer[] imgid={
            R.drawable.ic_description_black_24dp,
            R.mipmap.compass,
            R.mipmap.pencil,
            R.mipmap.glasses,
            R.mipmap.ruler,
            R.drawable.ic_camera_alt_black_24dp
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            updateClasses();
            showProgress(true);
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    showProgress(false);
                }
            }, 1000);
            return true;
        }

        if (id == R.id.class_picker) {
            startActivity(new Intent(getActivity(), ClassPickerActivity.class));
            return true;
        }

        return onOptionsItemSelected(item);
    }

    public void updateClasses(){
        FetchHomeworkClass weatherTask = new FetchHomeworkClass();
        weatherTask.execute();
        adapter.notifyDataSetChanged();
    }

    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
        listView = (ListView) rootView.findViewById(R.id.listview_classes);

        //If version>lollipop, allows nested scrolling view to close collapsingtoolbar from fragment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String forecast = adapter.getItem(i);

                if (GlobalVariables.getDay() == 17)
                    GlobalVariables.setDetailInfo(adapter.getTitle(i), imgid[i], adapter.getDescr(i));
                else
                    GlobalVariables.setDetailInfo(adapter.getTitle(i), imgid[i], adapter.getDescr(i));

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mProgressView = getView().findViewById(R.id.fragment_progress);
        mLoginFormView = getView().findViewById(R.id.listview_classes);
        showProgress(true);
        updateClasses();
        showProgress(false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        updateClasses();
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        }, 1000);
                    }
                }
        );
    }

    public class FetchHomeworkClass extends AsyncTask<String, Void, String[]> {

        //Formats what you get back from server
        private String[] getClassesDataFromJson(String forecastJsonStr, int numDays)
                throws JSONException {

            JSONObject weatherObject = new JSONObject(forecastJsonStr);

            String thisShit = weatherObject.toString();

            int thisLoc = thisShit.indexOf("classes");

            ArrayList<String> classes = new ArrayList<String>();

            thisShit = thisShit.substring(thisLoc+9);

            for(int rep = 0;rep<thisShit.length()-4;rep+=4) {
                classes.add(thisShit.substring(rep+2,rep+3));
            }

            String[] finProduct = new String[classes.size()];

            return classes.toArray(finProduct);

        }

        private ArrayList<String> getHomeworkDataFromJson(ArrayList<String> forecastJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_CLASS = "className";
            final String OWM_SUBJECT = "assignment";

            ArrayList<String> resultStrs = new ArrayList<String>();

            for (int rep = 0; rep < forecastJsonStr.size(); rep++)
            {
                JSONObject weatherArray = new JSONObject(forecastJsonStr.get(rep));

                String className;
                String assignment;

                className = weatherArray.getString(OWM_CLASS);
                assignment = weatherArray.getString(OWM_SUBJECT);
                Log.e("SHITSHITSHIT",className);
                GlobalVariables.addClassNames(className);

                resultStrs.add(className + "-" + assignment);
            }

            return resultStrs;

        }

        //Gets back from server
        @Override
        protected String[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            ArrayList<String> forecastJsonStr2 = new ArrayList<String>();
            String format = "json";
            String units = "metric";
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL = "http://nphw.herokuapp.com/api/own-classes";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty ("token", SaveSharedPreference.getLoginToken(getActivity()));
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
                GlobalVariables.clearList();
                String[] strs = getClassesDataFromJson(forecastJsonStr, numDays);
                for(int rep = 0;rep < strs.length; rep++) {
                    GlobalVariables.addClass(strs[rep]);
                }
            } catch (JSONException e) {
                Log.e("CLASSFRAGMENT", e.getMessage(), e);
                e.printStackTrace();
            }

            List<String> arr = GlobalVariables.getClasses();
            Log.e("HE:OOOO", arr.toString());

            //- 3 NEEDS TO BE FIXED; check if number before inserting?
            for (int temp = 0; temp < arr.size() - 3; temp++)
            {
                String classID = arr.get(temp);

                try {
                    final String FORECAST_BASE_URL = "http://nphw.herokuapp.com/api/get-homework?";

                    Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                            .appendQueryParameter("classID", classID)
                            .appendQueryParameter("dateAssigned","2017-01-01")
                            .build();

                    URL url = new URL(builtUri.toString());

                    // Create the request to OpenWeatherMap, and open the connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty ("token", SaveSharedPreference.getLoginToken(getActivity()));
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

                    forecastJsonStr2.add(buffer.toString());

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
            }

            try {
                ArrayList<String> str = getHomeworkDataFromJson(forecastJsonStr2, numDays);
                return str.toArray(new String[str.size()]);
            } catch (JSONException e) {
                Log.e("CLASSFRAGMENT", e.getMessage(), e);
                e.printStackTrace();
            }


            return null;
        }

        //Finishes the asynctask
        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                adapter.clear();
                for (String dayForecastStr : result) {
                    adapter.add(dayForecastStr.substring(0,dayForecastStr.indexOf("-")), dayForecastStr.substring(dayForecastStr.indexOf("-")+1));
                }
            }
            listView.setAdapter(adapter);
            ((ClassActivity)getActivity()).showProgress(false);
        }
    }
}

package app.com.example.victoriajuan.nphshomework;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.com.example.victoriajuan.nphshomework.SaveSharedPreference.LOGIN_TOKEN;

public class ClassPickerActivity extends AppCompatActivity {


    private AddClassTask mAuthTask = null;
    private ArrayAdapter<String> adapter;
    private View mProgressView;
    private View mLoginFormView;
    private Handler mHandler = new Handler();
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
            "English",
            "Math",
            "History"
    };
    private String[] imgid = {
    };
    private static final Map<String, String> myMap;
    static
    {
        myMap = new HashMap<String, String>();
        myMap.put("IB Chemistry Year 2", "1");
        myMap.put("IB Math HL", "2");
        myMap.put("Honors Independent CS", "3");
        myMap.put("IB 20th Century", "4");
        myMap.put("IB English Year 2", "5");
        myMap.put("Theory of Knowledge", "6");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.e("thefirsttoken",SaveSharedPreference.getLoginToken(ClassPickerActivity.this));
        setContentView(R.layout.activity_class_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mProgressView = findViewById(R.id.picker_progress);
        mLoginFormView = findViewById(R.id.listview_classes2);
        List<String> classTitles;
        classTitles = new ArrayList<String>(Arrays.asList(titles));
        adapter = new ArrayAdapter<String>(
                ClassPickerActivity.this,
                R.layout.list_item_classpicker,
                R.id.list_item_classpicker,
                classTitles);
        listView = (ListView) findViewById(R.id.listview_classes2);
        updateClasses();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String forecast = adapter.getItem(i);
                new AlertDialog.Builder(ClassPickerActivity.this)
                        .setTitle("Enroll in Class?")
                        .setMessage("Would you like to add this class to your schedule?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Log.e("earlytoken",SaveSharedPreference.getLoginToken(ClassPickerActivity.this));
                                mAuthTask = new ClassPickerActivity.AddClassTask(myMap.get(forecast));
                                mAuthTask.execute();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
    public void updateClasses(){
        FetchHomeworkClass weatherTask = new FetchHomeworkClass();
        weatherTask.execute();
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_class_picker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public class AddClassTask extends AsyncTask<Void, Void, Boolean> {

        AddClassTask(String forecast) {
           GlobalVariables.addClass(forecast);
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            DataOutputStream printout;
            DataInputStream input;
            try {

                final String FORECAST_BASE_URL = "http://nphw.herokuapp.com/api/add-class";
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .build();
                //Log.e("theclass",SaveSharedPreference.getClasses(ClassPickerActivity.this));
                URL url = new URL(builtUri.toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                //Log.e("token",SaveSharedPreference.getLoginToken(ClassPickerActivity.this));
                urlConnection.setRequestProperty ("token", SaveSharedPreference.getLoginToken(ClassPickerActivity.this));
                //Log.e("token2",urlConnection.getRequestProperty("token"));
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type","application/json");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                JSONObject jsonArr = new JSONObject();
                JSONArray fuckVic = new JSONArray();
                OutputStreamWriter wr = new   OutputStreamWriter(urlConnection.getOutputStream());
                List<String> myClasses = GlobalVariables.getClasses();
                for(String shit:myClasses)
                {
                    fuckVic.put(shit);
                }
                try{
                    jsonArr.put("classes",fuckVic);
                    Log.e("classes",jsonArr.toString());
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                printout = new DataOutputStream(urlConnection.getOutputStream());
                printout.writeBytes(jsonArr.toString());
                printout.flush ();
                printout.close ();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                }

                reader = new BufferedReader(new
                        InputStreamReader(inputStream));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                return true;
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
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
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

            JSONArray weatherArray = new JSONArray(forecastJsonStr);

            String[] resultStrs = new String[6];

            for(int i = 0; i < weatherArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String name;
                String teacher;

                // Get the JSON object representing the day
                JSONObject dayForecast = weatherArray.getJSONObject(i);

                // description is in a child array called "weather", which is 1 element long.
                JSONObject weatherObject = dayForecast.getJSONObject(OWM_VALUE);
                name = weatherObject.getString(OWM_NAME);
                teacher=weatherObject.getString(OWM_TEACHER);

                resultStrs[i] = name+"-"+teacher;
            }

            return resultStrs;

        }

        @Override
        protected String[] doInBackground(String... params) {
            showProgress(true);
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;
            int numDays = 7;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                final String FORECAST_BASE_URL = "http://nphw.herokuapp.com/api/all-classes";

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
                    adapter.add(dayForecastStr.substring(0,dayForecastStr.indexOf("-")));
                }
            }
            listView.setAdapter(adapter);
            showProgress(false);
        }
    }
}

package akshay.demoapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressDialog pDialog;
    private ArrayList<GitHubUser> userArrayList;
    private ListAdaper adaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (ListView) findViewById(R.id.listView);
        userArrayList = new ArrayList<>();
        adaper = new ListAdaper(this, userArrayList);
        listView.setAdapter(adaper);
        adaper.notifyDataSetChanged();
        getUserCommit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    private void getUserCommit() {
        String APIRequest = "APIRequest";

        String url = "https://api.github.com/repos/rails/rails/commits";

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue mRequestQueue;
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("APP", response.toString());
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        GitHubUser user = new GitHubUser();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        user.setName(jsonObject.getJSONObject("commit").getJSONObject("author").getString("name"));
                        user.setCommit(jsonObject.getString("sha"));
                        user.setCommitMessage(jsonObject.getJSONObject("commit").getString("message"));
                        Log.e("User", "User - : " + user.toString());
                        userArrayList.add(user);
                    }
                    adaper.notifyDataSetChanged();
                    pDialog.hide();
                } catch (Exception e) {
                    pDialog.hide();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("APP", "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(strReq);
    }


}

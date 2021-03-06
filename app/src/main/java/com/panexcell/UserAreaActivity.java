package com.panexcell;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserAreaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView lvPrograms;
    private ProgramListAdaptar adaptar;
    private List<Programs> programs ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvPrograms = (ListView)findViewById(R.id.lv_Details);
        programs = new ArrayList<>();

        final Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONArray recs = jsonObject.getJSONArray("data");
                    boolean success = jsonObject.getBoolean("success");

                    if(success){

                        for (int i = 0; i < recs.length(); ++i) {
                            JSONObject rec = recs.getJSONObject(i);
                            int id = rec.getInt("id");
                            String title = rec.getString("title");
                            String compensation = rec.getString("compensation");
                            programs.add(new Programs(id,title,compensation,""));
                        }
//                        ArrayList<String> list = new ArrayList<String>();
//                       // JSONArray jsonArray = (JSONArray)jsonObject;
//                        if (jsonArray != null) {
//                            int len = jsonArray.length();
//                            for (int i=0;i<len;i++){
//                                list.add(jsonArray.get(i).toString());
//                            }
//                        }  programs.add(new Programs(1,"Title one","some desciption here",""));
//                        programs.add(new Programs(2,"Title two","some desciption here",""));
//                        programs.add(new Programs(3,"Title three","some desciption here",""));
//                        programs.add(new Programs(4, "Title four", "No descriptions",""));
//                        programs.add(new Programs(5,"Title five","",""));
//                        programs.add(new Programs(6,"Title six","some desciption here",""));
//                        programs.add(new Programs(7,"Title seven","",""));
//                        programs.add(new Programs(8, "Title eight", "",""));

                        adaptar = new ProgramListAdaptar(getApplication(), programs);
                        lvPrograms.setAdapter(adaptar);
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        UserDetails userDetails = new UserDetails();
        UserAreaRequest userAreaRequest = new UserAreaRequest(userDetails.getUsername(), responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(UserAreaActivity.this);
        requestQueue.add(userAreaRequest);



        lvPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "id" +view.getTag(), Toast.LENGTH_SHORT).show();
                //Host call
                //save the data


                Intent SpecificProgramActivityIntent = new Intent(UserAreaActivity.this, SpecificProgramActivity.class);
                SpecificProgramActivityIntent.putExtra("message", id);
                UserAreaActivity.this.startActivity(SpecificProgramActivityIntent);
            }
        });


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_area2, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_aboutUs) {

            Intent AboutUsIntent = new Intent(UserAreaActivity.this, AboutUs.class);
            UserAreaActivity.this.startActivity(AboutUsIntent);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

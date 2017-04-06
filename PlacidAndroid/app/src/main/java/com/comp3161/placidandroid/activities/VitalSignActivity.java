package com.comp3161.placidandroid.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.adapters.VitalSignListAdapter;
import com.comp3161.placidandroid.rest.Rest;
import com.comp3161.placidandroid.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VitalSignActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    RecyclerView recyclerView;
    VitalSignListAdapter vitalSignListAdapter;
    TextView mTvPlaceHolder;
    ImageView mImgPlaceHolder;
    ProgressBar mProgressBar;
    List<String> vitals =new ArrayList<>();
    String mUrl = Constants.GET_VITAL_SIGNS;
    SwipeRefreshLayout swipeRefreshLayout;
    private String mUserType;
    private String mUserId;
    private String mPatientId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_sign);


        getSupportActionBar().setTitle("Vital Signs");

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS,Context.MODE_PRIVATE);

        mUserType = sharedPreferences.getString("user_type","none");
        mUserId = sharedPreferences.getString("userid","none");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUserType.contentEquals("Nurse")){
                    showDialog();
                }else{
                    Toast.makeText(view.getContext(),"You must be a nurse",Toast.LENGTH_SHORT).show();
                }


            }
        });

        initViews();

        setUpProgressBar();

        setUpRecyclerView();

        setUpSwipeRefresh();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            mUrl+="/"+extras.getString("patient_id","none");
            mPatientId = extras.getString("patient_id","none");
        }

        new LoadVitalTask(this,mUrl).execute();
    }

    private void showDialog()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_add_vital, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText editTextVital = (EditText)promptsView.findViewById(R.id.editTextVital);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        null)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        String vital = editTextVital.getText().toString();

                        if(TextUtils.isEmpty(vital)){
                            Toast.makeText(view.getContext(),"Enter vital",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        sendJsonData(vital,Constants.POST_VITAL_SIGN+"/"+mPatientId);
                        dialog.dismiss();





                    }
                });
            }
        });

        // show it
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }


    private void sendJsonData(String vital,String url)
    {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("vital",vital);
            jsonObject.put("nurse_id",mUserId);
            new PostVitalTask(this,url,jsonObject.toString()).execute();
        }catch (JSONException j){
            Log.d("JsonException",j.getMessage());
        }
    }
    private void setUpSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark);

        swipeRefreshLayout.setOnRefreshListener(this);
    }
    private void initViews()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) findViewById(R.id.vital_listview);
        mTvPlaceHolder = (TextView) findViewById(R.id.txt_notpresent);
        mImgPlaceHolder = (ImageView) findViewById(R.id.no_content);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void setUpProgressBar()
    {
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

    }

    private void setUpRecyclerView()
    {
        vitalSignListAdapter = new VitalSignListAdapter(this,vitals);
        recyclerView.setAdapter(vitalSignListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<String> getVitalsFromJson(String json)
    {
        List<String> vitals =new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                String name = (String) jsonArray.get(i);
                vitals.add(name);
            }

        }catch (JSONException j){
            Log.d("Exception",j.getMessage());
            return null;
        }

        return vitals;
    }

    @Override
    public void onRefresh() {
        new LoadVitalTask(this,mUrl).execute();
    }


    private class LoadVitalTask extends AsyncTask<Void,Void,Boolean>
    {

        Context context;
        String url;
        public LoadVitalTask(Context context,String url)
        {
            this.context = context;
            this.url = url;

        }
        @Override
        protected void onPreExecute() {
            mImgPlaceHolder.setVisibility(View.GONE);
            mTvPlaceHolder.setVisibility(View.GONE);

            if (vitals.size() == 0) { // check if any news are present
                mProgressBar.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Rest rest = new Rest();
            String response = rest.sendGetRequest(url);
            Log.d("Vitals",response);
            if(TextUtils.isEmpty(response))
            {
                return false;
            }
            vitals = getVitalsFromJson(response);

            if(vitals==null || vitals.size()==0)
            {
                return false;
            }

            return true;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            swipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);
            if (result) {
                vitalSignListAdapter.updateVitalList(vitals);
            }

            if (vitals==null || vitals.size() == 0) {
                mImgPlaceHolder.setVisibility(View.VISIBLE);
                mTvPlaceHolder.setVisibility(View.VISIBLE);
            }
        }


    }

    private class PostVitalTask extends AsyncTask<Void,Void,Boolean>
    {
        Context context;
        String url;
        String json;
        ProgressDialog pd;
        public PostVitalTask(Context context,String url,String json)
        {
            this.context = context;
            this.url = url;
            this.json = json;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setMessage("Adding vital sign");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Rest rest = new Rest();
            String response = rest.sendPostRequest(url,json);

            if(TextUtils.isEmpty(response))
                return false;

            try{
                Log.d("Response",response);
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status")){
                    if(jsonObject.getString("status").contentEquals("success")){
                        return true;
                    }else{
                        return false;
                    }

                }else{
                    return false;
                }
            }catch (JSONException j){
                Log.d("JsonException",j.getMessage());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pd.dismiss();
            if(result){
                Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
            }


        }


    }
}

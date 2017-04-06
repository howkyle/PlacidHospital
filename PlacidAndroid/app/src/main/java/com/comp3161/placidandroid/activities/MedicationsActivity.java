package com.comp3161.placidandroid.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.adapters.MedicationListAdapter;
import com.comp3161.placidandroid.fragments.PatientsFragment;
import com.comp3161.placidandroid.models.Medication;
import com.comp3161.placidandroid.models.Patient;
import com.comp3161.placidandroid.rest.RestMedication;
import com.comp3161.placidandroid.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MedicationsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    RecyclerView recyclerView;
    MedicationListAdapter mMedListAdapter;
    TextView mTvPlaceHolder;
    ImageView mImgPlaceHolder;
    ProgressBar mProgressBar;
    List<Medication> mMedications=new ArrayList<>();
    String mUrl = Constants.GET_ALLERGIES;
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergies);


        getSupportActionBar().setTitle("Allergies");

        initViews();

        setUpProgressBar();

        setUpRecyclerView();

        setUpSwipeRefresh();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            mUrl+="/"+extras.getString("patient_id","none");
        }

        new LoadMedicationTask(this,mUrl).execute();
    }


    private void setUpSwipeRefresh() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary,
                R.color.colorPrimaryDark);

        swipeRefreshLayout.setOnRefreshListener(this);
    }
    private void initViews()
    {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        recyclerView = (RecyclerView) findViewById(R.id.med_listview);
        mTvPlaceHolder = (TextView) findViewById(R.id.txt_notpresent);
        mImgPlaceHolder = (ImageView) findViewById(R.id.no_meds);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void setUpProgressBar()
    {
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

    }

    private void setUpRecyclerView()
    {
        mMedListAdapter = new MedicationListAdapter(this,mMedications);
        recyclerView.setAdapter(mMedListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Medication> getMedicationsFromJson(String json)
    {
        List<Medication> medications=new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                String name = (String) jsonArray.get(i);
                Medication medication = new Medication(name);
                medications.add(medication);
            }

        }catch (JSONException j){
            Log.d("Exception",j.getMessage());
            return null;
        }

        return medications;
    }

    @Override
    public void onRefresh() {
        new LoadMedicationTask(this,mUrl).execute();
    }

    private class LoadMedicationTask extends AsyncTask<Void,Void,Boolean>
    {

        Context context;
        String url;
        public LoadMedicationTask(Context context,String url)
        {
            this.context = context;
            this.url = url;

        }
        @Override
        protected void onPreExecute() {
            mImgPlaceHolder.setVisibility(View.GONE);
            mTvPlaceHolder.setVisibility(View.GONE);

            if (mMedications.size() == 0) { // check if any news are present
                mProgressBar.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            RestMedication restMedication = new RestMedication();
            String response = restMedication.getMedications(url);
            Log.d("Medications",response);
            if(TextUtils.isEmpty(response))
            {
                return false;
            }
            mMedications = getMedicationsFromJson(response);

            if(mMedications==null || mMedications.size()==0)
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
                mMedListAdapter.updateMedicationList(mMedications);
            }

            if (mMedications==null || mMedications.size() == 0) {
                mImgPlaceHolder.setVisibility(View.VISIBLE);
                mTvPlaceHolder.setVisibility(View.VISIBLE);
            }
        }


    }
}

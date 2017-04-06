package com.comp3161.placidandroid.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.adapters.MedicalDataListAdapter;
import com.comp3161.placidandroid.models.Intern;
import com.comp3161.placidandroid.models.MedicalData;
import com.comp3161.placidandroid.rest.Rest;
import com.comp3161.placidandroid.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MedicalDataActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{




    private RecyclerView recyclerView;
    private MedicalDataListAdapter mMedListAdapter;
    private TextView mTvPlaceHolder;
    private ImageView mImgPlaceHolder;
    private ProgressBar mProgressBar;
    private List<MedicalData> mMedDatas=new ArrayList<>();
    private String mUrl = Constants.GET_MEDICAL_DATA;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String mPatientId;
    private String mDoctorId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_data);
        Bundle extras = getIntent().getExtras();

        getSupportActionBar().setTitle(extras.getString("patient_name","none")+ " Medical Data");


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS,Context.MODE_PRIVATE);

        mDoctorId = sharedPreferences.getString("userid","none");

        initViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });

        setUpProgressBar();

        setUpRecyclerView();

        setUpSwipeRefresh();


        if(extras!=null){
            mUrl+="/"+extras.getString("patient_id","none");
            mPatientId = extras.getString("patient_id","none");
        }

        new LoadMedicalDataTask(this,mUrl).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_med_data, menu);
        return true;


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vital_sign:
                Intent intent = new Intent(this,VitalSignActivity.class);
                intent.putExtra("patient_id",mPatientId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        recyclerView = (RecyclerView) findViewById(R.id.meddata_listview);
        mTvPlaceHolder = (TextView) findViewById(R.id.txt_notpresent);
        mImgPlaceHolder = (ImageView) findViewById(R.id.no_content);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void setUpProgressBar()
    {
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

    }

    private void showDialog()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_add_med_data, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText editTextProcedure = (EditText)promptsView.findViewById(R.id.editTextProcedure);

        final EditText editTextDiagnosis = (EditText)promptsView.findViewById(R.id.editTextDiagnosis);
        //final EditText editTextTitle = (EditText)promptsView.findViewById(R.id.editTextPostTitle);
        final EditText editTextTestResult = (EditText)promptsView.findViewById(R.id.editTextTestResult);

        final EditText editTextTreatment = (EditText)promptsView.findViewById(R.id.editTextTreatment);

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
                        String diagnosis = editTextDiagnosis.getText().toString();
                        String procedure = editTextProcedure.getText().toString();
                        String test_result = editTextTestResult.getText().toString();
                        String treatment = editTextTreatment.getText().toString();

                        if(TextUtils.isEmpty(procedure)){
                            Toast.makeText(view.getContext(),"Enter procedure",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(TextUtils.isEmpty(test_result)){
                            Toast.makeText(view.getContext(),"Enter test result",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(TextUtils.isEmpty(diagnosis)){
                            Toast.makeText(view.getContext(),"Enter diagnosis",Toast.LENGTH_SHORT).show();
                            return;
                        }


                        if(TextUtils.isEmpty(treatment)){
                            Toast.makeText(view.getContext(),"Enter treatment",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        postMedicalData(procedure,test_result,diagnosis,treatment);
                        dialog.dismiss();





                    }
                });
            }
        });

        // show it
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }

    private void postMedicalData(String procedure,String test_result,String diagnosis,String treatment)
    {
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("procedure",procedure);
            jsonObject.put("result",test_result);
            jsonObject.put("diagnosis",diagnosis);
            jsonObject.put("treatment",treatment);
            jsonObject.put("doctor_id",mDoctorId);
           // Toast.makeText(this,"Querying..",Toast.LENGTH_SHORT).show();
            new PostMedDataTask(this,Constants.ADD_MED_DATA,jsonObject.toString()).execute();

        }catch (JSONException j){
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
            Log.d("JSON Exception",j.getMessage());
        }



    }

    private void setUpRecyclerView()
    {
        mMedListAdapter = new MedicalDataListAdapter(this,mMedDatas);
        recyclerView.setAdapter(mMedListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<MedicalData> getMedicalDataFromJson(String json)
    {
        List<MedicalData> medDatas =new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String procedure = jsonObject.getString("procedure");
                String test_result = jsonObject.getString("result");
                String diagnosis = jsonObject.getString("diagnosis");
                String treatment = jsonObject.getString("treatment");
                MedicalData medicalData = new MedicalData(procedure,test_result,diagnosis,treatment);
                medDatas.add(medicalData);
            }

        }catch (JSONException j){
            Log.d("Exception",j.getMessage());
            return null;
        }

        return medDatas;
    }

    @Override
    public void onRefresh() {
        new LoadMedicalDataTask(this,mUrl).execute();
    }

    private class LoadMedicalDataTask extends AsyncTask<Void,Void,Boolean> {

        Context context;
        String url;

        public LoadMedicalDataTask(Context context, String url) {
            this.context = context;
            this.url = url;

        }

        @Override
        protected void onPreExecute() {
            mImgPlaceHolder.setVisibility(View.GONE);
            mTvPlaceHolder.setVisibility(View.GONE);

            if (mMedDatas.size() == 0) { // check if any news are present
                mProgressBar.setVisibility(View.VISIBLE);

            }
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            Rest rest = new Rest();
            String response = rest.sendGetRequest(url);
            Log.d("Medical Data", response);
            if (TextUtils.isEmpty(response)) {
                return false;
            }
            mMedDatas = getMedicalDataFromJson(response);

            if (mMedDatas == null || mMedDatas.size() == 0) {
                return false;
            }

            return true;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            swipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);
            if (result) {
                mMedListAdapter.updateMedicalDataList(mMedDatas);
            }

            if (mMedDatas == null || mMedDatas.size() == 0) {
                mImgPlaceHolder.setVisibility(View.VISIBLE);
                mTvPlaceHolder.setVisibility(View.VISIBLE);
            }
        }
    }

    private class PostMedDataTask extends AsyncTask<Void,Void,Boolean>{


        Context context;
        ProgressDialog pd;
        String json;
        String url;
        String response;
        public PostMedDataTask(Context context,String url,String json){
            this.context = context;
            this.url = url;
            this.json = json;
        }
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(context);
            pd.setMessage("Registering user");
            pd.show();
        }



        @Override
        protected Boolean doInBackground(Void... voids) {
            Rest rest = new Rest();
            Log.d("Patient",mPatientId);
            response = rest.sendPostRequest(url+"/"+mPatientId,json);

            Log.d("Response",response);
            if(TextUtils.isEmpty(response)){
                return false;
            }else{
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("status").contentEquals("success")){
                        return true;
                    }else{
                        return false;
                    }
                }catch (JSONException j){
                    return false;
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pd.dismiss();
            if(result){
                Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"Update failed "+ response,Toast.LENGTH_SHORT).show();
            }

        }
    }




}

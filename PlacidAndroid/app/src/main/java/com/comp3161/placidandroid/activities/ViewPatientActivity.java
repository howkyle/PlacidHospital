package com.comp3161.placidandroid.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.models.Intern;
import com.comp3161.placidandroid.rest.RestPatient;
import com.comp3161.placidandroid.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewPatientActivity extends AppCompatActivity {


    private Button updateButton;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText phoneNumEdit;
    private EditText dobEdit;
    private EditText addressEdit;
    public boolean editable = false;
    private Bundle extras;
    private String mPatientId;
    private String mUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);



        extras = getIntent().getExtras();
        getSupportActionBar().setTitle(extras.getString("first_name")+ " "+ extras.getString("last_name"));

        initViews();

        mPatientId = extras.getString("id");

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE);
        mUserType = sharedPreferences.getString("user_type","none");
        Log.d("Type",mUserType);
        if(mUserType.contentEquals("Secretary")){
            editable = true;
        }

        setUpViews();



    }


    private void initViews()
    {
        firstNameEdit = (EditText) findViewById(R.id.first_name);
        lastNameEdit = (EditText) findViewById(R.id.last_name);
        dobEdit = (EditText) findViewById(R.id.dob);
        addressEdit = (EditText) findViewById(R.id.address);
        phoneNumEdit = (EditText) findViewById(R.id.phone_number);
        updateButton = (Button) findViewById(R.id.update_button);
    }

    private void setUpViews()
    {
        if(!editable){
            firstNameEdit.setEnabled(false);
            lastNameEdit.setEnabled(false);
            dobEdit.setEnabled(false);
            addressEdit.setEnabled(false);
            phoneNumEdit.setEnabled(false);
            updateButton.setVisibility(View.GONE);
        }
        firstNameEdit.setText(extras.getString("first_name"));
        lastNameEdit.setText(extras.getString("last_name"));
        dobEdit.setText(extras.getString("dob"));
        addressEdit.setText(extras.getString("address"));
        phoneNumEdit.setText(extras.getString("tel_number"));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstname = firstNameEdit.getText().toString();
                String lastName = lastNameEdit.getText().toString();
                String dob = dobEdit.getText().toString();
                String address = addressEdit.getText().toString();
                String phoneNumber = phoneNumEdit.getText().toString();

                if(TextUtils.isEmpty(firstname))
                {
                    Toast.makeText(view.getContext(),"Enter first name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(lastName))
                {
                    Toast.makeText(view.getContext(),"Enter last name",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(lastName))
                {
                    Toast.makeText(view.getContext(),"Enter phone number",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(dob))
                {
                    Toast.makeText(view.getContext(),"Enter dob",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(address))
                {
                    Toast.makeText(view.getContext(),"Enter address",Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject jsonObject = new JSONObject();
                try {

                    jsonObject.put("first_name",firstname);
                    jsonObject.put("last_name",lastName);
                    jsonObject.put("address",address);
                    jsonObject.put("dob",dob);
                    jsonObject.put("tel_number",phoneNumber);

                }catch (JSONException j){
                    Log.d("JSONException",j.getMessage());
                    return;
                }

                Log.d("JSON",jsonObject.toString());

                new UpdatePatientDataTask(view.getContext(), Constants.UPDATE_PATIENT,
                        jsonObject.toString()).execute();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_patient_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.med_data:
                if(mUserType.contentEquals("Secretary")){
                    Toast.makeText(this,"You need to be a doctor/nurse",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent2 = new Intent(this,MedicalDataActivity.class);
                    intent2.putExtra("patient_id",mPatientId);
                    intent2.putExtra("patient_name",extras.getString("first_name")+" "+ extras.getString("last_name"));
                    startActivity(intent2);
                }

                return true;
            case R.id.allergies:
                if(mUserType.contentEquals("Secretary")){
                    Toast.makeText(this,"You need to be a doctor/nurse",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(this, MedicationsActivity.class);
                    intent.putExtra("patient_id", mPatientId);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class UpdatePatientDataTask extends AsyncTask<Void,Void,Boolean>{


        Context context;
        ProgressDialog pd;
        String json;
        String url;
        public UpdatePatientDataTask(Context context,String url,String json){
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
            RestPatient restPatient = new RestPatient();
            String response = restPatient.updatePatient(url+"/"+mPatientId,json);
            Log.d("Patient",mPatientId);
            Log.d("URL",url);
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
                Toast.makeText(context,"Update failed",Toast.LENGTH_SHORT).show();
            }

        }
    }






}

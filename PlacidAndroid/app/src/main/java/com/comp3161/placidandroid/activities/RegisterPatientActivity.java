package com.comp3161.placidandroid.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comp3161.placidandroid.R;
import com.comp3161.placidandroid.rest.RestPatient;
import com.comp3161.placidandroid.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class RegisterPatientActivity extends AppCompatActivity {


    private Button registerButton;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText phoneNumEdit;
    private EditText dobEdit;
    private EditText addressEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);

        getSupportActionBar().setTitle("Add Patient");

        initViews();
        registerButton.setOnClickListener(new View.OnClickListener() {
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
                    return;
                }

                new RegisterPatientTask(view.getContext(), Constants.REGISTER_LOGIN,
                        jsonObject.toString()).execute();


            }
        });
    }

    private void initViews()
    {
        firstNameEdit = (EditText) findViewById(R.id.first_name);
        lastNameEdit = (EditText) findViewById(R.id.last_name);
        dobEdit = (EditText) findViewById(R.id.dob);
        addressEdit = (EditText) findViewById(R.id.address);
        phoneNumEdit = (EditText) findViewById(R.id.phone_number);
        registerButton = (Button) findViewById(R.id.login_button);
    }


    private class RegisterPatientTask extends AsyncTask<Void,Void,Boolean>
    {
        Context context;
        String url;
        String json;
        ProgressDialog pd;
        public RegisterPatientTask(Context context,String url,String json)
        {
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
            String response = restPatient.registerPatient(url,json);

            if(TextUtils.isEmpty(response))
                return false;

            try{
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status")){
                    return true;
                }else{
                    return false;
                }
            }catch (JSONException j){

            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pd.dismiss();
            if(result){
                Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(context,"Registration failed",Toast.LENGTH_SHORT).show();
            }


        }


    }
}
